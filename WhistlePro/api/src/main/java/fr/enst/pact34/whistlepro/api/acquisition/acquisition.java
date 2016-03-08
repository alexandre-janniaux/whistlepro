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
    private SourceDataLine sourceLine;
    private byte[] buffer = new byte[sourceLine.getBufferSize() / 5];
    public acquisition()
    {
        this.format = new AudioFormat(
                16000, // SAMPLE RATE
                32, // SAMPLE SIZE IN BYTE
                1, // CHANNELS
                true, // SIGNED
                true // BIGENDIAN
        );
    }
    
    public void startRecording()
    {
    	DataLine.Info infoTarget = new DataLine.Info(TargetDataLine.class, format);
    	try {
			dataLine = (TargetDataLine) AudioSystem.getLine(infoTarget);
			this.dataLine.open(this.format);
		} catch (LineUnavailableException lex){
			System.out.println("unavailable line");
			return;
		}
    }

    public double[] readData()
    {
    	DataLine.Info infoSource = new DataLine.Info(SourceDataLine.class, format);
    	DataLine.Info infoTarget = new DataLine.Info(TargetDataLine.class, format);
		if (!AudioSystem.isLineSupported(infoSource)|| !AudioSystem.isLineSupported(infoTarget)) 
		{
			System.out.println("unsupported line");
		}
    	dataLine.read(this.buffer, 0, sourceLine.getBufferSize() / 5); // BUFFER, START, LENGTH (blocking)
        double[] output = new double[sourceLine.getBufferSize() / 5];
        for(int i=0; i<this.buffer.length; ++i)
        {
            output[i] = (double)buffer[i];
        }
        return output;
    }

    public void stopRecording()
    {
        dataLine.close();
    } 
	
}

