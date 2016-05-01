package fr.enst.pact34.whistlepro.api2.test.main;

import org.junit.Test;

import java.util.List;

import fr.enst.pact34.whistlepro.api2.Synthese.Instru;
import fr.enst.pact34.whistlepro.api2.Synthese.Percu;
import fr.enst.pact34.whistlepro.api2.main.Piste;
import fr.enst.pact34.whistlepro.api2.main.PisteMelodie;
import fr.enst.pact34.whistlepro.api2.main.PistePercu;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 01/05/16.
 */
public class PisteBuilderTest {

    @Test
    public void testPistePercuBuilder()
    {
        PistePercu pistePercu = new PistePercu();
        pistePercu.setTotalTime(1.5);
        Percu p = new Percu();
        p.setType(Percu.Type.CaisseClaire);
        p.setStartTime(0);
        p.setEndTime(0.5);
        pistePercu.addPercu(p);
        Percu p2 = new Percu();
        p2.setType(Percu.Type.Kick);
        p2.setStartTime(0.5);
        p2.setEndTime(1.1);
        pistePercu.addPercu(p2);
        Percu p3 = new Percu();
        p3.setType(Percu.Type.Charleston);
        p3.setStartTime(1.1);
        p3.setEndTime(1.5);
        pistePercu.addPercu(p3);

        String saveData = pistePercu.getSaveString();

        //System.out.print(saveData);

        Piste.Builder builder = new Piste.Builder();
        builder.fromString(saveData);
        Piste piste = builder.build();

        assertEquals(Piste.TypePiste.Percussions,piste.getTypePiste());
        assertEquals(pistePercu.getTotalTime(),piste.getTotalTime(),1e-6);

        PistePercu pisteP = (PistePercu) piste;

        List<Percu> list = pisteP.getPercuList();
        assertEquals(3,list.size());

        assertEquals(p.getType(),list.get(0).getType());
        assertEquals(p.getStartTime(),list.get(0).getStartTime(),1e-6);
        assertEquals(p.getEndTime(),list.get(0).getEndTime(),1e-6);

        assertEquals(p2.getType(),list.get(1).getType());
        assertEquals(p2.getStartTime(),list.get(1).getStartTime(),1e-6);
        assertEquals(p2.getEndTime(),list.get(1).getEndTime(),1e-6);

        assertEquals(p3.getType(),list.get(2).getType());
        assertEquals(p3.getStartTime(),list.get(2).getStartTime(),1e-6);
        assertEquals(p3.getEndTime(),list.get(2).getEndTime(),1e-6);


    }


    @Test
    public void testPisteMedloBuilder()
    {
        PisteMelodie pisteMelodie = new PisteMelodie();
        pisteMelodie.setTotalTime(1.5);
        Instru p = new Instru();
        p.setFreq(50);
        p.setStartTime(0);
        p.setEndTime(0.5);
        pisteMelodie.addInstru(p);
        Instru p2 = new Instru();
        p2.setFreq(100);
        p2.setStartTime(0.5);
        p2.setEndTime(1.1);
        pisteMelodie.addInstru(p2);
        Instru p3 = new Instru();
        p3.setFreq(150);
        p3.setStartTime(1.1);
        p3.setEndTime(1.5);
        pisteMelodie.addInstru(p3);

        String saveData = pisteMelodie.getSaveString();

        //System.out.print(saveData);

        Piste.Builder builder = new Piste.Builder();
        builder.fromString(saveData);
        Piste piste = builder.build();

        assertEquals(Piste.TypePiste.Melodie,piste.getTypePiste());
        assertEquals(pisteMelodie.getTotalTime(),piste.getTotalTime(),1e-6);

        PisteMelodie pisteP = (PisteMelodie) piste;

        List<Instru> list = pisteP.getInstruList();
        assertEquals(3,list.size());

        assertEquals(p.getFreq(),list.get(0).getFreq(),1e-6);
        assertEquals(p.getStartTime(),list.get(0).getStartTime(),1e-6);
        assertEquals(p.getEndTime(),list.get(0).getEndTime(),1e-6);

        assertEquals(p2.getFreq(),list.get(1).getFreq(),1e-6);
        assertEquals(p2.getStartTime(),list.get(1).getStartTime(),1e-6);
        assertEquals(p2.getEndTime(),list.get(1).getEndTime(),1e-6);

        assertEquals(p3.getFreq(),list.get(2).getFreq(),1e-6);
        assertEquals(p3.getStartTime(),list.get(2).getStartTime(),1e-6);
        assertEquals(p3.getEndTime(),list.get(2).getEndTime(),1e-6);


    }
}
