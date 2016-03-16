package  fr.enst.pact34.whistlepro.api2.stream;

/**
 * Created by mms on 15/03/16.
 */
public abstract class StreamSourceBase<F extends StreamDataInterface<F>> extends StreamSourceS<F> {

    protected void pushData()
    {
        for (DataListenerInterface<F> listener: this.getListeners())
        {
            F buffer = getBufferOut();
            synchronized (buffer) {
                listener.fillBufferIn(buffer);
            }
        }
    }

    protected abstract F getBufferOut();
}
