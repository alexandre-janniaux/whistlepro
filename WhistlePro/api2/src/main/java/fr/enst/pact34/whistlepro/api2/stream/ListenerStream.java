package main.java.fr.enst.pact34.whistlepro.api2.stream;

/**
 * Created by mms on 15/03/16.
 */
public abstract class ListenerStream<E> implements DataListenerInterface<E>
{
    E buffer = null;

    public ListenerStream(E buffer) {
        this.buffer = buffer;
    }

    @Override
    public E getBufferToFill()
    {
        return buffer;
    }
}
