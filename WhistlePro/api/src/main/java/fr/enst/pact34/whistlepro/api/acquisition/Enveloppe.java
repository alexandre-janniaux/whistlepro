


import java.util.ArrayList;

public class Enveloppe {
	
	public static double[] enveloppe(double a, double[] x) {
		int len = x.length;
		double[] e = new double[len];
		e[0] = x[0];
		for (int i = 0; i<len; i++) {
			e[i+1] = a*e[i] + (1-a)*(x[i+1]*x[i+1]);	
		}
		return e;
	}
	
	public static double[] sousEchantillonne(int n, double[] x) {
		int l = x.length;
		int compteur = 0;
		double[] x2 = new double[l/n];
		for (int k=0; k<l; k++) {
			compteur++;
			if (compteur==n) {
				compteur = 0;
				x2[k/n] = x[k];
			}
		}
		return x2;
	}
	
	public static double[] derive(int N, double[] x) {
		
		ArrayList<Double> derive = new ArrayList<Double>();
		
		for (int k=2; k<x.length-2*N; k++) {
			double d1 = 0;
			double d2 = 0;
			
			for (int i=0; i<N-1; i++) {
				d1 = d1 + x[2*N-1+k-i]; 
			}
			
			for (int j=N; j<2*N-1; j++) {
				d2 = d2 + x[2*N-1+k-j]; 
			}
			derive.add(d1-d2);
		}
		
		int length = derive.size();
		double[] deriveDouble = new double[length];
		for (int i=0; i<length; i++) {
			deriveDouble[i] = (double) derive.get(i);
		}
		
		return deriveDouble;
		
	}

}
