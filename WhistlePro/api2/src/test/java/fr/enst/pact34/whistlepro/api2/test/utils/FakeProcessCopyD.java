package fr.enst.pact34.whistlepro.api2.test.utils;

import fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

import java.util.Hashtable;

/**
 * Created by mms on 15/03/16.
 */
public class FakeProcessCopyD<E extends StreamDataInterface> implements StreamProcessInterface<E,E> {

    private int nb_passage = 0;
    @Override
    public void process(E inputData, E outputData) {

        inputData.copyTo(outputData);

        nb_passage++;
        String  id = String.valueOf(Thread.currentThread().getId());

        System.out.println(id+" : "+nb_passage);


    }

    @Override
    public void reset() {
        int nb_passage = 0;
    }
}
