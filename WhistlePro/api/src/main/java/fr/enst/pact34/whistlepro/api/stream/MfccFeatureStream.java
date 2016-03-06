package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2D;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2DInterface;
import fr.enst.pact34.whistlepro.api.common.JobProviderInterface;
import fr.enst.pact34.whistlepro.api.features.MfccFeatureProvider;
import fr.enst.pact34.whistlepro.api.classification.FeatureProviderInterface;
import java.util.ArrayList;

import fr.enst.pact34.whistlepro.api.common.Spectrum;

public class MfccFeatureStream
    implements
        DataSourceInterface<DoubleSignal2DInterface>,
        // Receive an arraylist of double as input (the signal itself, smoothed or not)
        DataListenerInterface<DoubleSignal2DInterface>,
        // Notice feature provider
        FeatureProviderInterface
{
    private DataSource<DoubleSignal2DInterface> datasource = new DataSource<>();

    private MfccFeatureProvider mfcc = new MfccFeatureProvider();

    @Override
    public int countFeatures() {
        return this.mfcc.countFeatures();
    }

    @Override
    public void subscribe(DataListenerInterface<DoubleSignal2DInterface> listener) {
        this.datasource.subscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<DoubleSignal2DInterface> listener) {
        datasource.unsubscribe(listener);
    }

    @Override
    public void onPushData(DataSource<DoubleSignal2DInterface> source, DoubleSignal2DInterface inputData) {
        // TODO: do the computation work and put the data (cf DataSource)
        // TODO: lock data when processing

        double[][] results = new double[inputData.getSignal().length][];

        for(int index=0; index < inputData.getSignal().length; ++index)
        {
            Spectrum spectrum = new Spectrum(inputData.getNbPoints(), inputData.getFrequencySample(), inputData.getSignal()[index]);
            results[index] = mfcc.processMfcc(spectrum);
        }

        DoubleSignal2DInterface outputData = new DoubleSignal2D(results, inputData.getNbPoints(), inputData.getFrequencySample());
        this.datasource.push(outputData);
    }

}
