package fr.enst.pact34.whistlepro.api.acquisition;

import java.io.File;
import java.util.ArrayList;

public class TestAttaque {
	
	public double[] trapeze(int N) {
		
		double fs = 44100;
		ArrayList<Double> trapz = new ArrayList<Double>();
		for (int i=0; i<N; i++) {
			trapz.add((double) 0);
		}
		for (int j=0; j<N; j++) {
			trapz.add((double) j);
		}
		for (int k=0; k<N; k++) {
			trapz.add((double) 1);
		}
		for (int l=N-1; l>=0; l--) {
			trapz.add((double) l);
		}
		
		int length = trapz.size();
		double[] trapzDouble = new double[length];
		for (int i=0; i<length; i++) {
			trapzDouble[i] = (double) trapz.get(i);
		}
		
		return trapzDouble;
		
	}

	public static void main(String[] args) {
				
		File file = new File("data/testVoice.wav");
		
		//double[] x = ReadExample.audioRead(file);
		//System.out.println(x);
		
		
		double[] e = Enveloppe.enveloppe(0.99, x);
		

		
	}

}
