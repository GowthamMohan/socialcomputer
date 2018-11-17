package saarland.dfki.socialanxietytrainer

import android.graphics.Color
import android.media.AudioTrack
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_execute_task.*
import saarland.dfki.socialanxietytrainer.audioanalysis.AudioRecorder2

class ExecuteTaskActivity : AppCompatActivity() {

    private var taskActive = false
    private var audioRecorder: AudioRecorder2? = null
    private var audioTrack: AudioTrack? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_execute_task)

        audioRecorder = AudioRecorder2(applicationContext)

        button_start_stop_task.setOnClickListener { view ->
            if (taskActive) {
                stopAudioRecording()
                button_start_stop_task.setBackgroundColor(Color.parseColor("#738b28"))
                button_play_recording.isEnabled = true
            } else {
                startAudioRecording()
                button_start_stop_task.setBackgroundColor(Color.parseColor("#FFFFFF"))
                button_play_recording.isEnabled = false
            }
            taskActive = !taskActive
        }

        button_play_recording.setOnClickListener { view ->
            assert(audioTrack != null)
            mediaPlayer!!.start()
        }
    }

    fun startAudioRecording() {
        audioRecorder!!.start()
    }

    fun stopAudioRecording() {
        Log.i("ExecuteTaskActivity", "stopAudioRecording")
        audioRecorder!!.close()
        audioRecorder!!.join()
        mediaPlayer = audioRecorder!!.getMediaPlayer()
        Log.i("ExecuteTaskActivity", "Wrote track")
    }

}
