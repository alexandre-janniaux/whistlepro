package fr.enst.pact34.whistlepro.api.common;

import org.apache.commons.math3.complex.Complex;

/**
 * Created by Mohamed  on 25/02/16.
 * Implémentations following MATLAB method.
 */
public class transformers {

    public static double[] fft(double sig[])
    {
        Complex fft[] = new Complex[sig.length];


        for (int k = 0; k < fft.length; k++)
        {
            fft[k] = new Complex(0);
            for(int j = 0; j < sig.length; j++)
            {

                fft[k]=fft[k].add(new Complex(0.0,-2.0*Math.PI*j*k/sig.length).exp().multiply(sig[j]));//Complex.valueOf(0.0,-2.0*Math.PI*j*k/sig.length).exp().multiply((double)(sig[j])));

            }
        }

        double fftR[] = new double[fft.length];
        for(int i = 0; i < fft.length;i ++) fftR[i] = fft[i].abs();

        return fftR;
    }


    public static double[] dct(double[] signal) {
        int N = signal.length;
        double dct[] = new double[N];
        dct[0] = 0;
        for(int n = 0; n < N; n++)
        {
            dct[0] += signal[n]* 1;//1=cos(0)=>Math.cos((Math.PI*(2*n+1)*k)/(2*N)) k = 0;
        }
        dct[0]= dct[0]/Math.sqrt(N);

        for(int k = 1; k < N ; k++)
        {
            dct[k] = 0;
            for(int n = 0; n < N; n++)
            {
                dct[k] += signal[n]*Math.cos((Math.PI*(2*n+1)*k)/(2*N));
            }
            dct[k]= (dct[k]*Math.sqrt(2))/Math.sqrt(N);
        }
        return dct;
    }
}
