package fr.enst.pact34.whistlepro.api.common;

import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import java.util.ArrayList;

public interface DataListenerInterface<E> {

    
    //////////////////////////////
    /// @brief called whenever data is ready to be processed by the listener
    //////////////////////////////
    void onPushData(DataSourceInterface<E> source, ArrayList<E> inputData);


    ////////////////////////////// 
    /// @brief called when a transaction of data ends
    ////////////////////////////// 
    void onCommit(DataSourceInterface<E> source);

    ////////////////////////////// 
    /// @brief called when a transaction of data starts
    ////////////////////////////// 
    void onTransaction(DataSourceInterface<E> source);

}
