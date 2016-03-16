package  fr.enst.pact34.whistlepro.api2.phantoms;


import fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamDest;

/**
 * Created by mms on 15/03/16.
 */
public class FakeStreamDest<E extends StreamDataInterface<E>> extends StreamDest<E> {

    public FakeStreamDest(E bufferIn) {
        super(bufferIn);
    }
}
