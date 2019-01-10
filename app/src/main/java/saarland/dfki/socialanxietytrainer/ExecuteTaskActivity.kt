package saarland.dfki.socialanxietytrainer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView
import hcm.ssj.core.ExceptionHandler
import kotlinx.android.synthetic.main.activity_execute_task.*
import saarland.dfki.socialanxietytrainer.audioanalysis.BasePipelineRunner
import saarland.dfki.socialanxietytrainer.audioanalysis.EmoVoicePipelineRunner
import saarland.dfki.socialanxietytrainer.audioanalysis.IPipeLineExecutor
import saarland.dfki.socialanxietytrainer.classification.ClassificationKind
import saarland.dfki.socialanxietytrainer.classification.ClassificationManager
import saarland.dfki.socialanxietytrainer.executeTasks.ExecuteTaskWatcher

class ExecuteTaskActivity : IPipeLineExecutor, ExceptionHandler, AppCompatActivity() {

    private lateinit var category: String
    private lateinit var description: String
    private var difficulty: Int = 0
    private var id: Int = 0

    private lateinit var _pipe: BasePipelineRunner
    private var _error_msg: String? = null

    private val classificationManager = ClassificationManager()
    private val executeTaskWatcher = ExecuteTaskWatcher(this, classificationManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_execute_task)

        var intent = intent
        this.category = intent.getStringExtra("category")
        this.description = intent.getStringExtra("description")
        this.difficulty = intent.getStringExtra("difficulty").toInt()
        this.id = intent.getStringExtra("id").toInt()

        findViewById<TextView>(R.id.textView_task_category).text = "Category: ${this.category}"
        findViewById<TextView>(R.id.textView_task_description).text = "Description: ${this.description}"

        button_start_stop_task.setOnClickListener { view ->
            onStartStopPressed(view)
        }

        // Setup pipeline
        _pipe = EmoVoicePipelineRunner(this, classificationManager.getConsumer(ClassificationKind.VOICE), applicationContext)
        _pipe.setExceptionHandler(this)
    }

    override fun onDestroy() {
        if (_pipe.isRunning()) {
            _pipe.terminate()
            executeTaskWatcher.terminate()
        }

        super.onDestroy()
        Log.i("LogueWorker", "destroyed")
    }

    /**
     * Prevent activity from being destroyed once back button is pressed.
     */
    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    private fun onStartStopPressed(v: View) {
        val btn = findViewById<ImageButton>(R.id.button_start_stop_task)
        getCacheDir().getAbsolutePath()

        val am = applicationContext.getAssets()
        getAssets()

        if (!_pipe.isRunning()) {
            _pipe.start()
            executeTaskWatcher.start()
            btn.setImageResource(R.drawable.ic_stop_black)
        } else {
            _pipe.terminate()
            executeTaskWatcher.terminate()
            btn.setImageResource(R.drawable.ic_play_arrow_black)
        }
    }

    override fun notifyPipeState(running: Boolean) {
        this.runOnUiThread {
            val btn = findViewById<ImageButton>(R.id.button_start_stop_task)

            if (running) {
                btn.isEnabled = true
                btn.alpha = 1.0f
            } else {
                assert(_error_msg != null)
                btn.isEnabled = true
                btn.alpha = 1.0f
            }
        }
    }

    override fun handle(location: String, msg: String, t: Throwable) {
        _error_msg = msg
        _pipe.terminate() //attempt to shut down framework
        executeTaskWatcher.terminate()

        this.runOnUiThread {
            Toast.makeText(applicationContext,"Exception in Pipeline\n$msg", Toast.LENGTH_LONG).show()
        }
    }

}

