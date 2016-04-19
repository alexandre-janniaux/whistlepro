package fr.enst.pact34.whistlepro.api.Synthese;

import fr.enst.pact34.whistlepro.api.acquisition.ReadExample;
import com.sun.javafx.collections.ArrayListenerHelper;

import java.io.IOException;
import java.util.ArrayList;
import fr.enst.pact34.whistlepro.api.Synthese.WavFile ;

import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;

/**
 IMPORTANT : READ -> TO DO
 -find how to access to the instrument (change getInstrument, and
 -put the entry sound in variable "in"
 -change in synthesePercu the sizeOfThePercuSounds

 */
public class SynthèsePercussions
{
    public double[] wavFileToDoubleArray(WavFile src) {
        int numFramesToRead = (int) (src.getNumFrames() * src.getNumChannels());
        double[] srcInDouble = new double[numFramesToRead];
        try {
            int framesRead = src.readFrames(srcInDouble; numFramesToRead);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (WavFileException e) {
            e.printStackTrace();
        }
    }

    public double[] transposition(Onomatopee onomatopee) {
        ReadExample readExample = new ReadExample();
        switch (onomatopee.getInstrument()) {
            case 1 :
                return readExample.audioRead(data/caisseClaire);
            case 2 :
                return readExample.audioRead("data/charleston");
            case 3 :
                return readExample.audioRead("data/cymbale");
            case 4 :
                return readExample.audioRead("data/grosse caisse");
            default:
                return TableOfZeros ; // return a table of zeros of the size of the other sounds
        }
    }

    public double[] synthèsePercu(ArrayList<Onomatopee> in) {
        double[] out = new double[in.size()*sizeOfThePercuSounds]; //change
        for (int i = 0; i < in.size(); i++) {
            double intensite = in.get(i).getIntensity();
            double[] termeI = transposition(in.get(i));
            for (int j = 0; j < termeI.length; j++) {
                out[i + j] = intensite*termeI[j];
            }
        }
        return(out);
    }

}
