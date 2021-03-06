package fr.enst.pact34.whistlepro.api.common;

import java.util.HashSet;

//TODO: documentation
public class DataSource<E> implements DataSourceInterface<E>{

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
    
    //////////////////////////////
    /// @brief fillOut data to listeners
    /// @param outputData the data to broadcast
    //////////////////////////////
    public final void fillOut(E outputData) {
        for (DataListenerInterface<E> out : this.listeners)
        {
            out.fillIn(this, outputData);
        }
    }

}
