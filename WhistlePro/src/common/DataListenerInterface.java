package common;

import common.DataSourceInterface;

public interface DataListenerInterface<E> {

    
    //////////////////////////////
    /// @brief called whenever data is ready to be processed by the listener
    //////////////////////////////
    void onPushData(DataSourceInterface<E> source, E inputData);

}
