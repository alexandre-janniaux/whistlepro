package fr.enst.pact34.whistlepro.api.common;

import org.apache.commons.math3.complex.Complex;

import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * Created by Mohamed  on 25/02/16.
 * Implementations following MATLAB method.
 */
public class transformers {

    public static double[] fft(double sig[])
    {
        int N = sig.length;
        double fft_c[] = new double[N];
        double fft_s[] = new double[N];

        for (int k = 0; k < N; k++)
        {
            fft_c[k] = 0;
            fft_s[k] = 0;

            for(int j = 0; j < N; j++)
            {
                double tmp = -2.0*(Math.PI*j*k)/N;
                fft_c[k] = fft_c[k]+ sig[j]*Math.cos(tmp);
                fft_s[k] = fft_s[k]+ sig[j]*Math.sin(tmp);
            }
        }

        //reusing fft_c for result
        for(int i = 0; i < N;i ++) fft_c[i] = 2.0*Math.sqrt(fft_c[i]*fft_c[i]+fft_s[i]*fft_s[i])/N;

        return fft_c;
    }

    public static double[] fft_matlab(double sig[])
    {
        int N = sig.length;
        double fft_c[] = new double[N];
        double fft_s[] = new double[N];

        for (int k = 0; k < N; k++)
        {
            fft_c[k] = 0;
            fft_s[k] = 0;

            for(int j = 0; j < N; j++)
            {
                double tmp = -2.0*(Math.PI*j*k)/N;
                fft_c[k] = fft_c[k]+ sig[j]*Math.cos(tmp);
                fft_s[k] = fft_s[k]+ sig[j]*Math.sin(tmp);
            }
        }

        //reusing fft_c for result
        for(int i = 0; i < N;i ++) fft_c[i] = Math.sqrt(fft_c[i]*fft_c[i]+fft_s[i]*fft_s[i]);

        return fft_c;
    }


    public static double[] arrayToDouble(ArrayList<Double> array) {
        double[] output = new double[array.size()];
        for(int i=0; i<array.size(); ++i) output[i] = array.get(i);
        return output;
    }

    public static ArrayList<Double> doubleToArray(double[] array) {
        ArrayList<Double> output = new ArrayList<>();
        output.ensureCapacity(array.length);
        for(int i=0; i<array.length; ++i) output.set(i, array[i]);
        return output;
    }

    public static double[] dct(double[] signal, int nbCoeffs) {
        double dct[] = new double[nbCoeffs];
        if(nbCoeffs == 0) return dct;
        int N = signal.length;
        dct[0] = 0;
        for(int n = 0; n < N; n++)
        {
            dct[0] += signal[n]* 1;//1=cos(0)=>Math.cos((Math.PI*(2*n+1)*k)/(2*N)) k = 0;
        }
        dct[0]= dct[0]/Math.sqrt(N);

        for(int k = 1; k < nbCoeffs ; k++)
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
