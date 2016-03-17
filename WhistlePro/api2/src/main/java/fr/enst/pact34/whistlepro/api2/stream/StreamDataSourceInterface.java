package  fr.enst.pact34.whistlepro.api2.stream;


interface StreamDataSourceInterface<E> {

    //////////////////////////////
    /// @brief add a StreamDataListenerInterface as output
    /// @param listener the chained ouput
    //////////////////////////////
    void subscribe(StreamDataListenerInterface<E> listener);

    //////////////////////////////
    /// @brief remove a StreamDataListenerInterface as output
    /// @param listener the listener to remove as chained output
    //////////////////////////////
    void unsubscribe(StreamDataListenerInterface<E> listener);
}
