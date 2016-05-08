package fr.enst.pact34.whistlepro.api2.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

/**
 * Created by mms on 23/04/16.
 */
public class FreqProcess implements StreamProcessInterface<Signal,Frequency> {

    private int xFs;
    short[] sig;
    double[] res;
    private int Fs;

    public FreqProcess(int Fs, int nbSample)
    {
        xFs = (int) Math.ceil(50000.0/Fs);

        sig = new short[nbSample*xFs];
        res = new double[sig.length];
        this.Fs=Fs;
    }

    @Override
    public void process(Signal inputData, Frequency outputData) {

        double freq = 0;

        double tps= 1.0/Fs;

        double last_max = 0;
        for (double f : NoteCorrector.getNoteFreqs() ) {
            //System.out.print(" => "+f+" ");
            double mc=0,ms=0;
            double w= 2*Math.PI*f;

            for (int j = 1; j < inputData.length(); j++) {
                mc+=Math.cos(w * j * tps)*inputData.getValue(j);
                ms+=Math.sin(w * j * tps)*inputData.getValue(j);
            }
            double v = mc*mc+ms*ms;
            //System.out.println(" => " +v +" ;");
            if(last_max<v) {
                last_max = v;
                freq = f;
            }
        }

        outputData.setFrequency(freq);
    }

    @Override
    public void reset() {

    }
}
