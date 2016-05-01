package fr.enst.pact34.whistlepro.api2.test.main;

import org.junit.Test;

import java.util.List;

import fr.enst.pact34.whistlepro.api2.Synthese.Instru;
import fr.enst.pact34.whistlepro.api2.Synthese.Percu;
import fr.enst.pact34.whistlepro.api2.main.Morceau;
import fr.enst.pact34.whistlepro.api2.main.Piste;
import fr.enst.pact34.whistlepro.api2.main.PisteMelodie;
import fr.enst.pact34.whistlepro.api2.main.PistePercu;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 01/05/16.
 */
public class MorceauBuilderTest {

    @Test
    public void testMorceauBuilder()
    {
        Morceau morceau = new Morceau();
        morceau.setTitle("title test");

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

        morceau.addPiste(pistePercu);


        PisteMelodie pisteMelodie = new PisteMelodie();
        pisteMelodie.setTotalTime(1.5);
        Instru instru = new Instru();
        instru.setFreq(50);
        instru.setStartTime(0);
        instru.setEndTime(0.5);
        pisteMelodie.addInstru(instru);
        Instru instru1 = new Instru();
        instru1.setFreq(100);
        instru1.setStartTime(0.5);
        instru1.setEndTime(1.1);
        pisteMelodie.addInstru(instru1);
        Instru instru2 = new Instru();
        instru2.setFreq(150);
        instru2.setStartTime(1.1);
        instru2.setEndTime(1.5);
        pisteMelodie.addInstru(instru2);

        morceau.addPiste(pisteMelodie);

        String saveData = morceau.getSaveString();

        //System.out.print(saveData);

        Morceau.Builder builder = new Morceau.Builder();
        builder.fromString(saveData);
        Morceau morceau1 = builder.build();

        assertEquals(morceau.getTitle(),morceau1.getTitle());
        assertEquals(morceau.nbPiste(),morceau1.nbPiste());

        Piste piste = morceau1.getPisteByIndex(1);

        assertEquals(Piste.TypePiste.Melodie,piste.getTypePiste());
        assertEquals(pisteMelodie.getTotalTime(),piste.getTotalTime(),1e-6);

        PisteMelodie pisteP = (PisteMelodie) piste;

        List<Instru> list = pisteP.getInstruList();
        assertEquals(3,list.size());

        assertEquals(instru.getFreq(),list.get(0).getFreq(),1e-6);
        assertEquals(instru.getStartTime(),list.get(0).getStartTime(),1e-6);
        assertEquals(instru.getEndTime(),list.get(0).getEndTime(),1e-6);

        assertEquals(instru1.getFreq(),list.get(1).getFreq(),1e-6);
        assertEquals(instru1.getStartTime(),list.get(1).getStartTime(),1e-6);
        assertEquals(instru1.getEndTime(),list.get(1).getEndTime(),1e-6);

        assertEquals(instru2.getFreq(),list.get(2).getFreq(),1e-6);
        assertEquals(instru2.getStartTime(),list.get(2).getStartTime(),1e-6);
        assertEquals(instru2.getEndTime(),list.get(2).getEndTime(),1e-6);

        piste = morceau1.getPisteByIndex(0);

        assertEquals(Piste.TypePiste.Percussions,piste.getTypePiste());
        assertEquals(pistePercu.getTotalTime(),piste.getTotalTime(),1e-6);

        PistePercu pisteP2 = (PistePercu) piste;

        List<Percu> list2 = pisteP2.getPercuList();
        assertEquals(3, list2.size());

        assertEquals(p.getType(), list2.get(0).getType());
        assertEquals(p.getStartTime(),list2.get(0).getStartTime(),1e-6);
        assertEquals(p.getEndTime(),list2.get(0).getEndTime(),1e-6);

        assertEquals(p2.getType(), list2.get(1).getType());
        assertEquals(p2.getStartTime(),list2.get(1).getStartTime(),1e-6);
        assertEquals(p2.getEndTime(),list2.get(1).getEndTime(),1e-6);

        assertEquals(p3.getType(), list2.get(2).getType());
        assertEquals(p3.getStartTime(),list2.get(2).getStartTime(),1e-6);
        assertEquals(p3.getEndTime(),list2.get(2).getEndTime(),1e-6);


    }
}
