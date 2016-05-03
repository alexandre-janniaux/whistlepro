package fr.enst.pact34.whistlepro.api2.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

/**
 * Created by mms on 23/04/16.
 */
public class FreqProcess implements StreamProcessInterface<Signal,Frequency> {

    @Override
    public void process(Signal inputData, Frequency outputData) {
        /* Auto-correlation

        long t = System.currentTimeMillis();

        double[] res = new double[inputData.length()];
        double max = - Double.MAX_VALUE;
        int i_max = 0;

        for (int i = 0; i < res.length; i++) {
            res[i] = 0;
            for (int j = 0; j < res.length  ; j++) {
                res[i]+=inputData.getValue(j)*inputData.getValue((i+j)%res.length);
            }
            res[i]/=(i);
            if(i > 0 && res[i]> max && res[i]> res[i-1]) {
                max =res[i];
                i_max= i;
            }
        }

        double freq = inputData.getSamplingFrequency()/i_max;
        System.out.println(System.currentTimeMillis()-t);
        //*/



//*     Circular AMDF (Average Magnitude Difference Function)
//      + aumentation Fs artificielle by Momo
//      fonctionne mieux comme ça (entre 65 Hz et 3951 soit les octave 1 à 6)
//      octave 1 très sensible au bruit
        int xFs = (int) Math.ceil(100000.0/inputData.getSamplingFrequency());

        double[] sig = new double[inputData.length()*xFs];
        double[] res = new double[sig.length];

        //long t = System.currentTimeMillis();
        for (int i = 0; i < inputData.length()-1; i++) {
            for (int j = 0; j < xFs; j++) {
                //interpolation lineaire
                sig[i*xFs+j] =  (inputData.getValue(i+1)-inputData.getValue(i))*((double)(j)/xFs)+inputData.getValue(i);
            }
        }

        double max = Double.MAX_VALUE;
        int i_max = 0;

        for (int i = 0; i < res.length; i++) {
            res[i] = 0;
            for (int j = 0; i+j < res.length ; j++) {
                res[i]+=Math.abs(sig[j%res.length]-sig[(i+j)%res.length]);
            }
            res[i]/=(i); // <= ca aussi by momo, fonctionne mieux (quand y a du bruit)
            if(i > 0 && res[i]< max && res[i]< res[i-1]) {
                //recherche d'un pic en bas
                max =res[i];
                i_max= i;
            }
            //arret au premier pic en bas
            if(i_max != 0 && res[i] > res[i-1]) break;
        }


        double freq = -1;
        if(i_max>0) freq= inputData.getSamplingFrequency()*xFs/i_max;
        //System.out.println(System.currentTimeMillis()-t);
//*/

        outputData.setFrequency(NoteCorrector.closestNoteFreq(freq));

    }

    @Override
    public void reset() {

    }
}
