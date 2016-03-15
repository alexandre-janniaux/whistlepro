package fr.enst.pact34.whistlepro.api.test.stream;

import junit.framework.TestCase;
import junit.framework.TestResult;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DoubleSignalInterface;
import fr.enst.pact34.whistlepro.api.stream.AcquisitionStream;

import static org.junit.Assert.assertTrue;


public class AcquisitionStreamTest {

    @Test
    public void isRecording() {
        final AcquisitionStream stream = new AcquisitionStream();
        stream.subscribe(new DataListenerInterface<DoubleSignalInterface>() {
            @Override
            public void onPushData(DataSource<DoubleSignalInterface> source, DoubleSignalInterface inputData) {
                System.out.println("Reception de données");
                double[] signal = inputData.getSignal();
                boolean isNotNull = false;
                for (int i = 0; i < signal.length; ++i) {
                    //System.out.print(signal[i]);
                    isNotNull |= signal[i] != 0.;
                }
                stream.stopRecording();
                assertTrue("Le signal est non null", isNotNull);
            }
        });
        stream.startRecording();
        try {
            stream.waitThread();
            System.out.println("Fin du thread");
        } catch (InterruptedException e) {
            assertTrue("Aucune interruption de thread n'a lieu", false);
        }

    }
}