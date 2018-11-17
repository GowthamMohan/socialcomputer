package saarland.dfki.socialanxietytrainer.audioanalysis

import android.media.AudioTrack
import android.media.MediaPlayer

interface IAudioRecorder {

    fun close()

    fun getTrack(): AudioTrack

    fun getMediaPlayer(): MediaPlayer
}