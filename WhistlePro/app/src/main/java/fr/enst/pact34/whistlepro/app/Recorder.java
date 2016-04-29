package fr.enst.pact34.whistlepro.app;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Process;
import android.util.Log;

import fr.enst.pact34.whistlepro.api2.main.DoubleDataListener;

/**
 * Created by mms on 26/04/16.
 */
public class Recorder {
    private int sampleRate = 16000; //TODO make it a parameter ?
    private DoubleDataListener listener = null;


    public Recorder() {
        recThread.start();
    }

    public int getSampleRate()
    {
        return sampleRate;
    }


    public void setListener(DoubleDataListener listener)
    {
        this.listener = listener;
    }

    private double[] dataTmp ;
    private void dataProduced(short[] data) {
        if(data.length == dataTmp.length) {
            for (int i = 0; i < data.length; i++) {
                dataTmp[i] = ((double)data[i]) / Short.MAX_VALUE;
            }
        }
        else {
            for (int i = 0; i < data.length; i++) {
                dataTmp[i] = 0;
            }
        }

        if(listener != null) listener.pushData(dataTmp);
    }

    public boolean isRecording()
    {
        return !stopped;
    }

    public void startRec()
    {
        if(stopped==false) return;
        synchronized (recThread) {
            stopped = false;
            recThread.notify();
        }
    }

    public void stopRec()
    {
        if(stopped==true) return;
        stopped = true;
    }

    private boolean stopped = true;

    private Thread recThread = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
                    int N = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
                    AudioRecord recorder = null;
                    short[][] buffers = new short[256][N];
                    dataTmp = new double[N];
                    int ix = 0;

                    try { // ... initialise

                        while(true) {

                            synchronized (recThread) {
                                if (stopped) recThread.wait();
                            }

                            recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                                    sampleRate,
                                    AudioFormat.CHANNEL_IN_MONO,
                                    AudioFormat.ENCODING_PCM_16BIT,
                                    N * 10);

                            recorder.startRecording();
                            // ... loop

                            while (!stopped) {
                                short[] buffer = buffers[ix++ % buffers.length];

                                N = recorder.read(buffer, 0, buffer.length);
                                if (listener != null && N > 0 ) {
                                    dataProduced(buffer);
                                }
                            }

                            recorder.stop();
                            recorder.release();
                        }
                    } catch (Throwable x) {
                        Log.w("ee", "Error reading voice audio", x);
                    }
                }
            }
    );

    /* Getting supported Sample rates (in theory 44100 is always supported)
    public void getValidSampleRates() {
    for (int rate : new int[] {8000, 11025, 16000, 22050, 44100}) {  // add the rates you wish to check against
        int bufferSize = AudioRecord.getMinBufferSize(rate, AudioFormat.CHANNEL_CONFIGURATION_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
        if (bufferSize > 0) {
            // buffer size is valid, Sample rate supported

            }
        }
    }
     */
}
