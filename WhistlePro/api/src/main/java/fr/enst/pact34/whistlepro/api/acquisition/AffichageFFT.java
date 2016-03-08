package fr.enst.pact34.whistlepro.api.acquisition;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

public class AffichageFFT {

	public static double[] affichageFFT_C(Complex[] x, String name)
	{
		double[] y = new double[(int)Math.pow(2,20)];
		for(int j=0;j<(int)Math.pow(2,20);j++)
		{
			if(j<x.length-1)
			{
				y[j] = x[j].getReal();
			}
			else
			{
				y[j]=0;
			}
		}	

		FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);		

		Complex[] res1 = fft.transform(y,TransformType.FORWARD);

		double[] mod_res = new double[res1.length];

		for (int i= 0; i<res1.length; i++)
		{			
			mod_res[i]=20*Math.log10(res1[i].abs());
		}	

		return mod_res;
	}	

	public static double[] affichageFFT_R(double[] x, String name)
	{
		double[] y = new double[(int)Math.pow(2,13)];
		for(int j=0;j<(int)Math.pow(2,13);j++)
		{
			if(j<x.length-1)
			{
				y[j] = x[j];
			}
			else
			{
				y[j]=0;
			}
		}	

		FastFourierTransformer fft = new FastFourierTransformer(DftNormalization.STANDARD);

		Complex[] res1 = fft.transform(y,TransformType.INVERSE);

		double[] mod_res = new double[res1.length];

		for (int i= 0; i<res1.length; i++)
		{			
			mod_res[i]=res1[i].abs();
		}	

		return mod_res;
	}


}
