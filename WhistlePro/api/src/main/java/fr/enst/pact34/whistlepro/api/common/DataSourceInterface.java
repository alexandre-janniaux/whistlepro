package fr.enst.pact34.whistlepro.api.common;


public interface DataSourceInterface<E> {

    //////////////////////////////
    /// @brief add a DataListenerInterface as output
    /// @param listener the chained ouput
    //////////////////////////////
    void subscribe(DataListenerInterface<E> listener);

    //////////////////////////////
    /// @brief remove a DataListenerInterface as output
    /// @param listener the listener to remove as chained output
    //////////////////////////////
    void unsubscribe(DataListenerInterface<E> listener);
}
