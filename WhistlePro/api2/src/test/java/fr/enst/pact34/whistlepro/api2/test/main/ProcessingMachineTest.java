package fr.enst.pact34.whistlepro.api2.test.main;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.common.FileOperator;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.main.ProcessingMachine;
import fr.enst.pact34.whistlepro.api2.test.utils.TestUtils;

import static org.junit.Assert.assertTrue;

/**
 * Created by mms on 17/04/16.
 */
public class ProcessingMachineTest {

    private final String inputDataFile = "../testData/features/signal.data";

    private double[] bufferToSend ;

    public ProcessingMachineTest() {

    }

    @Test
    public void proccessingMachineTest()
    {
        //*
        Signal inputData = TestUtils.createSignalFromFile(inputDataFile);
        bufferToSend = new double[inputData.length()];
        inputData.fillArray(bufferToSend);

        ProcessingMachine pm = new ProcessingMachine(bufferToSend.length,44100, FileOperator.getDataFromFile("../testData/classification/voyelles.scs"),2);


        pm.pushData(bufferToSend);



        for (int i = 0; i < 10 && pm.transcriptionEnded()==false ; i++) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        assertTrue(pm.transcriptionEnded());
//*/
    }
}
