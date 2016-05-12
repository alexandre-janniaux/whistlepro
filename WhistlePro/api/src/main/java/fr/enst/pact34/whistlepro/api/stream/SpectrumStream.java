package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.common.*;

public class SpectrumStream
    implements
        // Output an arraylist of double (the spectrum)
        DataSourceInterface<DoubleSignal2DInterface>,
        // Receive an arraylist of double as input (the signal itself, smoothed or not)
        DataListenerInterface<DoubleSignal2DInterface>

{
    private DataSource<DoubleSignal2DInterface> datasource = new DataSource<>();

    public SpectrumStream() {

    }

    @Override
    public void unsubscribe(DataListenerInterface<DoubleSignal2DInterface> listener) {
        datasource.unsubscribe(listener);
    }

    @Override
    public void subscribe(DataListenerInterface<DoubleSignal2DInterface> listener) {
        datasource.subscribe(listener);
    }

    @Override
    public void fillIn(DataSource<DoubleSignal2DInterface> source, DoubleSignal2DInterface inputData) {

        double[][] results = new double[inputData.getSignal().length][];

        for(int index=0; index < inputData.getSignal().length; ++index)
        {
            results[index] = transformers.fft(inputData.getSignal()[index]);
        }

        DoubleSignal2DInterface outputData = new DoubleSignal2D(results, inputData.getNbPoints(), inputData.getFrequencySample());
        this.datasource.fillOut(outputData);
    }

}
