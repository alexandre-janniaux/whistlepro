package fr.enst.pact34.whistlepro.api.acquisition;

import java.util.ArrayList ;

public interface SynthesisInterface 
{
	public void synthesize(int f);
	
	public ArrayList spectrum();
	//fastFourierTransformer transform
	public void printSpectrum();
}
