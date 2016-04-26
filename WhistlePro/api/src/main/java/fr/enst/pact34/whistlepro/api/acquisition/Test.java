package fr.enst.pact34.whistlepro.api.acquisition;

import java.io.File;

import fr.enst.pact34.whistlepro.api.Attaque.Enveloppe;

public class Test {

	public static void main(String[] args) {

		double[] t = new double[100000];
		double[] x = new double[t.length];
		for (int k=0; k<t.length; k++) {
			t[k] = k;
			x[k] = Math.sin(t[k]/2000);
		}

		Synthesis spectre = new Synthesis();
		//double[] testSpectre = spectre.spectrum(x);

		Affichage2 affich = new Affichage2();
		affich.affichage(x,"testSinus");
		spectre.printSpectrum(x);
	}

}
