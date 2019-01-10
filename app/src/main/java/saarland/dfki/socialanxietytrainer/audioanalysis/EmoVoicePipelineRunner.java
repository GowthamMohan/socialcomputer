package saarland.dfki.socialanxietytrainer.audioanalysis;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import hcm.ssj.audio.AudioChannel;
import hcm.ssj.audio.Microphone;
import hcm.ssj.core.Consumer;
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
    private boolean running = false;

    private final String modelName = "emovoice.trainer";
    private final File resDir;

    private final IPipeLineExecutor act;
    private final Context ctx;
    private final Consumer cons;

    public EmoVoicePipelineRunner(IPipeLineExecutor act, Consumer cons, Context ctx) throws IOException {
        this.act = act;
        this.ctx = ctx;
        this.cons = cons;

        // Copy model resources
        resDir = ctx.getFilesDir();
        IOHelper.copyAssetToFile(ctx, modelName, new File(resDir, modelName));
        IOHelper.copyAssetToFile(ctx, "emovoice.model", new File(resDir, "emovoice.model"));

        if (Pipeline.isInstanced())
            Pipeline.getInstance().clear();
        setSsj(Pipeline.getInstance());
    }

    @Override
    public void run() {
        running = true;

        try {
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
            naiveBayes.options.file.setValue(resDir.getAbsolutePath() + File.separator + modelName);
            getSsj().addModel(naiveBayes);

            ClassifierT classifier = new ClassifierT();
            classifier.setModel(naiveBayes);
            getSsj().addTransformer(classifier, emovoiceFeatures, 1.35, 0);

            // Logger
//            Logger log = new Logger();
//            EmoVoiceConsumer econs = new EmoVoiceConsumer();
            //frame.addConsumer(log, emovoiceFeatures, 1, 0);
//            getSsj().addConsumer(log, classifier, 1.35, 0);
            getSsj().addConsumer(this.cons, classifier, 1.35, 0);

            // Start framework
            getSsj().start();
        } catch (SSJException e) {
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
        running = false;
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
        return running;
    }
}
