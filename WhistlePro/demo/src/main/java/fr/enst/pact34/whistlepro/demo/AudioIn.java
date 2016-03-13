package fr.enst.pact34.whistlepro.demo;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Process;
import android.util.Log;

/**
 * Created by mms on 29/02/16.
 */
public class AudioIn extends Thread {
    private boolean stopped    = false;
    private AudioDataListener listener = null;

    public void setListener(AudioDataListener lis)
    {
        listener=lis;
    }

    public AudioIn() {

        start();
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
        AudioRecord recorder = null;
        short[][]   buffers  = new short[256][160];
        int         ix       = 0;

        try { // ... initialise

            int N = AudioRecord.getMinBufferSize(PercussionTest.Fs,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT);

            recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    PercussionTest.Fs,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    N*10);

            recorder.startRecording();

            // ... loop

            while(!stopped) {
                short[] buffer = buffers[ix++ % buffers.length];

                N = recorder.read(buffer,0,buffer.length);
                if(listener!=null)
                {
                    listener.dataReceiver(buffer.clone());
                }
            }

            recorder.stop();
            recorder.release();

        } catch(Throwable x) {
            Log.w("ee","Error reading voice audio",x);
        } finally {
            close();
        }
    }

    private void close() {
        stopped = true;
    }

}