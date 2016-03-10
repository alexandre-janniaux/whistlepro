package fr.enst.pact34.whistlepro.api.Attaque;

import java.io.File;
import java.util.ArrayList;

import fr.enst.pact34.whistlepro.api.acquisition.Affichage2;
import fr.enst.pact34.whistlepro.api.acquisition.ReadExample;

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
		
		double[] x = ReadExample.audioRead(file);
		//System.out.println(x);
		//Affichage2.affichage(x, "signal");
		
		double[] e = Enveloppe.enveloppe(0.99, x);
		//Affichage2.affichage(e, "Détection de l'enveloppe");

		double[] e2 = Enveloppe.sousEchantillonne(200,e);
		//Affichage2.affichage(e2,"Enveloppe ssEch");

		double[] derive = Enveloppe.derive(10,e2);
		Affichage2.affichage(derive,"fonction de derivation");
	}

}
