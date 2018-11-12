package saarland.dfki.socialanxietytrainer

import android.graphics.Color
import android.media.AudioTrack
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_execute_task.*
import saarland.dfki.socialanxietytrainer.audioanalysis.AudioRecorder

class ExecuteTaskActivity : AppCompatActivity() {

    private var taskActive = false
    private var audioRecorder: AudioRecorder? = null
    private var audioTrack: AudioTrack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_execute_task)

        audioRecorder = AudioRecorder(applicationContext)

        button_start_stop_task.setOnClickListener { view ->
            if (taskActive) {
                stopAudioRecording()
                button_start_stop_task.setBackgroundColor(Color.parseColor("#738b28"))
            } else {
                startAudioRecording()
                button_start_stop_task.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            taskActive = !taskActive
        }
    }

    fun startAudioRecording() {
        audioRecorder!!.start()
    }

    fun stopAudioRecording() {
        Log.i("ExecuteTaskActivity", "stopAudioRecording")
        audioRecorder!!.close()
        audioRecorder!!.join()
        audioTrack = audioRecorder!!.track
        assert(audioTrack != null)
        Log.i("ExecuteTaskActivity", "Wrote track")
    }

}
