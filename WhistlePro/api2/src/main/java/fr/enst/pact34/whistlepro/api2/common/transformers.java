package fr.enst.pact34.whistlepro.api2.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.SignalGetInterface;
import fr.enst.pact34.whistlepro.api2.dataTypes.SignalSetInterface;


/**
 * Created by Mohamed  on 25/02/16.
 * Implementations following MATLAB method.
 */
public class transformers {

    public static void fft(SignalGetInterface sig, SignalSetInterface fft) {
        int N = sig.length();

        // TODO avoid using new

        double fft_c[] = new double[N];
        double fft_s[] = new double[N];

        for (int k = 0; k < N; k++) {
            fft_c[k] = 0;
            fft_s[k] = 0;

            for (int j = 0; j < N; j++) {
                double tmp = -2.0 * (Math.PI * j * k) / N;
                fft_c[k] = fft_c[k] + sig.getValue(j) * Math.cos(tmp);
                fft_s[k] = fft_s[k] + sig.getValue(j) * Math.sin(tmp);
            }
        }

        //reusing fft_c for result
        fft.setLength(N / 2);
        for (int i = 0; i < N / 2; i++) fft.setValue(i, 2.0 * Math.sqrt(fft_c[i] * fft_c[i] + fft_s[i] * fft_s[i]) / N);
    }
}
