package fr.enst.pact34.whistlepro.api.Acquisition;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class acquisition
{
    private TargetDataLine dataLine;
    private AudioFormat format;

    public acquisition()
    {
        this.format = new AudioFormat(
                16000, // SAMPLE RATE
                8, // SAMPLE SIZE IN BYTE
                2, // CHANNELS
                true, // SIGNED
                true // BIGENDIAN
        );
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
        this.startRecording();
        byte[] output = new byte[dataLine.getBufferSize()/5];
        int numBytes;
        numBytes=dataLine.read(output, 0, output.length); // BUFFER, START, LENGTH (blocking)
	    System.out.write(output,0,numBytes);
	    double[] sortie = new double[output.length];
        for (int i=0;i<output.length;i++)
        {
        	sortie[i] = (double)output[i];
        }
        return sortie;
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
        // TEST
    	acquisition acq = new acquisition();
        try {
        	acq.startRecording();
        } catch (NullPointerException n)
        {
            n.printStackTrace();
        }
        try {
            Thread.sleep(3000);                 //3 secondes
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        acq.stopRecording();
        double[] b = acq.readData();
        
        System.out.println("Le signal comporte "+ b.length + " echantillons");
        for(int i=0;i<b.length;++i)System.out.print(b[i]);
        System.out.println("\nFin du signal");
        
    }
}