package fr.enst.pact34.whistlepro.api.acquisition;

import java.util.ArrayList;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

public abstract class Synthesis implements SynthesisInterface {

	public void synthesize(int f) // f is the note number
	{
		try {
	        Synthesizer synthesizer = MidiSystem.getSynthesizer();
	        synthesizer.open();
	        MidiChannel[] channels = synthesizer.getChannels();
	        channels[0].noteOn(f, 60); //60 is the velocity of the sound
	        Thread.sleep(200);
	        channels[0].noteOff(f, 60);
	        Thread.sleep(200);
	        synthesizer.close();
	    } catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}

	@Override
	public ArrayList spectrum() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printSpectrum() {
		// TODO Auto-generated method stub

	}
	
}

