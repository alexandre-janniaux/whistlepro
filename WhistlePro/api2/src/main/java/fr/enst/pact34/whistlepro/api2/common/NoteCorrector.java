package fr.enst.pact34.whistlepro.api2.common;

/**
 * Created by mms on 26/04/16.
 */
public class NoteCorrector {

    private static final double[] freqs =
            {
                    // gamme 0 : trop lent
                    //32.7,34.7,36.7,38.9,41.2,43.7,46.3,49.0,51.9,55.0,58.3,61.7,
                    65.4,69.3,73.4,77.8,82.4,87.3,92.5,98.0,103.8,110.0,116.5,123.5,//};/*,
                    130.8,138.6,146.8,155.6,164.8,174.6,185.0,196.0,207.6,220.0,233.1,247.0,
                    261.6,277.2,293.7,311.1,329.6,349.2,370.0,392.0,415.3,440.0,466.2,493.9,
                    523.2,554.4,587.4,622.2,659.2,698.4,740.0,784.0,830.6,880.0,932.3,987.8,
                    1046.4,1108.8,1174.7,1244.5,1318.4,1396.8,1480.0,1568.0,1661.1,1760.0,1864.6,1975.7,
                    2092.8,2217.6,2349.4,2489.0,2636.8,2793.6,2960.0,3136.0,3322.2,3520.0,3729.3,3951.4,
                    //, // gamme 7 : trop d'imprecision
                    4185.6,4435.2,4698.9,4977.9,5273.6,5587.2,5920.0,6272.0,6644.5,7040.0,7458.6,7902.7


            };//*/

    public static final double[] getNoteFreqs()
    {
        return freqs;
    }

    public static double closestNoteFreq(double freq)
    {
        int iLow = 0, iHigh = freqs.length;
        int iAct ;

        if(freq < freqs[0]) return  freqs[0];
        if(freq > freqs[freqs.length-1]) return  freqs[freqs.length-1];

        while(iHigh-iLow > 1)
        {
            iAct = (iHigh+iLow)/2;

            if(freq < freqs[iAct])
            {
                iHigh = iAct;
            }
            else if( freqs[iAct] < freq)
            {
                iLow = iAct;
            }
            else //freqs[iAct] == freq
            {
                return freqs[iAct];
            }

        }

        double seuil = Math.sqrt(freqs[iHigh]*freqs[iLow]);
        if( seuil <= freq)
            return freqs[iHigh];
        else
            return freqs[iLow];

    }

    public static int getNoteFromFreq(double freq)
    {
        int iLow = 0, iHigh = freqs.length;
        int iAct ;

        if(freq < freqs[0]) return  0;
        if(freq > freqs[freqs.length-1]) return  freqs.length-1;

        while(iHigh-iLow > 1)
        {
            iAct = (iHigh+iLow)/2;

            if(freq < freqs[iAct])
            {
                iHigh = iAct;
            }
            else if( freqs[iAct] < freq)
            {
                iLow = iAct;
            }
            else //freqs[iAct] == freq
            {
                return iAct;
            }

        }

        double seuil = Math.sqrt(freqs[iHigh]*freqs[iLow]);
        if( seuil <= freq)
            return iHigh;
        else
            return iLow;
    }

}
