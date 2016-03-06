package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2D;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2DInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignalInterface;
import fr.enst.pact34.whistlepro.api.common.transformers;

public class SpectrumStream
    implements
        // Output an arraylist of double (the spectrum)
        DataSourceInterface<DoubleSignal2DInterface>,
        // Receive an arraylist of double as input (the signal itself, smoothed or not)
        DataListenerInterface<DoubleSignalInterface>

{
    private DataSource<DoubleSignal2DInterface> datasource = new DataSource<>();


    private final int windowSize;
    private final int overlap;


    public SpectrumStream(int windowSize, int overlap) {
        this.windowSize = windowSize;
        this.overlap = overlap;
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
    public void onPushData(DataSource<DoubleSignalInterface> source, DoubleSignalInterface inputData) {

        int signalLength = inputData.getSignal().length;
        double[] signal = inputData.getSignal();
        double[][] output = new double[1][]; //TODO: windowing with overlapping


        double[] spectrum = transformers.fft(signal);

        DoubleSignal2DInterface outputData = new DoubleSignal2D(
                output,
                this.windowSize,
                inputData.getSampleFrequency()
        );

        this.datasource.push(outputData);
    }

}
