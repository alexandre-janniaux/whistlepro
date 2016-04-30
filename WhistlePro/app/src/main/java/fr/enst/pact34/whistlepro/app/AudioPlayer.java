package fr.enst.pact34.whistlepro.app;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AudioPlayer {

    public int freq = 44100;
    private boolean isRunning = true;
    private Thread t;
    AudioTrack audioTrack = null;
    private short volume = Short.MAX_VALUE/2;

    private short[] buffer = new short[512];

    public void push(double[] samples) {
        int n = samples.length / 512;
        for(int i=0;i<n+1; ++i) {
            int m = Math.min(512, samples.length - i * 512);
            if (m==0) break;
            for(int j=0; j<m; ++j) {
                buffer[j] = (short) (samples[i*512+j]*this.volume);
            }
            audioTrack.write(buffer, 0, m);
        }
    }

    public void start() {
        int buffsize = AudioTrack.getMinBufferSize(freq,
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        // create an audiotrack object
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                freq, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, buffsize,
                AudioTrack.MODE_STREAM);


        // start audio
        audioTrack.play();
    }

    public void stop() {
        audioTrack.stop();
        audioTrack.release();
    }

}
