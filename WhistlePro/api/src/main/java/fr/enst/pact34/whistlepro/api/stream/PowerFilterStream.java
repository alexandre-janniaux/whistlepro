package fr.enst.pact34.whistlepro.api.stream;


import java.util.ArrayList;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataStreamInterface;

public class PowerFilterStream
    implements DataStreamInterface<Double, Double> {

    private final int windowSize;
    private final int step;
    private double max_power = 1.;

    private final DataSource<Double> outputSource = new DataSource<>();

    public PowerFilterStream(int windowSize, int step) {
        this.windowSize = windowSize;
        this.step = step;
    }

    @Override
    public void onPushData(DataSource<Double> source, ArrayList<Double> inputData) {
        for(int i=0; i<inputData.size(); i += this.step) {
            int power = 0;
            for (int j=i; j<inputData.size() && j<i+this.step; i+=this.step) {
                power += inputData.get(j);
            }
            if (power > this.max_power) {
                this.max_power = power;
            }
            else if (power/this.max_power < 0.10) {
                // FRAME IS EMPTY, TAKE NEXT ONE
                continue;
            }

            ArrayList<Double> data = (ArrayList)inputData.subList(i, i+this.step);
            this.outputSource.push(data);
            i+=this.windowSize-this.step;

        }

        this.outputSource.push(inputData);
    }

    @Override
    public void subscribe(DataListenerInterface<Double> listener) {
        this.outputSource.subscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<Double> listener) {
        this.outputSource.unsubscribe(listener);
    }
}
