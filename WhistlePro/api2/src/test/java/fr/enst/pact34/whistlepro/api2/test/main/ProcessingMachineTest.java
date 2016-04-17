package fr.enst.pact34.whistlepro.api2.test.main;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.common.FileOperator;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.main.ProccessingMachine;
import fr.enst.pact34.whistlepro.api2.stream.StreamSourceInput;
import fr.enst.pact34.whistlepro.api2.test.utils.TestUtils;

/**
 * Created by mms on 17/04/16.
 */
public class ProcessingMachineTest extends StreamSourceInput<double[]> {

    private final String inputDataFile = "../testData/features/signal.data";

    private double[] bufferToSend;

    public ProcessingMachineTest() {
        super(new double[TestUtils.createSignalFromFile("../testData/features/signal.data").length()]);
        bufferToSend=getBufferOut();
    }

    @Test
    public void proccessingMachineTest()
    {
        //*
        ProccessingMachine pm = new ProccessingMachine(this,44100, FileOperator.getDataFromFile("../testData/classification/voyelles.scs"));

        Signal inputData = TestUtils.createSignalFromFile(inputDataFile);

        inputData.fillArray(bufferToSend);

        pushData();

        while(pm.transcriptionEnded()==false)
        {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//*/
    }
}
