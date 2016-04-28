package fr.enst.pact34.whistlepro.api2.test.synthese;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import fr.enst.pact34.whistlepro.api2.Synthese.Percu;
import fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import fr.enst.pact34.whistlepro.api2.dataTypes.ClassifResults;
import fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import fr.enst.pact34.whistlepro.api2.main.Piste;
import fr.enst.pact34.whistlepro.api2.main.PisteBuilder;
import fr.enst.pact34.whistlepro.api2.main.PisteMelodie;
import fr.enst.pact34.whistlepro.api2.main.PistePercu;
import fr.enst.pact34.whistlepro.api2.main.TypePiste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mms on 28/04/16.
 */
public class PisteBuilderTest {

    @Test
    public void testPistePercuBuilder()
    {
        PisteBuilder builder = new PisteBuilder();

        //creation liste res classif
        ClassifResults res_a = new ClassifResults();
        res_a.setNbRes(1);
        res_a.fillClassesFromArray(new String[]{"a"});
        res_a.fillResFromArray(new double[]{2});

        ClassifResults res_empty = new ClassifResults();
        res_empty.setNbRes(1);
        res_empty.fillClassesFromArray(new String[]{"a"});
        res_empty.fillResFromArray(new double[]{-2});
        List<ClassifResults> resClassif = new LinkedList<>();

        int id = 0;
        for (; id < 4 ; id++) {
            ClassifResults res = new ClassifResults();
            res_empty.copyTo(res);
            res.setId(id);
            resClassif.add(res);
        }
        for (; id < 10 ; id++) {
            ClassifResults res = new ClassifResults();
            res_a.copyTo(res);
            res.setId(id);
            resClassif.add(res);
        }
        for (; id < 14 ; id++) {
            ClassifResults res = new ClassifResults();
            res_empty.copyTo(res);
            res.setId(id);
            resClassif.add(res);
        }

        //creation liste res attaque
        List<AttackTimes> resAttack = new LinkedList<>();
        for (int i = 0; i <= id ; i++) {
            resAttack.add(new AttackTimes());
        }
        resAttack.get(5).addUp(0.007);
        resAttack.get(6).addUp(0.007); //Sould be ignored
        resAttack.get(10).addDown(0.005); //Should be ignored
        resAttack.get(10).addDown(0.009);

        builder.addAttackTimes(resAttack);
        builder.addPercus(resClassif);

        builder.setPercuCorrespondance("a", Percu.Type.Kick);

        Piste piste= builder.buildPiste(TypePiste.Percussions);

        assertTrue(piste.getTypePiste() == TypePiste.Percussions);
        PistePercu pistepercu = (PistePercu) piste;
        assertEquals(1,pistepercu.getPercuList().size());

        assertEquals(0.057,pistepercu.getPercuList().get(0).getStartTime(),Double.MIN_VALUE);
        assertEquals(0.109,pistepercu.getPercuList().get(0).getEndTime(),Double.MIN_VALUE);

        assertTrue(pistepercu.getPercuList().get(0).getType()== Percu.Type.Kick);
    }


    @Test
    public void testPisteMelodieBuilder()
    {
        PisteBuilder builder = new PisteBuilder();

        //creation liste res classif
        Frequency frequency = new Frequency();
        frequency.setFrequency(0);

        Frequency frequency2 = new Frequency();
        frequency2.setFrequency(100);
        List<Frequency> resClassif = new LinkedList<>();

        int id = 0;
        for (; id < 4 ; id++) {
            Frequency res = new Frequency();
            frequency.copyTo(res);
            res.setId(id);
            resClassif.add(res);
        }
        for (; id < 10 ; id++) {
            Frequency res = new Frequency();
            frequency2.copyTo(res);
            resClassif.add(res);
        }
        for (; id < 14 ; id++) {
            Frequency res = new Frequency();
            frequency.copyTo(res);
            res.setId(id);
            resClassif.add(res);
        }

        //creation liste res attaque
        List<AttackTimes> resAttack = new LinkedList<>();
        for (int i = 0; i <= id ; i++) {
            resAttack.add(new AttackTimes());
        }
        resAttack.get(5).addUp(0.007);
        resAttack.get(6).addUp(0.007); //Sould be ignored
        resAttack.get(10).addDown(0.005); //Should be ignored
        resAttack.get(10).addDown(0.009);

        builder.addAttackTimes(resAttack);
        builder.addFrequencies(resClassif);

        Piste piste= builder.buildPiste(TypePiste.Melodie);

        assertTrue(piste.getTypePiste() == TypePiste.Melodie);
        PisteMelodie pistepercu = (PisteMelodie) piste;
        assertEquals(1, pistepercu.getInstruList().size());

        assertEquals(0.057,pistepercu.getInstruList().get(0).getStartTime(),Double.MIN_VALUE);
        assertEquals(0.109,pistepercu.getInstruList().get(0).getEndTime(),Double.MIN_VALUE);

        assertEquals(100,pistepercu.getInstruList().get(0).getFreq(),Double.MIN_VALUE);
    }
}
