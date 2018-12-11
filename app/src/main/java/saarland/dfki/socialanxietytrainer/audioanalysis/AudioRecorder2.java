package saarland.dfki.socialanxietytrainer.audioanalysis;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioTrack;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class AudioRecorder2 extends Thread implements IAudioRecorder {

    private MediaRecorder myAudioRecorder;
    private String outputFile;

    public AudioRecorder2(Context context) {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        if (!checkRecordAudioPermission(context)) {
            throw new IllegalStateException("Recording audio is not allowed.");
        }

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);
    }

    @Override
    public void run() {
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        } catch (IllegalStateException ise) {
            // make something ...
        } catch (IOException ioe) {
            // make something
        }
    }

    private boolean checkRecordAudioPermission(Context context) {
        String permission = Manifest.permission.RECORD_AUDIO;
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void close() {
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
    }

    @NotNull
    @Override
    public AudioTrack getTrack() {
        return null;
    }

    @NotNull
    @Override
    public MediaPlayer getMediaPlayer() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(outputFile);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }
}
