package fr.enst.pact34.whistlepro.api.Synthese;

public class TestSynthese {

	public static void main(String[] args) {
		
		int length = 8000;
		int Fe = 10000;
		double r = 1;
		double[] fm = new double[length];
		double[] fp = new double[length];
		double[] m = new double[length];
		double[] d = new double[length];
		
		for (int k=0; k<length; k++) {
			fm[k] = NotesDeMusique.la3();
			fp[k] = fm[k] * r;
			m[k] = 1;
			d[k] = fm[k]*m[k]*4;
		}
		
		/*double[] x = SyntheseFM.oscFM(fm,d,Fe);
		
		System.out.println(x);
		
		double[] y = SyntheseFM.instFM(fp, fm, d, m, Fe);
		
		System.out.println(y);*/

		SyntheseFM synth = new SyntheseFM();

		double[] x = synth.oscFM(fm,d,Fe);

		double[] y = synth.instFM(fp,fm,d,m,Fe);
		for (int k = 0; k<y.length; k++) {
			System.out.println(y[k]);
		}
	}

}
