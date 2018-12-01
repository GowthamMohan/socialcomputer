package saarland.dfki.socialanxietytrainer

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.AudioTrack
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.jjoe64.graphview.GraphView
import com.thalmic.myo.Hub
import com.thalmic.myo.Myo
import hcm.ssj.core.ExceptionHandler
import hcm.ssj.core.Pipeline
import hcm.ssj.myo.Vibrate2Command
import kotlinx.android.synthetic.main.activity_execute_task.*
import saarland.dfki.socialanxietytrainer.audioanalysis.AudioRecorder2
import saarland.dfki.socialanxietytrainer.audioanalysis.IPipeLine
import saarland.dfki.socialanxietytrainer.audioanalysis.PipelineRunner

//IPipeLine
class ExecuteTaskActivity : IPipeLine, ExceptionHandler, AppCompatActivity() {

    private var taskActive = false
//    private var audioRecorder: AudioRecorder2? = null
//    private var audioTrack: AudioTrack? = null
//    private var mediaPlayer: MediaPlayer? = null

    private var _pipe: PipelineRunner? = null
    private var _ssj_version: String? = null
    private var _error_msg: String? = null
    private val REQUEST_DANGEROUS_PERMISSIONS = 108



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_execute_task)

//        audioRecorder = AudioRecorder2(applicationContext)

        button_start_stop_task.setOnClickListener { view ->
//            if (taskActive) {
//                stopAudioRecording()
//                button_start_stop_task.setBackgroundColor(Color.parseColor("#738b28"))
//                button_play_recording.isEnabled = true
//            } else {
//                startAudioRecording()
//                button_start_stop_task.setBackgroundColor(Color.parseColor("#FFFFFF"))
//                button_play_recording.isEnabled = false
//            }
//            taskActive = !taskActive
            onStartPressed(view)
        }

        button_demo.setOnClickListener { view ->
            onDemoPressed(view)
        }
//
//        button_play_recording.setOnClickListener { view ->
//            assert(audioTrack != null)
//            mediaPlayer!!.start()
//        }

        // SSJ setup
//        _ssj_version = "SSJ v" + Pipeline.getInstance().getVersion()
        _ssj_version = "SSJ v?"

        val text = findViewById(R.id.txt_ssj) as TextView
        text.setText(_ssj_version)

        // Permissions setup
        checkPermissions()
    }

//    fun startAudioRecording() {
//        audioRecorder!!.start()
//    }
//
//    fun stopAudioRecording() {
//        Log.i("ExecuteTaskActivity", "stopAudioRecording")
//        audioRecorder!!.close()
//        audioRecorder!!.join()
//        mediaPlayer = audioRecorder!!.getMediaPlayer()
//        Log.i("ExecuteTaskActivity", "Wrote track")
//    }






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
        val text = findViewById(R.id.txt_ssj) as TextView

        if (_pipe == null || !_pipe!!.isRunning()) {
            text.setText(_ssj_version!! + " - starting")


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
            text.setText(_ssj_version!! + " - stopping")
            _pipe!!.terminate()
        }
    }

    override fun notifyPipeState(running: Boolean) {
        this.runOnUiThread(Runnable {
            val btn = findViewById(R.id.button_start_stop_task) as Button
            val text = findViewById(R.id.txt_ssj) as TextView

            if (running) {
                text.setText(_ssj_version!! + " - running")
                btn.setText(R.string.stop)
                btn.setEnabled(true)
                btn.setAlpha(1.0f)
            } else {
                text.setText(_ssj_version!! + " - not running")
                if (_error_msg != null) {
                    val str = text.text.toString() + "\nERROR: " + _error_msg
                    text.setText(str)
                }

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
                Runnable { Toast.makeText(getApplicationContext(), "Exception in Pipeline\n$msg", Toast.LENGTH_LONG).show() })

    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            //dangerous permissions
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !== PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) !== PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) !== PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BODY_SENSORS, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_DANGEROUS_PERMISSIONS)
            }
        }
    }

}
