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
        DataListenerInterface<Double>
{
    private DataSource<Spectrum> datasource = new DataSource<>();

    private final int windowSize;
    private final int overlap;

    public SpectrumStream(int windowSize, int overlap) {
        this.windowSize = windowSize;
        this.overlap = overlap;
    }

    @Override
    public void unsubscribe(DataListenerInterface<Spectrum> listener) {
        datasource.unsubscribe(listener);
    }

    @Override
    public void subscribe(DataListenerInterface<Spectrum> listener) {
        datasource.subscribe(listener);
    }

    @Override
    public void onPushData(DataSource<Double> source, ArrayList<Double> inputData) {

        ArrayList<Spectrum> outputData = new ArrayList<>();

        double[] inputs;
        inputs = new double[inputData.size()];
        for (int i = 0; i < inputData.size(); ++i) {
            inputs[i] = (double) inputData.get(i);
        }


        double[] output = transformers.fft(inputs);
        Spectrum spectrum = new Spectrum(this.windowSize, 16000, output); //TODO: don't hardcode F_e


        outputData.add(spectrum);
        this.datasource.push(outputData);
    }

}
