package fr.enst.pact34.whistlepro.api2.test.attaque;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.attaque.AttackDetectorProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 27/04/16.
 */
public class AttackDetectorTest {

    @Test
    public void testAttack()
    {
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

        for (double d : res.getUpTimes()
                ) {
            System.out.println(" up :" + d);
        }
        for (double d : res.getDownTimes()
                ) {
            System.out.println(" down :"+d);
        }

        assertEquals(1,res.getUpTimes().size());
        assertEquals(1,res.getDownTimes().size());

        assertEquals(start,res.getUpTimes().get(0),Double.MIN_VALUE);
        assertEquals(end,res.getDownTimes().get(0),Double.MIN_VALUE);

    }


    @Test
    public void testAttack2()
    {
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

        for (double d : res.getUpTimes()
                ) {
            System.out.println(" up :" + d);
        }
        for (double d : res.getDownTimes()
                ) {
            System.out.println(" down :"+d);
        }

        assertEquals(1,res.getUpTimes().size());
        assertEquals(1,res.getDownTimes().size());

        assertEquals(start,res.getUpTimes().get(0),Double.MIN_VALUE);
        assertEquals(end,res.getDownTimes().get(0),Double.MIN_VALUE);

    }


    @Test
    public void testAttack3()
    {
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

        for (double d : res.getUpTimes()
                ) {
            System.out.println(" up :" + d);
        }
        for (double d : res.getDownTimes()
                ) {
            System.out.println(" down :"+d);
        }

        //sig supposed to be 0 before that's why there is a start
        assertEquals(1,res.getUpTimes().size());
        assertEquals(0,res.getDownTimes().size());


        attackDetector.process(inputData, res);

        for (double d : res.getUpTimes()
                ) {
            System.out.println(" up :" + d);
        }
        for (double d : res.getDownTimes()
                ) {
            System.out.println(" down :"+d);
        }

        //now it already started and before it was 1
        assertEquals(0,res.getUpTimes().size());
        assertEquals(0,res.getDownTimes().size());
    }


    @Test
    public void testAttack4()
    {
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

        for (double d : res.getUpTimes()
                ) {
            System.out.println(" up :" + d);
        }
        for (double d : res.getDownTimes()
                ) {
            System.out.println(" down :"+d);
        }

        assertEquals(1, res.getUpTimes().size());
        assertEquals(0, res.getDownTimes().size());

        for (int i = 0; i < inputData.length(); i++) {
            inputData.setValue(i,  0.1*Math.sin(2.0 * Math.PI * 2000 * i * t_step));
        }

        attackDetector.process(inputData, res);

        for (double d : res.getUpTimes()
                ) {
            System.out.println(" up :" + d);
        }
        for (double d : res.getDownTimes()
                ) {
            System.out.println(" down :"+d);
        }

        assertEquals(0,res.getUpTimes().size());
        assertEquals(1,res.getDownTimes().size());

    }

    //TODO make it with real signal
}
