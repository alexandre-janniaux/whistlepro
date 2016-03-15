package main.java.fr.enst.pact34.whistlepro.api2.stream;

//TODO: documentation
public abstract class StreamBase<E extends StreamDataInterface<E>,F extends StreamDataInterface<F>>
        extends StreamStreamDataSource<F> implements  DataListenerInterface<E>{

    @Override
    public void bufferFilled() {
        //signal that indicate to start process

        for (DataListenerInterface<F> listener: this.getListeners())
        {
            F buffer = listener.getBufferToFill();
            getBufferOut().copyTo(buffer);
            listener.bufferFilled();
        }
    }

    protected abstract F getBufferOut();

}
