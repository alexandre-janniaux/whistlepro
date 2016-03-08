import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class NewSound implements NewSoundInterface {

	Thread thread = new Thread();
	static boolean stopped = false;
	boolean isRecording = false; //quand la personne appuie sur le bouton record, devient true, quand elle ré-appuie pour arrêter le record, redevient false
	
	AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
	
	public WavFile captureAndSave(String name, String dossier) 
	{
		while (isRecording = true) 
		{
			File file = new File(dossier, name) ;
			try
		      {
		         int sampleRate = 44100;    // frequence d'echantillonnage
		         double dureeDeLEchantillon = 5.0;     // en secondes
		         long nbFrames = (long)(dureeDeLEchantillon * sampleRate);
		         WavFile wavFile = WavFile.newWavFile(file, 2, nbFrames, 16, sampleRate) ; // on cree un buffer de 100 frames
		         double[][] buffer = new double[2][100];
		         long frameCounter = 0; //on compte localement le nb de frames
		         while (frameCounter < nbFrames) //condition d'arret = toutes les frames sont ecrites
		         {
		            long NbFramesRestantes = wavFile.getFramesRemaining(); //cela dépend de la taille du buffer
		            int toWrite = (NbFramesRestantes > 100) ? 100 : (int) NbFramesRestantes ;
		            for (int s=0 ; s<toWrite ; s++, frameCounter++) //on remplit le buffer channel par channel (une note par channel)
		            {
		               buffer[0][s] = Math.sin(2.0 * Math.PI * 400 * frameCounter / sampleRate);
		               buffer[1][s] = Math.sin(2.0 * Math.PI * 500 * frameCounter / sampleRate);
		            }
		            wavFile.writeFrames(buffer, toWrite); //on ferme le buffer
		         }
		         wavFile.close();// on ferme wavFile
		      }
		      catch (Exception e)
		      {
		         System.err.println(e);
		      }
		}
		return null;
	}

	public void captureAndPlay()
	{
			AudioFormat format = getAudioFormat();
			SourceDataLine sourceLine; //sert à capturer
			TargetDataLine targetLine; //sert à jouer (play)
			DataLine.Info infoSource = new DataLine.Info(SourceDataLine.class, format);
			DataLine.Info infoTarget = new DataLine.Info(TargetDataLine.class, format);
			if (!AudioSystem.isLineSupported(infoSource)|| !AudioSystem.isLineSupported(infoTarget)) 
			{
				System.out.println("unsupported line");
			}
			try
			{
				sourceLine = (SourceDataLine) AudioSystem.getLine(infoSource);
				targetLine = (TargetDataLine) AudioSystem.getLine(infoTarget);
				sourceLine.open(format);
				targetLine.open(format);
			}catch (LineUnavailableException lex){
				System.out.println("unavailable line");
				return;
			}
			ByteArrayOutputStream out  = new ByteArrayOutputStream();
			int numBytesRead;
			byte[] data = new byte[sourceLine.getBufferSize() / 5];
			sourceLine.start(); //on commence la capture
			while (!stopped) {
			   numBytesRead =  targetLine.read(data, 0, data.length);
			   out.write(data, 0, numBytesRead);
			}
			sourceLine.drain();
			sourceLine.stop();
			stopped = true ;
			sourceLine.close();
			sourceLine = null;
	}	
}
