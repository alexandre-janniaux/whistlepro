package stream;

import common.DataListenerInterface;
import common.DataSourceInterface;
import common.JobProviderInterface;
import features.MfccFeatureProvider;
import classification.FeatureProviderInterface;
import java.util.ArrayList;

import common.Spectrum;

public class MfccFeatureStream
    extends 
        // Output an arraylist of double (the features)
        DataSourceInterface<ArrayList<Double>> 
    implements 
        // Receive an arraylist of double as input (the signal itself, smoothed or not)
        DataListenerInterface<Spectrum>,
        // Define a possible parallel job 
        JobProviderInterface,
        // Notice feature provider
        FeatureProviderInterface
{
    private MfccFeatureProvider mfcc = new MfccFeatureProvider();
    private ArrayList<Spectrum> storedData = new ArrayList<>(); 
    private int index = 0;

    @Override
    public int countFeatures() {
        return this.mfcc.countFeatures();
    }

    @Override
    public void onPushData(DataSourceInterface<Spectrum> source, ArrayList<Spectrum> data) {
       this.storedData.addAll(data);
    }

    @Override
    public void onCommit(DataSourceInterface<Spectrum> source) {
        // TODO: Unlock data processing
    }

    @Override
    public void onTransaction(DataSourceInterface<Spectrum> source) {
        // TODO: Lock data processing
    }

    @Override
    public void doWork() {
        // TODO: do the computation work and put the data (cf DataSourceInterface)
        // TODO: lock data when processing, then unlocked it and activate the transaction lock to send the data in push
        ArrayList<ArrayList<Double>> results = new ArrayList<>();
        results.ensureCapacity(this.storedData.size());
        for(int index=0; index < this.storedData.size(); ++index)
        {
            results.add(mfcc.processMfcc(this.storedData.get(index)));
        }
        this.storedData.clear();

        transaction();
        push(results);
        commit();
    }

    @Override
    public boolean isWorkAvailable() {
        // TODO: If enough data is available for work, return true
        return this.storedData.size()>0;
    }

}
