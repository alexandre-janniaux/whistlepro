package stream;

import common.DataListenerInterface;
import common.DataSourceInterface;
import common.JobProviderInterface;
import java.util.ArrayList;

public class SpectrumStream 
    extends 
        // Output an arraylist of double (the spectrum)
        DataSourceInterface<ArrayList<Double>> 
    implements 
        // Receive an arraylist of double as input (the signal itself, smoothed or not)
        DataListenerInterface<ArrayList<Double>>,
        // Define a possible parallel job 
        JobProviderInterface
{

    @Override
    public void onPushData(DataSourceInterface<ArrayList<Double>> source, ArrayList<Double> data) {
       // TODO: Store the data to compute spectrum
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

}
