package fr.enst.pact34.whistlepro.api2.Synthese;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


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
            int framesRead = src.readFrames(srcInDouble, numFramesToRead);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (WavFileException e) {
            e.printStackTrace();
        }
        return srcInDouble;
    }

    public double[] transposition(Onomatopee onomatopee) {
        ReadExample readExample = new ReadExample();
        switch (onomatopee.getInstrument()) {
            case 1 :
                return readExample.audioRead(new File("data/caisseClaire"));
            case 2 :
                return readExample.audioRead(new File("data/charleston"));
            case 3 :
                return readExample.audioRead(new File("data/cymbale"));
            case 4 :
                return readExample.audioRead(new File("data/grosse caisse"));
            default: //TODO
                return null; //return TableOfZeros ; // return a table of zeros of the size of the other sounds
        }
    }

    public double[] synthèsePercu(ArrayList<Onomatopee> in) {
        //TODO
        /*
        double[] out = new double[in.size()*sizeOfThePercuSounds]; //change
        for (int i = 0; i < in.size(); i++) {
            double intensite = in.get(i).getIntensity();
            double[] termeI = transposition(in.get(i));
            for (int j = 0; j < termeI.length; j++) {
                out[i + j] = intensite*termeI[j];
            }
        }
        return(out);
        */
        return null;
    }

}
