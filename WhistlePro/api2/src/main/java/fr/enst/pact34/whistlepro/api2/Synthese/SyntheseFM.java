package fr.enst.pact34.whistlepro.api2.Synthese;

public class SyntheseFM {
	
	//Synthese numerique d'un sinus modulé en fréquence
	private static double[] oscFM(double[] f, double[] m, double Fe) {

		int length = f.length;
		double[] x = new double[length];

		double fCumul = 0;
		for (int n = 0; n<length; n++) {	//calcul de la somme cumulée de phi

			//for (int k = 0; k<n; k++) {		//calcul de la somme sur f
				fCumul = fCumul + f[n];
			//}
			x[n] = m[n]*Math.cos(2*Math.PI/Fe*fCumul);		//calculer le signal x(n)=m*cos(phi)
			/*
			phi[n] = 2*Math.PI/Fe*fCumul;
			x[n] = m[n]*Math.cos(phi[n]);
			 */
		}
		
		return x;	
	}
	
	//Synthèse numérique d'un instrument FM
	public static double[] instFM(int length, double Fe, double Freq, double r, double m_in) {

		//double r = 1;
		double[] fm = new double[length];
		//double[] fp = new double[length];
		double[] m = new double[length];
		double[] d = new double[length];

		for (int k=0; k<length; k++) {
			fm[k] = Freq;
			//fp[k] = fm[k] / r;
			m[k] = m_in; //m[k] = 1;
			d[k] = fm[k]*m[k];
		}

		double[] x1 = new double[length];
		double[] osc = oscFM(fm,d,Fe);	//osc a la même longueur que fm, donc de x1
		for (int k = 0; k<length; k++) {
			x1[k] = Freq/r + osc[k] ;//fp[k] + osc[k];
		}
		double[] x = oscFM(x1,m,Fe);

		int i_m = 40;
		for (int i = 0; i < i_m && i <x.length; i++) {
			x[i]*= ((double)i+1)/i_m;
			x[x.length-i-1]*= ((double)i+1)/i_m;
		}

		return x;
	}

}
