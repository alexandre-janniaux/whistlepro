package common;

import java.util.HashSet;
import java.util.ArrayList;
import common.DataListenerInterface;

//TODO: documentation
public class DataSourceInterface<E> {

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
    /// @brief push data to listeners
    /// @param outputData the data to broadcast
    //////////////////////////////
    protected final void push(ArrayList<E> outputData) {
        for (DataListenerInterface<E> out : this.listeners)
        {
            out.onPushData(this, outputData);
        }
    }


    //////////////////////////////
    /// @brief allow following module to process data
    //////////////////////////////
    protected final void commit() {
        for (DataListenerInterface<E> out : this.listeners)
        {
            out.onCommit(this);
        }
    }

    //////////////////////////////
    /// @brief allow following module to process data
    //////////////////////////////
    protected final void transaction() {
        for (DataListenerInterface<E> out : this.listeners)
        {
            out.onTransaction(this);
        }
    }
}
