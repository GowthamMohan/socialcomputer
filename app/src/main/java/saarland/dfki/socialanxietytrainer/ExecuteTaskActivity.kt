package saarland.dfki.socialanxietytrainer

import android.app.Notification
import android.graphics.Color
import android.media.AudioTrack
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_execute_task.*
import saarland.dfki.socialanxietytrainer.audioanalysis.AudioRecorder
import android.app.NotificationManager
import android.support.v4.app.NotificationCompat



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

                button_start_stop_task.setBackgroundColor(Color.parseColor("#738b28"))
                stopAudioRecording()
            } else {
                button_start_stop_task.setBackgroundColor(Color.parseColor("#FFFFFF"))
                startAudioRecording()
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
