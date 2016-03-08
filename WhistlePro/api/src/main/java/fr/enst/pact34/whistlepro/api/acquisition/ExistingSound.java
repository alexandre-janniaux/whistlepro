package fr.enst.pact34.whistlepro.api.acquisition;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;
import java.net.URL;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;

public abstract class ExistingSound implements ExistingSoundInterface, NewSoundInterface 
{
	public WavFile read(String pathName) //ouvre le fichier wav a l'adresse pathName
	{
		WavFile wavFile = null; 
		try
		{
		    wavFile = WavFile.openWavFile(new File(pathName));
		}
		catch (Exception e)
		{
		    System.err.println(e);
		}
		
		return(wavFile);
		}

	/*public void play(WavFile wavFile) 
	{
		String name = "unusedName"; 
		saveSound(wavFile, name, "bin");
		URL url = ExistingSound.class.getResource(name);
		AudioClip ac = Applet.newAudioClip(url);
		ac.play();
		try {
		    Files.delete(Paths.get("bin/unusedName"));
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
	} */
	

	public void printSound() 
	{
		WavFile wavFile = null ;
		try
		{
			wavFile.display();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}

}
