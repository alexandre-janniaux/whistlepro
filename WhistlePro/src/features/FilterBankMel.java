package features;

/**
 * Created by Mohamed  on 25/02/16.
 */
public class FilterBankMel {


    public static double frequencyToMel(double frequency)
    {
        return 1125*Math.log(1+frequency/700.);
    }

    public static double melToFrequency(double mel)
    {
        return 700*(Math.exp(mel/1125)-1);
    }

    private static double[] computeFilterFrequencies(double minFrequency, double maxFrequency,int nbMelFilter )
    {
        double minMel = frequencyToMel(minFrequency);
        double maxMel = frequencyToMel(maxFrequency);
        double stepMel = (maxMel-minMel)/(nbMelFilter+1) ;

        double[] frequencies = new double[nbMelFilter+2];
        for(int i=0; i < nbMelFilter+2; ++i)
        {
            frequencies[i] = melToFrequency(minMel+i*stepMel);
        }
        return frequencies;

    }

    private static double[] computeTriangleFilter(double[] echelleFreq, double fmin, double fcenter, double fmax)
    {
        double filter[] = new double[echelleFreq.length];

        double a1 = 1.0/(fcenter-fmin);
        double b1 = -fmin*a1;

        double a2 = -1.0/(fmax-fcenter);
        double b2 = -fmax*a2;

        for(int i = 0; i < echelleFreq.length; i++)
        {
            if(echelleFreq[i] <= fmin || echelleFreq[i]>= fmax)
            {
                filter[i] = 0;
            }
            else
            {
                if(echelleFreq[i] <= fcenter )
                {
                    filter[i] = a1*echelleFreq[i]+b1;
                }
                else
                {
                    filter[i] = a2*echelleFreq[i]+b2;
                }
            }
        }

        return filter;
    }

    private static  double[][] computeFilterBank(double[] frequencies, double[] echelleFreq)
    {
        double filters[][] = new double[frequencies.length-2][];

        for(int i = 1; i < frequencies.length-1; i++)
        {
            filters[i-1] = computeTriangleFilter(echelleFreq,frequencies[i-1],frequencies[i],frequencies[i+1]);
        }

        return filters;
    }

    public static double[][] computeFilterBank(double minFrequency, double maxFrequency, int nbMelFilter, double[] echelleFreq)
    {
        return  computeFilterBank(computeFilterFrequencies(minFrequency, maxFrequency,nbMelFilter),echelleFreq);
    }
}
