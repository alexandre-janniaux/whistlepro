package fr.enst.pact34.whistlepro.api2.stream;

/**
 * Created by mms on 16/03/16.
 */
class StreamDestBase<E extends StreamDataInterface<E>> implements DataListenerInterface<E> {

    E bufferIn = null;

    public StreamDestBase(E bufferIn) {
        this.bufferIn = bufferIn;
    }

    @Override
    public void fillBufferIn(E data) {
         synchronized (data)
        {
            synchronized (bufferIn) {
                data.copyTo(bufferIn);
            }
        }
    }
}
