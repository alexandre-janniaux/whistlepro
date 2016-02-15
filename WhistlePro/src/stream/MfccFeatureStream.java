package stream;

import common.DataListenerInterface;
import common.DataSourceInterface;
import common.JobProviderInterface;
import java.util.ArrayList;
import features.MfccFeatureProvider;

public class MfccFeatureStream
    extends 
        // Output an arraylist of double (the features)
        DataSourceInterface<ArrayList<Double>> 
    implements 
        // Receive an arraylist of double as input (the signal itself, smoothed or not)
        DataListenerInterface<ArrayList<Double>>,
        // Define a possible parallel job 
        JobProviderInterface
{
    private MfccFeatureProvider mfcc = new MfccFeatureProvider();
    private ArrayList<ArrayList<Double>> storedData = new ArrayList<>(); 
    private int index = 0;

    @Override
    public void onPushData(DataSourceInterface<ArrayList<Double>> source, ArrayList<Double> data) {
       // TODO: Store the data to compute the coefficients
    }

    @Override
    public void onCommit(DataSourceInterface<ArrayList<Double>> source) {
        // TODO: Unlock data processing
    }

    @Override
    public void onTransaction(DataSourceInterface<ArrayList<Double>> source) {
        // TODO: Lock data processing
    }

    @Override
    public void doWork() {
        // TODO: do the computation work and put the data (cf DataSourceInterface)
        // TODO: lock data when processing, then unlocked it and activate the transaction lock to send the data in push
        while(this.index < this.storedData.size())
        {
            double[] input = new double[storedData.get(this.index).size()];
            for(int i=0; i<this.storedData.get(index).size(); ++i) input[i] = (double) this.storedData.get(index).get(i);

            ArrayList<Double> coeffs = mfcc.processMfcc(input);
            push(coeffs); // TODO: push full array
            index++;
        }
        commit();
    }

    @Override
    public boolean isWorkAvailable() {
        // TODO: If enough data is available for work, return true
        return false;
    }

}
