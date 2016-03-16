package fr.enst.pact34.whistlepro.api2.stream;

/**
 * Created by mms on 16/03/16.
 */
public class StreamDest<E extends StreamDataInterface<E>> extends StreamDestBase<E> {

    public StreamDest(E bufferIn) {
        super(bufferIn);
    }


}
