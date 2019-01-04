package saarland.dfki.socialanxietytrainer.audioanalysis;

import android.util.Log;

import com.jjoe64.graphview.GraphView;

import hcm.ssj.audio.AudioChannel;
import hcm.ssj.audio.Microphone;
import hcm.ssj.audio.Pitch;
import hcm.ssj.core.Pipeline;
import hcm.ssj.core.Provider;
import hcm.ssj.graphic.SignalPainter;

public class SamplePipelineRunner extends BasePipelineRunner {

    private boolean terminate = false;

    private IPipeLineExecutor act = null;
    private GraphView graphs[] = null;

    public SamplePipelineRunner(IPipeLineExecutor a, GraphView[] graphs) {
//        super();

        act = a;
        this.graphs = graphs;

        if (Pipeline.isInstanced())
            Pipeline.getInstance().clear();
        setSsj(Pipeline.getInstance());
    }

    @Override
    public void run() {
        try {
            getSsj().options.bufferSize.set(10.0f);
            getSsj().options.countdown.set(1);
            getSsj().options.log.set(true);

            //** connection to sensors
            Microphone mic = new Microphone();
            AudioChannel audio = new AudioChannel();
            audio.options.sampleRate.set(16000);
            audio.options.scale.set(true);
            getSsj().addSensor(mic,audio);

            //** transform data coming from sensors
            Pitch pitch = new Pitch();
            pitch.options.detector.set(Pitch.YIN);
            pitch.options.computePitchedState.set(false);
            pitch.options.computePitch.set(true);
            pitch.options.computeVoicedProb.set(false);
            pitch.options.computePitchEnvelope.set(false);
            getSsj().addTransformer(pitch, audio, 0.032, 0); //512 samples

            //** configure GUI
            //paint audio
            SignalPainter paint = new SignalPainter();
            paint.options.manualBounds.set(true);
            paint.options.min.set(0.);
            paint.options.max.set(1.);
            paint.options.renderMax.set(true);
            paint.options.secondScaleMin.set(0.);
            paint.options.secondScaleMax.set(500.);
            paint.options.graphView.set(graphs[0]);
            getSsj().addConsumer(paint, new Provider[]{audio, pitch}, 0.032, 0);
        } catch(Exception e) {
            Log.e("SSJ_Demo", "error building pipe", e);
            return;
        }

        Log.i("SSJ_Demo", "starting pipeline");
        getSsj().start();
        act.notifyPipeState(true);

        while(!terminate) {
            try {
                synchronized(this) {
                    this.wait();
                }
            } catch (InterruptedException e) {
                Log.e("pipeline", "Error", e);
            }
        }

        Log.i("SSJ_Demo", "stopping pipeline");
        getSsj().stop();
        getSsj().clear();
        act.notifyPipeState(false);
    }

    @Override
    public void terminate() {
        terminate = true;

        synchronized(this) {
            this.notify();
        }
    }

    @Override
    public boolean isRunning() {
        return getSsj().isRunning();
    }
}
