package test.java.fr.enst.pact34.whistlepro.api.test.features;

import fr.enst.pact34.whistlepro.api.common.FileOperator;
import fr.enst.pact34.whistlepro.api.features.FilterBankMel;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class FilterBankMelTest {

    @Test
    public void frequencyToMel() {
        double freq = 100., mel = 150.48987948783699;
        assertEquals(FilterBankMel.frequencyToMel(freq),mel,mel*0.001); // TODO: precalculate mel coeff and round it
    }

    @Test
    public void melToFrequency() {
        double mel = 100., freq = 64.950770658278702;
        assertEquals(FilterBankMel.melToFrequency(mel),freq,freq*0.001); // TODO: precalculate mel coeff and round it
    }

    @Test
    public void computeFilterFrequencies()
    {
        double freqMin = 0, freqMax = 3500;
        int nbMelFiler = 24;
        int nbPtsEchelle = 350+1;
        double[] echelleFreq = new double[nbPtsEchelle];
        for(int i = 0; i < echelleFreq.length; i ++)
        {
            echelleFreq[i] = ((double)i/(echelleFreq.length-1))*(freqMax-freqMin);
        }
        assertEquals(freqMin,echelleFreq[0],Double.MIN_VALUE);
        assertEquals(freqMax,echelleFreq[echelleFreq.length-1],Double.MIN_VALUE);

        double[][] filters = FilterBankMel.computeFilterBank(freqMin,freqMax,nbMelFiler,echelleFreq);

        ArrayList<String> lines = FileOperator.getLinesFromFile("../testData/features/genFilters.valid");

        assertEquals(lines.size(),nbMelFiler);

        for(int i = 0; i < filters.length; i++)
        {
            String[] line = lines.get(i).split(";");

            assertEquals(nbPtsEchelle,line.length);

            for(int j = 0; j < filters[i].length; j++)
            {
                double tmp = Double.parseDouble(line[j]);
                if(tmp != 0)
                    assertEquals(tmp,filters[i][j], Math.abs(tmp*0.001));
                else
                    assertEquals(tmp,filters[i][j],1e-14);
            }
        }



    }


}
