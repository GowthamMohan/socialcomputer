package saarland.dfki.socialanxietytrainer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.ImageButton
import android.widget.TextView
import hcm.ssj.core.ExceptionHandler
import kotlinx.android.synthetic.main.activity_execute_task.*
import saarland.dfki.socialanxietytrainer.audioanalysis.BasePipelineRunner
import saarland.dfki.socialanxietytrainer.audioanalysis.EmoVoicePipelineRunner
import saarland.dfki.socialanxietytrainer.audioanalysis.IPipeLineExecutor
import saarland.dfki.socialanxietytrainer.classification.ClassificationKind
import saarland.dfki.socialanxietytrainer.task.ExecuteTaskWatcher
import saarland.dfki.socialanxietytrainer.heartrate.SimulationType

class ExecuteTaskActivity : IPipeLineExecutor, ExceptionHandler, AppCompatActivity() {

    private lateinit var category: String
    private lateinit var description: String
    private var level: Int = 0
    private var id: Int = 0

    private lateinit var _pipe: BasePipelineRunner
    private var _error_msg: String? = null

    private val classificationManager = MainActivity.classificationManager
    private val executeTaskWatcher = ExecuteTaskWatcher(this, classificationManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_execute_task)

        var intent = intent
        this.category = intent.getStringExtra("category")
        this.description = intent.getStringExtra("description")
        this.level = intent.getStringExtra("level").toInt()
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

            val intent = Intent(this@ExecuteTaskActivity, RatingActivity::class.java)
            startActivity(intent)
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

    /* onKeyUp method is for developing only!  Allows you to manually change the heartrate:
                    press I on keybord to simulate a calm heartrate
                    press O on keybord to simulate a slightly increased heartrate (a bit nervous or excited)
                    press P on keybord to simulate a very high heartrate (fear)
                    If input does not work, click on the emulator and try again
                    IMPORTANT: Only works in activities that have this method! If you need to manipulate the heartrate in another activity,
                    simply copy & paste this method and add it to that activity

            * */
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        //don't react if no simulated watch is connected
        if (!MainActivity.bandConnectAcitivity!!.isConnected) {
            return super.onKeyUp(keyCode, event)
        }
        //kept general to be able to copy it everywhere
        val simulator = MainActivity.simulator
        when (keyCode) {
            KeyEvent.KEYCODE_I -> {
                simulator.simulateHeartRate(SimulationType.CALM)
                return true
            }

            KeyEvent.KEYCODE_O -> {
                simulator.simulateHeartRate(SimulationType.EXCITED)
                return true
            }

            KeyEvent.KEYCODE_P -> {
                simulator.simulateHeartRate(SimulationType.FRIGHTENED)
                return true
            }
            else -> return super.onKeyUp(keyCode, event)
        }
    }




}

