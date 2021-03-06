package  fr.enst.pact34.whistlepro.api2.phantoms;

import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;
import  fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 15/03/16.
 */
public class FakeProcessOutValue<E extends StreamDataInterface,F extends StreamDataInterface> implements StreamProcessInterface<E,F>
{
    F v =null;
    public FakeProcessOutValue(F value)
    {
        v =value;
    }

    @Override
    public void process(E inputData, F outputData) {
        v.copyTo(outputData);
    }

    @Override
    public void reset() {

    }
}
