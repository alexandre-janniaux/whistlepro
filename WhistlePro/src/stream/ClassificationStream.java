package stream;

import common.DataListenerInterface;
import common.DataSourceInterface;
import common.JobProviderInterface;
import java.util.ArrayList;

public class ClassificationStream 
    //extends 
        // Output an arraylist of events
        // DataSourceInterface<ArrayList<Double>> 
    implements 
        // Receive an arraylist of double as input (the features) 
        DataListenerInterface<ArrayList<Double>>,
        // Define a possible parallel job 
        JobProviderInterface
{

    @Override
    public void onPushData(DataSourceInterface<ArrayList<Double>> source, ArrayList<Double> data) {
       // TODO: Store the data
    }

    @Override
    public void doWork() {
        // TODO: do the computation work and put the data (cf DataSourceInterface)
    }

    @Override
    public boolean isWorkAvailable() {
        // TODO: If enough data is available for work, return true
        return false;
    }

    @Override
    public void onCommit(DataSourceInterface<ArrayList<Double>> source) {
    }

    @Override
    public void onTransaction(DataSourceInterface<ArrayList<Double>> source) {

    }

}
