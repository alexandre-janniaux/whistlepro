package fr.enst.pact34.whistlepro.api2.test.main;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.common.FileOperator;
import fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.main.ProcessingMachine;
import fr.enst.pact34.whistlepro.api2.main.ProcessorEventListener;
import fr.enst.pact34.whistlepro.api2.main.TypePiste;
import fr.enst.pact34.whistlepro.api2.test.utils.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mms on 17/04/16.
 */
public class ProcessingMachineTest implements ProcessorEventListener {

    private final String inputDataFile = "../testData/features/signal.data";

    private double[] bufferToSend ;


    @Test
    public void proccessingMachineTest()
    {
        //*
        Signal inputData = TestUtils.createSignalFromFile(inputDataFile);
        bufferToSend = new double[inputData.length()];
        inputData.fillArray(bufferToSend);

        final ProcessingMachine pm = new ProcessingMachine(44100, FileOperator.getDataFromFile("../testData/classification/voyelles.scs"),2, TypePiste.Percussions);

        pm.pushData(bufferToSend);


/*
        for (int i = 0; i < 10 && pm.transcriptionEnded()==false ; i++) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        */

        pm.waitEnd();


        //assertTrue(pm.transcriptionEnded());
        //TODO test on ret values for example
//*/
    }

    ProcessingMachine pmRef ;
    @Test
    public void proccessingMachineTest2()
    {
        //*
        Signal inputData = TestUtils.createSignalFromFile(inputDataFile);
        //inputData.setLength((int)(44100*0.020)); // Will be be treated as one sample (will not be cut)
        bufferToSend = new double[inputData.length()];
        inputData.fillArray(bufferToSend);

        final ProcessingMachine pm = new ProcessingMachine(44100, FileOperator.getDataFromFile("../testData/classification/voyelles.scs"),2,TypePiste.Percussions);
        pmRef = pm;
        pm.setEventLister(this);

        pm.pushData(bufferToSend);
        System.out.println("1");
        pm.pushData(bufferToSend);
        System.out.println("2");
        pm.pushData(bufferToSend);
        System.out.println("3");


        pm.waitEnd();


        //assertTrue(pm.transcriptionEnded());
        //TODO test on ret values for example



//*/
    }

    @Test
    public void proccessingMachineTest3()
    {
        //*
        Signal inputData = TestUtils.createSignalFromFile(inputDataFile);
        inputData.setLength(inputData.length());
        for (int i = 0; i < inputData.length()/3; i++) {
            inputData.setValue(inputData.length()-i-1,0);
        }
        //inputData.setLength((int)(44100*0.020)); // Will be be treated as one sample (will not be cut)
        bufferToSend = new double[inputData.length()];

        //inputData.fillArray(bufferToSend);


        final ProcessingMachine pm = new ProcessingMachine(44100, FileOperator.getDataFromFile("../testData/classification/voyelles.scs"),4,TypePiste.Percussions);
        pmRef = pm;
        pm.setEventLister(this);

        //pm.pushData(bufferToSend);

        // test start
        for(int i = 0; i < inputData.length(); i++) {
            pm.pushData(new double[]{inputData.getValue(i)});
        }

        pm.waitEnd();
        //while (true);

        //assertTrue(pm.transcriptionEnded());
        //TODO test on ret values for example
//*/
    }

    int i  = 0;
    @Override
    public synchronized void newWorkEvent(WorkEvent e) {
        if(e==WorkEvent.AllWorkDone) {
            System.out.print(i++ + " Last Work Done : ");
            System.out.println(pmRef.getLastReco());
            AttackTimes attackTimes = pmRef.getLastAttack();
            if(attackTimes.isValid()==false) System.out.print("Not Valid ");
/* TODO
            for (double d :
                    attackTimes.getUpTimes()) {
                System.out.println(" up => " + d);
            }
            for (double d :
                    attackTimes.getDownTimes()) {
                System.out.println( " down => "+ d);
            }
            */
        }
            if(e==WorkEvent.OneWorkDone) {
            System.out.print(i++ + " One Work Done : ");
            System.out.println(pmRef.getLastReco());
            AttackTimes attackTimes = pmRef.getLastAttack();
            if(attackTimes.isValid()==false) System.out.print("Not Valid ");

            for (AttackTimes.Attack d : attackTimes.getAttackTimes()
                    ) {
                System.out.println(d.getType().name()+" up :" + d.getTime());
            }


        }
    }
}
