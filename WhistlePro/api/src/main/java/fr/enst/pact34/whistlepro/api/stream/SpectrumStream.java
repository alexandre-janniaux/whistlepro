package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.JobProviderInterface;
import fr.enst.pact34.whistlepro.api.common.Spectrum;
import fr.enst.pact34.whistlepro.api.common.transformers;

import java.util.ArrayList;

public class SpectrumStream
    implements
        // Output an arraylist of double (the spectrum)
        DataSourceInterface<Spectrum>,
        // Receive an arraylist of double as input (the signal itself, smoothed or not)
        DataListenerInterface<Double>,
        // Define a possible parallel job 
        JobProviderInterface
{
    private DataSource<Spectrum> datasource = new DataSource<>();

    private ArrayList<Double> storedData = new ArrayList<>();

    @Override
    public void unsubscribe(DataListenerInterface<Spectrum> listener) {
        datasource.unsubscribe(listener);
    }

    @Override
    public void subscribe(DataListenerInterface<Spectrum> listener) {
        datasource.subscribe(listener);
    }

    @Override
    public void onPushData(DataSource<Double> source, ArrayList<Double> data) {
       synchronized (this.storedData) {
           this.storedData.addAll(data);
       }
    }

    @Override
    public void doWork() {
        double[] inputs;
        synchronized (this.storedData) {
            inputs = new double[this.storedData.size()];
            for (int i = 0; i < this.storedData.size(); ++i) {
                inputs[i] = (double) this.storedData.get(i);
            }
            this.storedData.clear();
        }

        double[] output = transformers.fft(inputs);
        //TODO: push output

    }

    @Override
    public boolean isWorkAvailable() {
        // TODO: If enough data is available for work, return true
        return false;
    }

}
