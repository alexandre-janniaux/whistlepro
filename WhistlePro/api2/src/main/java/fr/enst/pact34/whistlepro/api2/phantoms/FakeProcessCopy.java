package  fr.enst.pact34.whistlepro.api2.phantoms;

import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;
import  fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 15/03/16.
 */
public class FakeProcessCopy<E extends StreamDataInterface> implements StreamProcessInterface<E,E> {
    int c = (int) (Math.random()*100);
    @Override
    public void process(E inputData, E outputData) {
        System.out.println("=>"+c+" copy");
        inputData.copyTo(outputData);
    }
}
