package fr.enst.pact34.whistlepro.api.common;

import java.util.ArrayList;

public interface DataListenerInterface<E> {

    
    //////////////////////////////
    /// @brief called whenever data is ready to be processed by the listener
    //////////////////////////////
    void onPushData(DataSource<E> source, E inputData);


}
