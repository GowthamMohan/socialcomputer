package saarland.dfki.socialanxietytrainer.audioanalysis;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import hcm.ssj.audio.AudioChannel;
import hcm.ssj.audio.Microphone;
import hcm.ssj.core.Pipeline;
import hcm.ssj.core.SSJException;
import hcm.ssj.ml.ClassifierT;
import hcm.ssj.ml.NaiveBayes;
import hcm.ssj.mobileSSI.SSI;
import hcm.ssj.mobileSSI.SSITransformer;
import hcm.ssj.test.Logger;
import saarland.dfki.socialanxietytrainer.IOHelper;

public class EmoVoicePipelineRunner extends BasePipelineRunner {

    private boolean terminate = false;

    private IPipeLineExecutor act = null;
    private Context ctx = null;

    public EmoVoicePipelineRunner(IPipeLineExecutor act, Context ctx) {
        this.act = act;
        this.ctx = ctx;

        if (Pipeline.isInstanced())
            Pipeline.getInstance().clear();
        setSsj(Pipeline.getInstance());
    }

    @Override
    public void run() {
        // Copy model resources
        File dir = ctx.getFilesDir();
        String modelName = "emovoice.trainer";

        try {
            IOHelper.copyAssetToFile(ctx, modelName, new File(dir, modelName));
            IOHelper.copyAssetToFile(ctx, "emovoice.model", new File(dir, "emovoice.model"));
            // Setup framework
            getSsj().options.bufferSize.set(10.0f);

            // Sensor
            Microphone microphone = new Microphone();
            AudioChannel audioChannel = new AudioChannel();
            audioChannel.options.sampleRate.set(8000);
            audioChannel.options.scale.set(true);
            getSsj().addSensor(microphone, audioChannel);

            // Feature transformer
            SSITransformer emovoiceFeatures = new SSITransformer();
            emovoiceFeatures.options.name.set(SSI.TransformerName.EmoVoiceFeat);
            emovoiceFeatures.options.ssioptions.set(new String[]{"maj->1", "min->0"});
            getSsj().addTransformer(emovoiceFeatures, audioChannel, 1.35);

            // Classifier
            NaiveBayes naiveBayes = new NaiveBayes();
            naiveBayes.options.file.setValue(dir.getAbsolutePath() + File.separator + modelName);
            getSsj().addModel(naiveBayes);

            ClassifierT classifier = new ClassifierT();
            classifier.setModel(naiveBayes);
            getSsj().addTransformer(classifier, emovoiceFeatures, 1.35, 0);

            // Logger
//            Logger log = new Logger();
            EmoVoiceConsumer econs = new EmoVoiceConsumer();
            //frame.addConsumer(log, emovoiceFeatures, 1, 0);
//            getSsj().addConsumer(log, classifier, 1.35, 0);
            getSsj().addConsumer(econs, classifier, 1.35, 0);

            // Start framework
            getSsj().start();
        } catch (IOException | SSJException e) {
            e.printStackTrace();
            terminate = true;
        }

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
        getSsj().release();
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
        return false;
    }
}
