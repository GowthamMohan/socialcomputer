package saarland.dfki.socialanxietytrainer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.support.v7.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import hcm.ssj.core.ExceptionHandler
import kotlinx.android.synthetic.main.activity_execute_task.*
import saarland.dfki.socialanxietytrainer.audioanalysis.BasePipelineRunner
import saarland.dfki.socialanxietytrainer.audioanalysis.EmoVoicePipelineRunner
import saarland.dfki.socialanxietytrainer.audioanalysis.IPipeLineExecutor
import saarland.dfki.socialanxietytrainer.audioanalysis.SamplePipelineRunner
import saarland.dfki.socialanxietytrainer.classification.ClassificationKind
import saarland.dfki.socialanxietytrainer.classification.ClassificationManager

class ExecuteTaskActivity : IPipeLineExecutor, ExceptionHandler, AppCompatActivity() {

    private lateinit var _pipe: BasePipelineRunner
    private var _error_msg: String? = null

    private val classificationManager = ClassificationManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_execute_task)

        button_start_stop_task.setOnClickListener { view ->
            onStartPressed(view)
        }

        // Setup pipeline
        val graph = findViewById(R.id.graph) as GraphView
        graph.removeAllSeries()
        //            graph.getSecondScale().removeAllSeries(); //not implemented in GraphView 4.0.1
//        val graphs = arrayOf<GraphView>(graph)
        _pipe = EmoVoicePipelineRunner(this, classificationManager.getConsumer(ClassificationKind.VOICE), applicationContext)
//        _pipe = SamplePipelineRunner(this, graphs)
        _pipe.setExceptionHandler(this)
    }

    override fun onDestroy() {
        if (_pipe.isRunning())
            _pipe.terminate()

        super.onDestroy()
        Log.i("LogueWorker", "destroyed")
    }



    /**
     * Prevent activity from being destroyed once back button is pressed
     */
    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    fun onStartPressed(v: View) {
        val btn = findViewById(R.id.button_start_stop_task) as Button
        btn.setAlpha(0.5f)
        btn.setEnabled(false)
        getCacheDir().getAbsolutePath()

        val am = applicationContext.getAssets()
        getAssets()

        if (!_pipe.isRunning()) {
            _pipe.start()
        } else {
            _pipe.terminate()
        }
    }

    override fun notifyPipeState(running: Boolean) {
        this.runOnUiThread {
            val btn = findViewById(R.id.button_start_stop_task) as Button

            if (running) {
                btn.setText(R.string.stop)
                btn.setEnabled(true)
                btn.setAlpha(1.0f)
            } else {
                assert(_error_msg != null)
                btn.setText(R.string.start)
                btn.setEnabled(true)
                btn.setAlpha(1.0f)
            }
        }
    }

    override fun handle(location: String, msg: String, t: Throwable) {
        _error_msg = msg
        _pipe.terminate() //attempt to shut down framework

        this.runOnUiThread {
            Toast.makeText(applicationContext,"Exception in Pipeline\n$msg", Toast.LENGTH_LONG).show()
        }

    }

}

