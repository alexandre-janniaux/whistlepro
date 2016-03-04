package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.JobProviderInterface;
import fr.enst.pact34.whistlepro.api.features.MfccFeatureProvider;
import fr.enst.pact34.whistlepro.api.classification.FeatureProviderInterface;
import java.util.ArrayList;

import fr.enst.pact34.whistlepro.api.common.Spectrum;

public class MfccFeatureStream
    implements
        DataSourceInterface<ArrayList<Double>>,
        // Receive an arraylist of double as input (the signal itself, smoothed or not)
        DataListenerInterface<Spectrum>,
        // Define a possible parallel job 
        JobProviderInterface,
        // Notice feature provider
        FeatureProviderInterface
{
    private DataSource<ArrayList<Double>> datasource = new DataSource<>();

    private MfccFeatureProvider mfcc = new MfccFeatureProvider();
    private ArrayList<Spectrum> storedData = new ArrayList<>(); 
    private int index = 0;

    @Override
    public int countFeatures() {
        return this.mfcc.countFeatures();
    }

    @Override
    public void subscribe(DataListenerInterface<ArrayList<Double>> listener) {
        this.datasource.subscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<ArrayList<Double>> listener) {
        datasource.unsubscribe(listener);
    }

    @Override
    public void onPushData(DataSource<Spectrum> source, ArrayList<Spectrum> data) {
       this.storedData.addAll(data);
    }

    @Override
    public void doWork() {
        // TODO: do the computation work and put the data (cf DataSource)
        // TODO: lock data when processing, then unlocked it and activate the transaction lock to send the data in push
        ArrayList<ArrayList<Double>> results = new ArrayList<>();
        results.ensureCapacity(this.storedData.size());
        for(int index=0; index < this.storedData.size(); ++index)
        {
            results.add(mfcc.processMfcc(this.storedData.get(index)));
        }
        this.storedData.clear();

        this.datasource.transaction();
        this.datasource.push(results);
        this.datasource.commit();
    }

    @Override
    public boolean isWorkAvailable() {
        // TODO: If enough data is available for work, return true
        return this.storedData.size()>0;
    }

}
