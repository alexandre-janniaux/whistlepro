import java.io.ByteArrayOutputStream;
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
			
		}
		return null;
	}

	public void captureAndPlay()
	{
			AudioFormat format = getAudioFormat();
			SourceDataLine sourceLine;
			TargetDataLine targetLine;
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
			sourceLine.close();
			sourceLine = null;
	}	
}
