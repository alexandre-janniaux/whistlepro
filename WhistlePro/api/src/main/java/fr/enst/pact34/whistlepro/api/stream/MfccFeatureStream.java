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
        // Notice feature provider
        FeatureProviderInterface
{
    private DataSource<ArrayList<Double>> datasource = new DataSource<>();

    private MfccFeatureProvider mfcc = new MfccFeatureProvider();

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
    public void onPushData(DataSource<Spectrum> source, ArrayList<Spectrum> inputData) {
        // TODO: do the computation work and put the data (cf DataSource)
        // TODO: lock data when processing
        ArrayList<ArrayList<Double>> results = new ArrayList<>();
        results.ensureCapacity(inputData.size());
        for(int index=0; index < inputData.size(); ++index)
        {
            results.add(mfcc.processMfcc(inputData.get(index)));
        }

        this.datasource.push(results);
    }

}
