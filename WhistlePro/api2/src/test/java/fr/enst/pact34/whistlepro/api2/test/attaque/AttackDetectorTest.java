package fr.enst.pact34.whistlepro.api2.test.attaque;

import org.junit.Test;

import java.util.Collections;
import java.util.Comparator;

import fr.enst.pact34.whistlepro.api2.attaque.AttackDetectorProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mms on 27/04/16.
 */
public class AttackDetectorTest {

    @Test
    public void testAttack()
    {
        System.out.println("test 1");
        Signal inputData = new Signal();
        double Fs = 44100;
        inputData.setSamplingFrequency(Fs);
        inputData.setLength((int) (0.020 * Fs));


        double t_step = 1 / Fs;

        double start = 0.004;
        double end = 0.017;

        for (int i = (int) (start * Fs); i < (int) (end * Fs); i++) {
            inputData.setValue(i, Math.sin(2.0 * Math.PI * 2000 * i * t_step)*Math.sin(2.0 * Math.PI * 150 * i * t_step));
        }

        AttackTimes res = new AttackTimes();

        AttackDetectorProcess attackDetector = new AttackDetectorProcess(inputData.length());


        attackDetector.process(inputData, res);

        for (AttackTimes.Attack d : res.getAttackTimes()
                ) {
            System.out.println(d.getType().name() + " :" + d.getTime());
        }

        assertEquals(2, res.getAttackTimes().size());

        Collections.sort(res.getAttackTimes(), new Comparator<AttackTimes.Attack>() {
            @Override
            public int compare(AttackTimes.Attack t0, AttackTimes.Attack t1) {
                return (int) (t0.getTime() - t1.getTime());
            }
        });

        assertTrue(res.getAttackTimes().get(0).getType()== AttackTimes.Attack.Type.Up);
        assertEquals(start,res.getAttackTimes().get(0).getTime(),Double.MIN_VALUE);
        assertTrue(res.getAttackTimes().get(1).getType() == AttackTimes.Attack.Type.Down);
        assertEquals(end,res.getAttackTimes().get(1).getTime(),Double.MIN_VALUE);


    }


    @Test
    public void testAttack2()
    {
        System.out.println("test 2");
        Signal inputData = new Signal();
        double Fs = 16000;
        inputData.setSamplingFrequency(Fs);
        inputData.setLength((int) (0.020 * Fs));


        double t_step = 1 / Fs;

        double start = 0.01;
        double end = 0.019;

        for (int i = (int) (start * Fs); i < (int) (end * Fs); i++) {
            inputData.setValue(i, Math.sin(2.0 * Math.PI * 2000 * i * t_step) );
        }

        AttackTimes res = new AttackTimes();

        AttackDetectorProcess attackDetector = new AttackDetectorProcess(inputData.length());


        attackDetector.process(inputData, res);

        for (AttackTimes.Attack d : res.getAttackTimes()
                ) {
            System.out.println(d.getType().name()+" :" + d.getTime());
        }

        assertEquals(2, res.getAttackTimes().size());

        Collections.sort(res.getAttackTimes(), new Comparator<AttackTimes.Attack>() {
            @Override
            public int compare(AttackTimes.Attack t0, AttackTimes.Attack t1) {
                return (int) (t0.getTime() - t1.getTime());
            }
        });

        assertTrue(res.getAttackTimes().get(0).getType() == AttackTimes.Attack.Type.Up);
        assertEquals(start,res.getAttackTimes().get(0).getTime(),Double.MIN_VALUE);
        assertTrue(res.getAttackTimes().get(1).getType() == AttackTimes.Attack.Type.Down);
        assertEquals(end,res.getAttackTimes().get(1).getTime(),Double.MIN_VALUE);

    }


    @Test
    public void testAttack3()
    {
        System.out.println("test 3");
        Signal inputData = new Signal();
        double Fs = 16000;
        inputData.setSamplingFrequency(Fs);
        inputData.setLength((int) (0.020 * Fs));

        for (int i = 0; i < inputData.length(); i++) {
            inputData.setValue(i, 1+ Math.random()*0.2-0.2);
        }

        AttackTimes res = new AttackTimes();

        AttackDetectorProcess attackDetector = new AttackDetectorProcess(inputData.length());


        attackDetector.process(inputData, res);

        for (AttackTimes.Attack d : res.getAttackTimes()
                ) {
            System.out.println(d.getType().name()+" :" + d.getTime());
        }


        assertEquals(1, res.getAttackTimes().size());
        assertTrue(res.getAttackTimes().get(0).getType() == AttackTimes.Attack.Type.Up);


        attackDetector.process(inputData, res);

        for (AttackTimes.Attack d : res.getAttackTimes()
                ) {
            System.out.println(d.getType().name()+" :" + d.getTime());
        }

        assertEquals(0, res.getAttackTimes().size());

    }


    @Test
    public void testAttack4()
    {
        System.out.println("test 4");
        Signal inputData = new Signal();
        double Fs = 16000;
        inputData.setSamplingFrequency(Fs);
        inputData.setLength((int) (0.020 * Fs));

        double t_step = 1 / Fs;
        for (int i = 0; i < inputData.length(); i++) {
            inputData.setValue(i,  0.5*Math.sin(2.0 * Math.PI * 2000 * i * t_step));
        }

        AttackTimes res = new AttackTimes();

        AttackDetectorProcess attackDetector = new AttackDetectorProcess(inputData.length());


        attackDetector.process(inputData, res);

        for (AttackTimes.Attack d : res.getAttackTimes()
                ) {
            System.out.println(d.getType().name()+" :" + d.getTime());
        }


        assertEquals(1, res.getAttackTimes().size());
        assertTrue(res.getAttackTimes().get(0).getType() == AttackTimes.Attack.Type.Up);


        for (int i = 0; i < inputData.length(); i++) {
            inputData.setValue(i,  0.1*Math.sin(2.0 * Math.PI * 2000 * i * t_step));
        }

        attackDetector.process(inputData, res);


        for (AttackTimes.Attack d : res.getAttackTimes()
                ) {
            System.out.println(d.getType().name()+" :" + d.getTime());
        }


        assertEquals(1, res.getAttackTimes().size());
        assertTrue(res.getAttackTimes().get(0).getType() == AttackTimes.Attack.Type.Down);



    }

    //TODO make it with real signal
}
