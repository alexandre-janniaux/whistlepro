package common;

import common.DataSourceInterface;

public interface DataListenerInterface<E> {

    
    //////////////////////////////
    /// @brief called whenever data is ready to be processed by the listener
    //////////////////////////////
    void onPushData(DataSourceInterface<E> source, E inputData);


    ////////////////////////////// 
    /// @brief called when a transaction of data ends
    ////////////////////////////// 
    void onCommit(DataSourceInterface<E> source);

    ////////////////////////////// 
    /// @brief called when a transaction of data starts
    ////////////////////////////// 
    void onTransaction(DataSourceInterface<E> source);

}
