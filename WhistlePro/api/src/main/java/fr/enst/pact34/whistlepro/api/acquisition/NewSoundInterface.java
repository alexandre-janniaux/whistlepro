package fr.enst.pact34.whistlepro.api.acquisition;

public interface NewSoundInterface 
{
	public WavFile captureAndSave(String name, String dossier); //capture le son
	
	public void captureAndPlay(); //capture et lit "en live" le son
}
