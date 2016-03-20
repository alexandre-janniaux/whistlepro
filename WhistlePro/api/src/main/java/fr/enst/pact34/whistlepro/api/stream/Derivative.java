package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal;
import fr.enst.pact34.whistlepro.api.common.DoubleSignalInterface;
import fr.enst.pact34.whistlepro.api.common.ConvolutionInterface;
import fr.enst.pact34.whistlepro.api.common.Convolution1D;

class Derivative
    //extends DataSource<ArrayList<Double>>
    implements
        DataSourceInterface<DoubleSignalInterface>,
        DataListenerInterface<DoubleSignalInterface>
{
    private DataSource<DoubleSignalInterface> datasource = new DataSource<>();

    private ConvolutionInterface convolution;
    private int n;

    private double[] lastbuffer;
    private double[] buffer;

    public Derivative(int nbMean) {
        this.n = nbMean;
        this.lastbuffer = new double[this.n];
        double[] kernel = new double[nbMean*2];
        
        for(int i=0; i<nbMean*2; ++i) kernel[i] = i <nbMean ? -1 : 1;

        convolution = new Convolution1D(2*nbMean, kernel);
    }

    public void fillIn(DataSource<DoubleSignalInterface> source, DoubleSignalInterface inputData) {
        // TODO: use previous array
        // -n is for bounds of the array
        // TODO: check and test impl (fast change done)

        boolean useLastBuffer = this.lastbuffer.length>this.n;
        int start = useLastBuffer ? this.n : 0;
        int ssize = inputData.getSignal().length;
        int size = useLastBuffer ? this.n + ssize : ssize;
        this.buffer = new double[size];

        if (useLastBuffer) for(int i=0; i<n; ++i) this.buffer[i] = this.lastbuffer[i];
        for(int i=start; i < size; ++i) {
            this.buffer[i] = inputData.getSignal()[i];
            if (ssize-i <= this.n) {
                this.lastbuffer[i-ssize+this.n] = inputData.getSignal()[i];
            }
        }

        double[] output = convolution.convoluate(this.buffer,0, buffer.length-this.n);
        DoubleSignalInterface outputData = new DoubleSignal(output,inputData.getNbSamples(),inputData.getSampleFrequency());
        this.datasource.fillOut(outputData);
    }


    @Override
    public void subscribe(DataListenerInterface<DoubleSignalInterface> listener) {
        this.datasource.unsubscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<DoubleSignalInterface> listener) {
        this.datasource.unsubscribe(listener);
    }
}
