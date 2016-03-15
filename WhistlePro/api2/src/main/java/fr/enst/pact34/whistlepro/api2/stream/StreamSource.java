package main.java.fr.enst.pact34.whistlepro.api2.stream;

/**
 * Created by mms on 15/03/16.
 */
public abstract class StreamSource<F extends StreamDataInterface<F>> extends StreamSourceBase<F> {

    public void pushData()
    {
        for (DataListenerInterface<F> listener: this.getListeners())
        {
            F buffer = listener.getBufferToFill();
            getBufferOut().copyTo(buffer);
            listener.bufferFilled();
        }
    }

    protected abstract F getBufferOut();
}
