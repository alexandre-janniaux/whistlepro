package fr.enst.pact34.whistlepro.api.common;

import java.util.ArrayList;

public interface DataListenerInterface<E> {

    
    //////////////////////////////
    /// @brief called whenever data is ready to be processed by the listener
    //////////////////////////////
    void onPushData(DataSource<E> source, ArrayList<E> inputData);


    ////////////////////////////// 
    /// @brief called when a transaction of data ends
    ////////////////////////////// 
    void onCommit(DataSource<E> source);

    ////////////////////////////// 
    /// @brief called when a transaction of data starts
    ////////////////////////////// 
    void onTransaction(DataSource<E> source);

}
