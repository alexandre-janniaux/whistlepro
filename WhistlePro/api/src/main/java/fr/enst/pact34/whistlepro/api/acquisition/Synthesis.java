package fr.enst.pact34.whistlepro.api.acquisition;

import java.util.ArrayList;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

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
	public double[] spectrum(double[] x) {
		double[] y = new double[(int)Math.pow(2,20)];
		for(int j=0;j<(int)Math.pow(2,20);j++)
		{
			if(j<x.length-1)
			{
				y[j] = x[j];
			}
			else
			{
				y[j]=0;
			}
		}	

		FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);		

		Complex[] res1 = fft.transform(y,TransformType.FORWARD);

		double[] mod_res = new double[res1.length];

		for (int i= 0; i<res1.length; i++)
		{
			mod_res[i]=20*Math.log10(res1[i].abs());
		}	
		return mod_res;

	}

	@Override
	public void printSpectrum(double[] x) {
		
		double[] y = spectrum(x);
		Affichage2.affichage(y, "FFT de x");

	}
	
}

