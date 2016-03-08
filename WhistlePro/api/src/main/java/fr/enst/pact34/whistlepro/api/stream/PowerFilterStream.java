package fr.enst.pact34.whistlepro.api.stream;


import java.util.ArrayList;
import java.util.Arrays;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataStreamInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal;
import fr.enst.pact34.whistlepro.api.common.DoubleSignalInterface;

public class PowerFilterStream
    implements DataStreamInterface<DoubleSignalInterface, DoubleSignalInterface> {

    private final int windowSize;
    private final int step;
    private double max_power = 1.;

    private final DataSource<DoubleSignalInterface> outputSource = new DataSource<>();

    public PowerFilterStream(int windowSize, int step) {
        this.windowSize = windowSize;
        this.step = step;
    }

    @Override
    public void onPushData(DataSource<DoubleSignalInterface> source, DoubleSignalInterface inputData) {
        int signalLength = inputData.getSignal().length;
        for(int i=0; i<signalLength; i += this.step) {
            int power = 0;
            for (int j=i; j<signalLength && j<i+this.step; i+=this.step) {
                power += inputData.getSignal()[j];
            }
            if (power > this.max_power) {
                this.max_power = power;
            }
            else if (power/this.max_power < 0.10) {
                // FRAME IS EMPTY, TAKE NEXT ONE
                continue;
            }

            DoubleSignalInterface output = new DoubleSignal(
                    Arrays.copyOfRange(inputData.getSignal(), i, i+this.step),
                    inputData.getNbSamples(),
                    inputData.getSampleFrequency()
            );
            this.outputSource.push(output);
            i+=this.windowSize-this.step;
        }

        this.outputSource.push(inputData);
    }

    @Override
    public void subscribe(DataListenerInterface<DoubleSignalInterface> listener) {
        this.outputSource.subscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<DoubleSignalInterface> listener) {
        this.outputSource.unsubscribe(listener);
    }
}
