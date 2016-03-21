package fr.enst.pact34.whistlepro.api.acquisition;

public class TestSynthese {

	public static void main(String[] args) {
		
		int length = 8000;
		int Fe = 10000;
		double d = 2850;
		double m = 1;
		double r = 1;
		double[] fm = new double[length];
		double[] fp = new double[length];
		
		for (int k=0; k<length; k++) {
			fm[k] = NotesDeMusique.la3();
			fp[k] = fm[k] * r;
		}
		
		/*double[] x = fr.enst.pact34.whistlepro.api.Synthese.SyntheseFM.oscFM(fm, d, Fe);
		
		System.out.println(x);
		
		double[] y = fr.enst.pact34.whistlepro.api.Synthese.SyntheseFM.instFM(fp, fm, d, m, Fe);
		
		System.out.println(y);*/

	}

}
