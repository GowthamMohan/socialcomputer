package saarland.dfki.socialanxietytrainer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.support.v7.app.AppCompatActivity
import com.jjoe64.graphview.GraphView
import com.thalmic.myo.Hub
import com.thalmic.myo.Myo
import hcm.ssj.core.ExceptionHandler
import hcm.ssj.myo.Vibrate2Command
import kotlinx.android.synthetic.main.activity_execute_task.*
import saarland.dfki.socialanxietytrainer.audioanalysis.IPipeLine
import saarland.dfki.socialanxietytrainer.audioanalysis.PipelineRunner

class ExecuteTaskActivity : IPipeLine, ExceptionHandler, AppCompatActivity() {

    private var _pipe: PipelineRunner? = null
    private var _error_msg: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_execute_task)

        button_start_stop_task.setOnClickListener { view ->
            onStartPressed(view)
        }

        button_demo.setOnClickListener { view ->
            onDemoPressed(view)
        }
    }

    override fun onDestroy() {
        if (_pipe != null && _pipe!!.isRunning())
            _pipe!!.terminate()

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

        val am = getApplicationContext().getAssets()
        getAssets()

        if (_pipe == null || !_pipe!!.isRunning()) {
            val graph = findViewById(R.id.graph) as GraphView
            graph.removeAllSeries()
            //            graph.getSecondScale().removeAllSeries(); //not implemented in GraphView 4.0.1
            val graph2 = findViewById(R.id.graph2) as GraphView
            graph2.removeAllSeries()
            //            graph2.getSecondScale().removeAllSeries(); //not implemented in GraphView 4.0.1

            val graphs = arrayOf<GraphView>(graph, graph2)

            _pipe = PipelineRunner(this, graphs)
            _pipe!!.setExceptionHandler(this)
            _pipe!!.start()
        } else {
            _pipe!!.terminate()
        }
    }

    override fun notifyPipeState(running: Boolean) {
        this.runOnUiThread(Runnable {
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
        })
    }

    fun onDemoPressed(v: View) {
        val hub = Hub.getInstance()
        val t1 = Thread(Runnable {
            if (hub.getConnectedDevices().isEmpty()) {
                Log.e("Logue_SSJ", "device not found")
            } else {
                val myo = hub.getConnectedDevices().get(0)
                startVibrate(myo, hub)
            }
        })
        t1.start()
    }

    private fun startVibrate(myo: Myo, hub: Hub) {
        val _name = "test"
        Log.i(_name, "connected")
        try {
            val vibrate2Command = Vibrate2Command(hub)

            Log.i(_name, "vibrate 1...")
            myo.vibrate(Myo.VibrationType.MEDIUM)
            Thread.sleep(3000)

            Log.i(_name, "vibrate 2...")
            //check strength 50
            vibrate2Command.vibrate(myo, 1000, 50.toByte())
            Thread.sleep(3000)

            Log.i(_name, "vibrate 3 ...")
            //check strength 100
            vibrate2Command.vibrate(myo, 1000, 100.toByte())
            Thread.sleep(3000)

            Log.i(_name, "vibrate 4 ...")
            //check strength 100
            vibrate2Command.vibrate(myo, 1000, 150.toByte())
            Thread.sleep(3000)

            Log.i(_name, "vibrate 5...")
            //check strength 250
            vibrate2Command.vibrate(myo, 1000, 200.toByte())
            Thread.sleep(3000)

            Log.i(_name, "vibrate 6...")
            //check strength 250
            vibrate2Command.vibrate(myo, 1000, 250.toByte())
            Thread.sleep(3000)

            Log.i(_name, "vibrate pattern...")
            //check vibrate pattern
            vibrate2Command.vibrate(myo, intArrayOf(500, 500, 500, 500, 500, 500), byteArrayOf(25, 50, 100, 150.toByte(), 200.toByte(), 250.toByte()))
            Thread.sleep(3000)
        } catch (e: Exception) {
            Log.e(_name, "exception in vibrate test", e)
        }

    }

    override fun handle(location: String, msg: String, t: Throwable) {

        _error_msg = msg
        _pipe!!.terminate() //attempt to shut down framework

        this.runOnUiThread(
                Runnable {
                    Toast.makeText(getApplicationContext(),"Exception in Pipeline\n$msg", Toast.LENGTH_LONG).show()
                })

    }

}

