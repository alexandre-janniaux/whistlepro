package fr.enst.pact34.whistlepro.api.common;

/**
 * Created by alexandre on 20/03/16.
 */
public class DoubleSignal2DRequest implements OutputRequestInterface<DoubleSignal2DInterface> {

    private final int nbSamples;
    private final int sampleSize;

    public DoubleSignal2DRequest(int nbSamples, int sampleSize) {
        this.nbSamples = nbSamples;
        this.sampleSize = sampleSize;
    }

    @Override
    public boolean isValid(DoubleSignal2DInterface object) {
        if (object.getSignal().length != this.nbSamples) return false;
        for(int i=0; i<object.getSignal().length; ++i)
            if (object.getSignal()[i].length != this.sampleSize)
                return false;
        return true;
    }

    @Override
    public DoubleSignal2DInterface makeObject() {
        DoubleSignal2D signal = new DoubleSignal2D(new double[this.nbSamples][this.sampleSize], 0, 0.);
        return signal;
    }
}
