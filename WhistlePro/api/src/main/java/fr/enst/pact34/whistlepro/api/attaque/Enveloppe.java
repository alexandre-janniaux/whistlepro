package fr.enst.pact34.whistlepro.api.attaque;

import java.util.ArrayList;

public class Enveloppe {

	//extraction de l'enveloppe par la methode du reservoir d'energie
	public static double[] enveloppe(double a, double[] x) {
		int len = x.length;
		double[] e = new double[len];
		e[0] = x[0];
		for (int i = 0; i<len-1; i++) {	//application du filtre recursif
			e[i+1] = a*e[i] + (1-a)*(x[i+1]*x[i+1]);	
		}
		return e;
	}

	//sous-echantillonnage pour diminuer la quantite de calculs en respectant le critere de Shannon-Nyquist
	public static double[] sousEchantillonne(int n, double[] x) {
		int l = (int)Math.floor(x.length/n)+1;
		int i = 0;
		double[] x2 = new double[l];
		for (int k=0; k<x.length; k+=n) {
			x2[i] = x[k];
			i++;
		}
		return x2;
	}

	//calcul de la derive "large"
	public static double[] derive(int N, double[] x) {
		
		ArrayList<Double> derive = new ArrayList<Double>();
		
		for (int k=1; k<x.length-2*N-1; k++) {
			double d1 = 0;
			double d2 = 0;

			//on calcule la somme des N coefficients precedents et on lui soustrait la somme des N coefficients les precedents
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
