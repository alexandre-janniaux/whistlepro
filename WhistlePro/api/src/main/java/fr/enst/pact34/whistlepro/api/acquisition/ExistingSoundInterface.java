package fr.enst.pact34.whistlepro.api.acquisition;

import java.io.IOException;

public interface ExistingSoundInterface 
{
	public WavFile read(String pathName) throws IOException, WavFileException; //lit le fichier son
	
	/* public void play(WavFile wavfile); //joue le fichier son */
	
	public void printSound(); //affiche le fichier
}
