package fr.enst.pact34.whistlepro.api.Attaque;

import java.io.File;
import java.util.ArrayList;

import fr.enst.pact34.whistlepro.api.acquisition.Affichage2;
import fr.enst.pact34.whistlepro.api.acquisition.ReadExample;
import fr.enst.pact34.whistlepro.api.common.Convolution1D;

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
				
		File file = new File("data/Jean caisse claire/Jean caisse claire 2.wav");

		//Pics pics = new Pics();

		Affichage2 affich = new Affichage2();

		//double[] attaque = pics.tAttaque(file);
		//affich.affichage(attaque, "fonction stylée");
		
		double[] x = ReadExample.audioRead(file);
		//System.out.println(x);
		affich.affichage(x, "signal");
		
		double[] e = Enveloppe.enveloppe(0.99, x);
		//affich.affichage(e, "Détection de l'enveloppe");

		double[] e2 = Enveloppe.sousEchantillonne(200,e);
		//affich.affichage(e2,"Enveloppe ssEch");


		///FONCTION DERIVATION AVEC CONVOLUTION///
		/*Convolution1D convolution;

		double[] kernel = new double[20];
		for(int i=0; i<20; ++i) {
			kernel[i] = i < 10 ? 1 : -1;
		}
		convolution = new Convolution1D(0, kernel);

		//double[] derive = convolution.convoluate(e2,0, e2.length);*/


		double[] derive = Enveloppe.derive(10,e2);
		//affich.affichage(derive,"fonction de derivation");
		//for (int i=0; i<derive.length; i++) {
		//	System.out.println(derive[i]);
		//}

		Pics pics = new Pics();
		double[] y = pics.DetectionPics(derive);
		//affich.affichage(y,"Pics d'attaque");
		for (int i=0; i<y.length; i++) {
			System.out.println(y[i]/(44100/200));
		}
	}

}
