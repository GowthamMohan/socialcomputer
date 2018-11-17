package saarland.dfki.socialanxietytrainer.audioanalysis;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

/*
 * Thread to manage live recording/playback of voice input from the device's microphone.
 */
public class AudioRecorder extends Thread {
    private AtomicBoolean stopped = new AtomicBoolean(false);
    private AudioTrack track = null;

    private static int[] mSampleRates = new int[] { 8000, 11025, 22050, 44100 };

    /**
     * Give the thread high priority so that it's not canceled unexpectedly, and start it
     */
    public AudioRecorder(Context context) {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        if (!checkRecordAudioPermission(context)) {
            throw new IllegalStateException("Recording audio is not allowed.");
        }
    }

    @Override
    public void run() {
        Log.i("Audio", "Running Audio Thread");
        AudioRecord recorder = null;
        short[][]   buffers  = new short[256][160];
        int ix = 0;

        /*
         * Initialize buffer to hold continuously recorded audio data, start recording, and start
         * playback.
         */
        try {
            Object[] config = findAudioRecordConfig();
            assert (config.length == 5);
            int N = (Integer) config[4];
            int audioSrc = (Integer) config[0];
            int rate = (Integer) config[1];
            int channelConfig = (Integer) config[2];
            int audioFormat = (Integer) config[3];
            int bufferSize = (Integer) config[4];
            recorder = new AudioRecord(audioSrc,
                    rate,
                    channelConfig,
                    audioFormat,
                    bufferSize);
            track = new AudioTrack(AudioManager.STREAM_MUSIC,
                    rate,
                    channelConfig,
                    audioFormat,
                    N*10,
                    AudioTrack.MODE_STREAM);

            recorder.startRecording();
            /*
             * Loops until something outside of this thread stops it.
             * Reads the data from the recorder and writes it to the audio track for playback.
             */
            while (true) {
                if (stopped.get()) {
                    recorder.stop();
                }

                short[] buffer = buffers[ix++ % buffers.length];
                N = recorder.read(buffer,0, buffer.length);
                System.out.println("N: " + N);
                track.write(buffer, 0, buffer.length);
                if (N <= 0) {
                    break;
                }
            }
        } catch(Throwable x) {
            Log.w("Audio", "Error reading voice audio", x);
        }
        /*
         * Frees the thread's resources after the loop completes so that it can be run again
         */
        finally {
//            recorder.stop();
            recorder.release();
//            track.stop();
//            track.pause();
//            track.flush();
//            track.release();
        }
    }

    private Object[] findAudioRecordConfig() {
        for (int rate : mSampleRates) {
            for (int audioFormat : new Integer[] { AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT }) {
                for (int channelConfig : new Integer[] { AudioFormat.CHANNEL_IN_STEREO, AudioFormat.CHANNEL_OUT_MONO }) {
                    for (int audioSource : new Integer[] { MediaRecorder.AudioSource.MIC, MediaRecorder.AudioSource.DEFAULT }) {
                        try {
                            Log.d("Audio", "Attempting rate " + rate + "Hz, bits: " + audioFormat + ", channel: "
                                    + channelConfig);
                            int bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat);

                            if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                                // check if we can instantiate and have a success
                                AudioRecord recorder = new AudioRecord(audioSource,
                                        rate,
                                        channelConfig,
                                        audioFormat,
                                        bufferSize);

                                if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
                                    return new Object[] { audioSource, rate, channelConfig, audioFormat, bufferSize };
                            }
                        } catch (Exception e) {
                            Log.e("Audio", rate + "Exception, keep trying.", e);
                        }
                    }
                }
            }
        }
        throw new IllegalStateException("Could not find an AudioRecorder.");
    }

    private boolean checkRecordAudioPermission(Context context) {
        String permission = Manifest.permission.RECORD_AUDIO;
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Called from outside of the thread in order to stop the recording/playback loop
     */
    public void close() {
        stopped.set(true);
    }

    public AudioTrack getTrack() {
        return track;
    }

}
