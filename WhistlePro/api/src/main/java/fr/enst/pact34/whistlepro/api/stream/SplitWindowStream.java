package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataStreamInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2D;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2DInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignalInterface;

/**
 * Created by alexandre on 13/03/16.
 */
public class SplitWindowStream implements DataStreamInterface<DoubleSignalInterface, DoubleSignal2DInterface> {

    private final DataSource<DoubleSignal2DInterface> dataSource;
    private final int windowSize;
    private final int overlapping;
    private DoubleSignal2D output;

    public SplitWindowStream(int windowSize, int overlapping) {
        this.windowSize = windowSize;
        this.overlapping = overlapping;
        this.dataSource = new DataSource<>();
    }

    @Override
    public void onPushData(DataSource<DoubleSignalInterface> source, DoubleSignalInterface inputData) {

        // TODO: improve code when the last window can't be completed (delay the computation).

        //////////////////////////////
        // the number of window we can extract from the signal
        int nbWindow = Integer.valueOf(inputData.getSignal().length / (this.windowSize-this.overlapping)); //TODO: the last todo needs this to be +1

        //////////////////////////////
        // initialisation of intern output buffer
        if (output == null) {
            this.output = new DoubleSignal2D(new double[nbWindow][], this.windowSize, inputData.getSampleFrequency());
            for(int i=0; i<nbWindow; ++i) this.output.getSignal()[i] = new double[windowSize];

        }else if (output.getSignal().length != nbWindow) {
            this.output.setSignal(new double[nbWindow][]);
            for(int i=0; i<nbWindow; ++i) this.output.getSignal()[i] = new double[windowSize];
        }

        //////////////////////////////
        // computation of the window
        int step = this.windowSize - this.overlapping;
        for(int w=0; w < nbWindow; ++w) {
            for(int index=0; index < windowSize; ++index)
            {
                this.output.getSignal()[w][index] = inputData.getSignal()[step*w + index];
            }
        }

        this.dataSource.push(this.output);
    }

    @Override
    public void subscribe(DataListenerInterface<DoubleSignal2DInterface> listener) {
        this.dataSource.subscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<DoubleSignal2DInterface> listener) {
        this.dataSource.subscribe(listener);
    }
}
