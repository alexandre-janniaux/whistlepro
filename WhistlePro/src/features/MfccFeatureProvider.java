package features;

import classification.FeatureProviderInterface;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.math3.transform.FastCosineTransformer;
import org.apache.commons.math3.transform.DctNormalization;
import org.apache.commons.math3.transform.TransformType;

public class MfccFeatureProvider 
    implements FeatureProviderInterface
{

    private int nbMelFilter = 24;

    public int countFeatures() 
    {
        // MFCC + d(MFCC) + dd(MFCC)
        return 13; //+ 13 + 13;
    }

    public ArrayList<Double> processMfcc(double[] spectrum)
    {
        // COMPUTE THE POWER SPECTRUM
        for(int i=0; i<spectrum.length; ++i)
            spectrum[i] = spectrum[i]*spectrum[i]; 
        

        /* MEL FILTER
        for(int i = 0; i < nbMelFilter; ++i)
        {
            
        }*/
        
        double[] melSignal = spectrum; // TODO : compute mel filtered signal

        for(Double x : melSignal) x = Math.log(x);

        // DCT TRANSFORM
        FastCosineTransformer dct = new FastCosineTransformer(DctNormalization.STANDARD_DCT_I);

        double mfcc[] = dct.transform(melSignal, TransformType.FORWARD);
        
        ArrayList<Double> coeffs = new ArrayList<>();
        coeffs.ensureCapacity(13);
        for(int i=0; i<13; ++i)
            coeffs.add(mfcc[i]);

        return coeffs;
    }

}
