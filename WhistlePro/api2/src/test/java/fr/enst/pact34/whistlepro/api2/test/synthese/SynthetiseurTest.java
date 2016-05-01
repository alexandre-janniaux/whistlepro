package fr.enst.pact34.whistlepro.api2.test.synthese;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.Synthese.Instru;
import fr.enst.pact34.whistlepro.api2.Synthese.InstruGenerator;
import fr.enst.pact34.whistlepro.api2.Synthese.Percu;
import fr.enst.pact34.whistlepro.api2.Synthese.PercuGenerator;
import fr.enst.pact34.whistlepro.api2.Synthese.Synthetiseur;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.main.PisteMelodie;
import fr.enst.pact34.whistlepro.api2.main.PistePercu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mms on 29/04/16.
 */
public class SynthetiseurTest {

    @Test
    public void testSynthtiseurPercu()
    {
        Signal inputData = new Signal();
        double Fs = 16000;
        inputData.setSamplingFrequency(Fs);
        inputData.setLength((int) (0.020 * Fs));

        double t_step = 1 / Fs;
        for (int i = 0; i < inputData.length(); i++) {
            inputData.setValue(i,  Math.sin(2.0 * Math.PI * 2000 * i * t_step));
        }

        PercuGenerator generator = new PercuGenerator(Fs);
        generator.addPercu(Percu.Type.Kick, inputData);

        Synthetiseur synthe = new Synthetiseur(generator,null);

        PistePercu pistePercu = new PistePercu();
        pistePercu.setTotalTime(0.1);

        Percu p = new Percu();
        p.setType(Percu.Type.Kick);
        p.setStartTime(0.010);
        p.setEndTime(0.080);

        pistePercu.addPercu(p);

        Signal sound = synthe.synthetise(pistePercu);

        assertEquals(0.1, sound.length() / sound.getSamplingFrequency(), Double.MIN_VALUE);

        double sum = 0;
        for (int i = 0; i < Fs * 0.01 && i < sound.length(); i++) {
            sum+= Math.abs(sound.getValue(i));
        }

        assertEquals(0, sum, Double.MIN_VALUE);

        sum = 0;
        for (int i = (int) (Fs * 0.01); i < Fs * 0.08 && i < sound.length(); i++) {
            sum+= Math.abs(sound.getValue(i));
        }

        assertTrue(sum > 1);

        sum = 0;
        for (int i = (int) (Fs * 0.08); i < sound.length(); i++) {
            sum+= Math.abs(sound.getValue(i));
        }

        assertEquals(0, sum, Double.MIN_VALUE);
    }


    @Test
    public void testSynthtiseurMelodie()
    {
        double Fs = 16000;
        InstruGenerator generator = new InstruGenerator(Fs);
        generator.addInstru(Instru.Type.Piano, 0.9, 0.9);

        Synthetiseur synthe = new Synthetiseur(null,generator);

        PisteMelodie pisteMelodie = new PisteMelodie();
        pisteMelodie.setTotalTime(0.1);

        Instru p = new Instru();
        p.setType(Instru.Type.Piano);
        p.setStartTime(0.010);
        p.setEndTime(0.080);
        p.setFreq(150);

        pisteMelodie.addInstru(p);

        Signal sound = synthe.synthetise(pisteMelodie);

        assertEquals(0.1, sound.length() / sound.getSamplingFrequency(), Double.MIN_VALUE);

        double sum = 0;
        for (int i = 0; i < Fs * 0.01 && i < sound.length(); i++) {
            sum+= Math.abs(sound.getValue(i));
        }

        assertEquals(0, sum, Double.MIN_VALUE);

        sum = 0;
        for (int i = (int) (Fs * 0.01); i < Fs * 0.08 && i < sound.length(); i++) {
            sum+= Math.abs(sound.getValue(i));
        }

        assertTrue(sum > 1);

        sum = 0;
        for (int i = (int) (Fs * 0.08); i < sound.length(); i++) {
            sum+= Math.abs(sound.getValue(i));
        }

        assertEquals(0, sum, Double.MIN_VALUE);
    }
}
