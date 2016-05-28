package fr.enst.pact34.whistlepro.api2.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.dataTypes.Spectrum;

import static java.lang.StrictMath.ceil;

/**
 * Created by mms on 23/04/16.
 */
public class FFTCalculator {

    private final int fftLength ;
    private final int siglength ;
    private double[][] cos_table,sin_table;

    public FFTCalculator(int sigInlength)
    {
        this.siglength = sigInlength;
        fftLength = (int) ceil((sigInlength + 1) / 2);
        double cste =  -2.0 * Math.PI / sigInlength;
        cos_table = new double[fftLength][siglength];
        sin_table = new double[fftLength][siglength];
        for (int k = 0; k < fftLength; k++) {
            double tmp = cste * k;
            for (int j = 0; j < siglength; j++) {
                double tmp2 = tmp * j; //-2.0 * (Math.PI * j * k) / N;
                cos_table[k][j] = Math.cos(tmp2);
                sin_table[k][j] = Math.sin(tmp2);
            }
        }
        tmp_sig = new double[sigInlength];
        cst_tmp = 2.0 / siglength;
    }

    private double[] tmp_sig;
    private double fft_ctmp;
    private double fft_stmp;
    private double cst_tmp ;

    public void fft(Signal sig, Spectrum fft, int start, int stop) {
        fft.setLength(fftLength);
        fft.setNbPtsSig(siglength);
        fft.setFs(sig.getSamplingFrequency());
        sig.fillArray(tmp_sig, start, stop);

        for (int k = 0; k < fftLength; k++) {
            fft_ctmp = 0;
            fft_stmp = 0;
            for (int j = 0; j < Math.min(siglength, stop-start); j++) {
                fft_ctmp += tmp_sig[j] * cos_table[k][j];
                fft_stmp += tmp_sig[j] * sin_table[k][j];
            }

            fft.setValue(k, cst_tmp* Math.sqrt(fft_ctmp*fft_ctmp + fft_stmp*fft_stmp) / siglength );
        }
    }

    public void fft(Signal sig, Spectrum spectrum) {
        int stop = sig.length();
        fft(sig, spectrum, 0, stop);
    }
}
