

public class SyntheseFM {
	
	//Synthese numerique d'un sinus modulé en fréquence
	public static double[] oscFM(double[] f, double m, int Fe) {
		
		int length = f.length;
		double[] phi = new double[length];
		double[] x = new double[length];
		
		for (int n = 0; n<length; n++) {	//calcul de la sommu cumulée de phi
			double fCumul = 0;
			
			for (int k = 0; k<n; k++) {		//calcul de la somme sur f
				fCumul = fCumul + f[k];
			}
			
			phi[n] = 2*Math.PI/Fe*fCumul;
			x[n] = m*Math.cos(phi[n]);		//calculer le signal x(n)=m*cos(phi)
		}
		
		return x;	
	}
	
	//Synthèse numérique d'un instrument FM
	public static double[] instFM(double [] fp, double[] fm, double d, double m, int Fe) {
		
		int length = fm.length;
		double[] x1 = new double[length];
		double[] osc = oscFM(fm,d,Fe);	//osc a la même longueur que fm, donc de x1
		for (int k = 0; k<length; k++) {
			x1[k] = fp[k] + osc[k];
		}
		double[] x = oscFM(x1,m,Fe);
		
		return x;
	}

}
