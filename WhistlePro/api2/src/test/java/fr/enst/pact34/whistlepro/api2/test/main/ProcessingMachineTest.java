package fr.enst.pact34.whistlepro.api2.test.main;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.common.FileOperator;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.main.ProcessingMachine;
import fr.enst.pact34.whistlepro.api2.main.ProcessingMachineEventListener;
import fr.enst.pact34.whistlepro.api2.test.utils.TestUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mms on 17/04/16.
 */
public class ProcessingMachineTest implements ProcessingMachineEventListener {

    private final String inputDataFile = "../testData/features/signal.data";

    private double[] bufferToSend ;


    @Test
    public void proccessingMachineTest()
    {
        //*
        Signal inputData = TestUtils.createSignalFromFile(inputDataFile);
        bufferToSend = new double[inputData.length()];
        inputData.fillArray(bufferToSend);

        final ProcessingMachine pm = new ProcessingMachine(44100, FileOperator.getDataFromFile("../testData/classification/voyelles.scs"),2);

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


        assertTrue(pm.transcriptionEnded());
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

        final ProcessingMachine pm = new ProcessingMachine(44100, FileOperator.getDataFromFile("../testData/classification/voyelles.scs"),1);
        pmRef = pm;
        pm.setEventLister(this);

        pm.pushData(bufferToSend);
        System.out.println("1");
        pm.pushData(bufferToSend);
        System.out.println("2");
        pm.pushData(bufferToSend);
        System.out.println("3");


        pm.waitEnd();


        assertTrue(pm.transcriptionEnded());



//*/
    }

    @Test
    public void proccessingMachineTest3()
    {
        //*
        Signal inputData = TestUtils.createSignalFromFile(inputDataFile);
        //inputData.setLength((int)(44100*0.020)); // Will be be treated as one sample (will not be cut)
        bufferToSend = new double[inputData.length()];

        //inputData.fillArray(bufferToSend);


        final ProcessingMachine pm = new ProcessingMachine(44100, FileOperator.getDataFromFile("../testData/classification/voyelles.scs"),2);
        pmRef = pm;
        pm.setEventLister(this);

        //pm.pushData(bufferToSend);

        // test start
        for(int i = 0; i < inputData.length(); i++) {
            pm.pushData(new double[]{inputData.getValue(i)});
        }

        pm.waitEnd();


        assertTrue(pm.transcriptionEnded());
//*/
    }

    int i  = 0;
    @Override
    public synchronized void newWorkEvent(WorkEvent e) {
        if(e==WorkEvent.OneWorkDone) {
            System.out.print(i++ +" One Work Done : ");
            System.out.println(pmRef.getLastReco());
        }
    }
}
