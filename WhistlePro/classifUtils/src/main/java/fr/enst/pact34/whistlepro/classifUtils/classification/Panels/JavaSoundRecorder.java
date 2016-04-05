package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;

/**
 * Created by user on 04/04/16.
 */
import javax.sound.sampled.*;
import java.io.*;
import java.util.*;

/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */
public class JavaSoundRecorder {

    public void setListener(AudioDataListener listener) {
        this.listener = listener;
    }

    private AudioDataListener listener = null;

    public JavaSoundRecorder(double tempsRec)
    {
        tps_rec = tempsRec;
    }


    private double tps_rec ; //(10ms)

    /**
     * Defines an audio format
     */
    private AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }

    private boolean rec = false;
    /**
     * Captures the sound and record into a WAV file
     */
    void start() {
        if(rec==true) return;
        rec = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                 // the line from which audio data is captured
                TargetDataLine line = null;
        try {

            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                //System.exit(0);
                return;
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing

            //System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            //System.out.println("Start recording...");

            int nbSamples = (int)(tps_rec*format.getFrameRate());

            byte[] bytes = new byte[ais.getFormat().getFrameSize()*nbSamples];

            double[][]   buffers  = new double[1024][nbSamples];
            int         ix       = 0;
            while(rec)
            {
                ais.read(bytes,0,bytes.length);

                double[] buffer = buffers[ix++ % buffers.length];
                for(int i =0; i < nbSamples; i++)
                {
                    buffer[i] = ( (bytes[2*i] << 8) + bytes[2*i+1] ) / Short.MAX_VALUE;
                }

                listener.pushData(buffer);

            }
            line.stop();
            line.close();

        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //System.out.println("Finished");
            rec = false;
        }

            }
        }
        ).start();
    }

    /**
     * Closes the target data line to finish capturing and recording
     */
    void stop() {
        //System.out.println("Finish...");
        rec = false;
    }


    public boolean isRecording()
    {
        return rec;
    }

    public double getSampleRate() {
        return getAudioFormat().getSampleRate();
    }
}