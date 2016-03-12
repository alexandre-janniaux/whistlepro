package fr.enst.pact34.whistlepro.api.acquisition;

import java.io.File;

import fr.enst.pact34.whistlepro.api.Attaque.Enveloppe;

public class Test {

	public static void main(String[] args) {
		
		File file = new File("data/testVoice.wav");
		double[] x = ReadExample.audioRead(file);
		
		double[] e = Enveloppe.enveloppe(0.99, x);
		
		Affichage2.affichage(e, "test");
		
	}

}
