package fr.enst.pact34.whistlepro.api.acquisition;

import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class acquisition
{
    private TargetDataLine dataLine;
    private AudioFormat format;
    private SourceDataLine sourceLine;
    private byte[] buffer;
    private int bufferSize ;

    public acquisition(int bufferSize)
    {
        this.bufferSize = bufferSize;
        this.format = new AudioFormat(
                16000, // SAMPLE RATE
                8, // SAMPLE SIZE IN BYTE
                2, // CHANNELS
                true, // SIGNED
                true // BIGENDIAN
        );
        this.buffer = new byte[this.bufferSize];
    }

    public void startRecording()
    {
        DataLine.Info infoTarget = new DataLine.Info(TargetDataLine.class, this.format);
        if (AudioSystem.isLineSupported(infoTarget))
         {
        try {
            this.dataLine = (TargetDataLine) AudioSystem.getLine(infoTarget);
            this.dataLine.open(this.format);
            this.dataLine.start();
        } catch (LineUnavailableException lex){
            System.out.println("unavailable line");
            return;
        } catch (IllegalArgumentException i){
            i.printStackTrace();
        }
        }
        else {System.out.println("error");}
    }


    public double[] readData()
    {
        DataLine.Info infoSource = new DataLine.Info(SourceDataLine.class, format);
        DataLine.Info infoTarget = new DataLine.Info(TargetDataLine.class, format);
        if (!AudioSystem.isLineSupported(infoSource)|| !AudioSystem.isLineSupported(infoTarget))
        {
            System.out.println("unsupported line");
        }
        dataLine.read(this.buffer, 0, this.buffer.length); // BUFFER, START, LENGTH (blocking)
        double[] output = new double[this.buffer.length];
        for(int i=0; i<this.buffer.length; ++i)
        {
            output[i] = (double)buffer[i];
        }
        return output;
    }

    public void stopRecording()
    {
        try {
            dataLine.stop();
            dataLine.close();
        } catch(NullPointerException n){
            n.printStackTrace();
        }
    }


    public static void main(String args[]) throws NullPointerException
    {
        acquisition acq = new acquisition(16000);
        try {
            acq.startRecording();
        } catch (NullPointerException n)
        {
            n.printStackTrace();
        }

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        acq.stopRecording();
        acq.readData();
        acq.stopRecording();
        double[] b = acq.readData();
        System.out.println("Le signal comporte "+ b.length + " echantillons");
        for(int i=0;i<b.length;++i)System.out.print(b[i]);
        System.out.println("\nFin du signal");
    }
}