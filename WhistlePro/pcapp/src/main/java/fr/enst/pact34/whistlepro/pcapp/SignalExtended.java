package fr.enst.pact34.whistlepro.pcapp;

import java.io.File;
import java.io.IOException;

import fr.enst.pact34.whistlepro.api.acquisition.WavFile;
import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;
import fr.enst.pact34.whistlepro.api.common.Spectrum;
import fr.enst.pact34.whistlepro.api.common.transformers;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;

/**
 * Created by mms on 26/05/16.
 */
public class SignalExtended extends Signal implements CurveAdapterInterface {
    @Override
    public boolean isFinite() {
        return false;
    }

    @Override
    public double getAbscisse(int i) {
        return i;
    }

    @Override
    public int getNbPoints() {
        return this.length();
    }

    private int amplitude = 5;
    @Override
    public int getMaxAmplitude() {
        return amplitude;
    }

    public void loadFromWavFile(String fileName) throws IOException, WavFileException {

        WavFile readWavFile = WavFile.openWavFile(new File(fileName));
        double Fs = readWavFile.getSampleRate();
        this.setSamplingFrequency(Fs);
        double Ts = 1.0/readWavFile.getSampleRate();
        double Tps = 20e-3;
        int nbPts = (int)(Tps/Ts);

        //reading buffer
        double[] buffer = new double[nbPts];
        int N ;
        //on rempli les donnes
        this.setLength(0);
        while( (N= readWavFile.readFrames(buffer,nbPts)) > 0)
        {
            int act_len = this.length();
            this.setLength(this.length()+N);
            this.fromArray(buffer,act_len,N);
        }

    }
}
