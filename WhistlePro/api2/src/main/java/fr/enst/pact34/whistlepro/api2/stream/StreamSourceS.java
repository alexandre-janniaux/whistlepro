package  fr.enst.pact34.whistlepro.api2.stream;

import java.util.HashSet;

//TODO: documentation
abstract class StreamSourceS<E> implements StreamDataSourceInterface<E>
{

    private HashSet<DataListenerInterface<E>> listeners = new HashSet<>();

    //////////////////////////////
    /// @brief add a DataListenerInterface as output
    /// @param listener the chained ouput
    //////////////////////////////
    public final void subscribe(DataListenerInterface<E> listener) {
        if (this.listeners.contains(listener)) return;
        this.listeners.add(listener);
    }

    //////////////////////////////
    /// @brief remove a DataListenerInterface as output
    /// @param listener the listener to remove as chained output
    //////////////////////////////
    public final void unsubscribe(DataListenerInterface<E> listener) {
        if (!this.listeners.contains(listener)) return;
        this.listeners.remove(listener);
    }

    protected HashSet<DataListenerInterface<E>> getListeners()
    {
        return listeners;
    }

}
