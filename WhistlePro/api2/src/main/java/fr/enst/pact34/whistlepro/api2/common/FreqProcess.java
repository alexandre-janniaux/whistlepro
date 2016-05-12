package fr.enst.pact34.whistlepro.api2.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

/**
 * Created by mms on 23/04/16.
 */
public class FreqProcess implements StreamProcessInterface<Signal,Frequency> {

    private final int nbSample;
    private int Fs;
    private double[][] tblCos,tblSin;
    double[] freqs ;

    public FreqProcess(int Fs, int nbSample)
    {
        this.nbSample=nbSample;
        this.Fs=Fs;
        tblCos = new double[NoteCorrector.getNoteFreqs().length][nbSample];
        tblSin = new double[NoteCorrector.getNoteFreqs().length][nbSample];
        freqs = NoteCorrector.getNoteFreqs();
        double tps= 1.0/Fs;
        for (int i = 0; i < freqs.length ; i++) {

            double w= 2*Math.PI*freqs[i];

            for (int j = 0; j < nbSample; j++) {
                tblCos[i][j]=Math.cos(w * j * tps);
                tblSin[i][j]=Math.sin(w * j * tps);
            }
        }

    }

    @Override
    public void process(Signal inputData, Frequency outputData) {

        double freq = 0;

        double last_max = 0;
        for (int i = 0; i < freqs.length ; i++) {
            double mc=0,ms=0;
            double w= 2*Math.PI*freqs[i];

            for (int j = 1; j < nbSample; j++) {
                mc+=tblCos[i][j]*inputData.getValue(j);
                ms+=tblSin[i][j]*inputData.getValue(j);
            }
            double v = mc*mc+ms*ms;
            if(last_max<v) {
                last_max = v;
                freq = freqs[i];
            }
        }

        outputData.setFrequency(freq);
    }

    @Override
    public void reset() {

    }
}
