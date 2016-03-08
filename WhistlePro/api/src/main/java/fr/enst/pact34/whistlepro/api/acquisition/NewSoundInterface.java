
public interface NewSoundInterface 
{
	public WavFile captureAndSave(String name, String dossier); //capture le son
	
	public void captureAndPlay(); //capture et lit "en live" le son
}
