package fr.enst.pact34.whistlepro.api2.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.SignalGetInterface;
import fr.enst.pact34.whistlepro.api2.dataTypes.SignalSetInterface;
import fr.enst.pact34.whistlepro.api2.dataTypes.Spectrum;

import static java.lang.StrictMath.ceil;
import static java.lang.StrictMath.floor;
import static java.lang.StrictMath.round;


/**
 * Created by Mohamed  on 25/02/16.
 * Implementations following MATLAB method.
 */
public class transformers {

    double[] fft_c= new double[1],fft_s= new double[1] ;
    public void fft(SignalGetInterface sig, Spectrum fft) {
        int N = sig.length();


        fft.setLength((int) ceil((N + 1) / 2));
        fft.setNbPtsSig(N);
        fft.setFs(sig.getSamplingFrequency());

        int fftLen = fft.length();

        // TODO avoid using new (almost done)
        if(fft_c.length < fftLen) fft_c = new double[fftLen];
        if(fft_s.length < fftLen) fft_s = new double[fftLen];

        double cste = -2.0 * Math.PI / N;
        for (int k = 0; k < fftLen; k++) {
            fft_c[k] = 0;
            fft_s[k] = 0;
            double tmp = cste * k;
            for (int j = 0; j < N; j++) {
                double tmp2 = tmp * j; //-2.0 * (Math.PI * j * k) / N;
                fft_c[k] = fft_c[k] + sig.getValue(j) * Math.cos(tmp2);
                fft_s[k] = fft_s[k] + sig.getValue(j) * Math.sin(tmp2);
            }
        }

        for (int i = 0; i < fft.length(); i++) fft.setValue(i, 2.0 * Math.sqrt(fft_c[i] * fft_c[i] + fft_s[i] * fft_s[i]) / N);
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
