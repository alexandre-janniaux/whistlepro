package  fr.enst.pact34.whistlepro.api2.stream;

import java.util.HashSet;

/**
 * Created by mms on 15/03/16.
 */
public class StreamSourceBase<E extends StreamDataInterface<E>>
    implements StreamDataSourceInterface<E>
{

    private E bufferOut = null;

    public StreamSourceBase(E bufferOut) {
        this.bufferOut = bufferOut;
    }

    protected final E getBufferOut()
    {
        return bufferOut;
    }

    public final void pushData()
    {
        for (StreamDataListenerInterface<E> listener: listeners)
        {
            //synchronized (bufferOut) {
                listener.fillBufferIn(bufferOut);
            //}
        }
    }

    private HashSet<StreamDataListenerInterface<E>> listeners = new HashSet<>();

    //////////////////////////////
    /// @brief add a StreamDataListenerInterface as output
    /// @param listener the chained ouput
    //////////////////////////////
    public final void subscribe(StreamDataListenerInterface<E> listener) {
        if (this.listeners.contains(listener)) return;
        this.listeners.add(listener);
    }

    //////////////////////////////
    /// @brief remove a StreamDataListenerInterface as output
    /// @param listener the listener to remove as chained output
    //////////////////////////////
    public final void unsubscribe(StreamDataListenerInterface<E> listener) {
        if (!this.listeners.contains(listener)) return;
        this.listeners.remove(listener);
    }

}
