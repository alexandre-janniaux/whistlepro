package fr.enst.pact34.whistlepro.api.stream;

import java.util.ArrayList;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.JobProviderInterface;
import fr.enst.pact34.whistlepro.api.common.ConvolutionInterface;
import fr.enst.pact34.whistlepro.api.common.Convolution1D;

class Derivative
    //extends DataSource<ArrayList<Double>>
    implements
        DataSourceInterface<Double>,
        DataListenerInterface<Double>
{
    private DataSource<Double> datasource = new DataSource<>();

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

    public void onPushData(DataSource<Double> source, ArrayList<Double> inputData) {
        // TODO: use previous array
        // -n is for bounds of the array
        // TODO: check and test impl (fast change done)

        boolean useLastBuffer = this.lastbuffer.length>this.n;
        int start = useLastBuffer ? this.n : 0;
        int ssize = inputData.size();
        int size = useLastBuffer ? this.n + inputData.size() : inputData.size();
        this.buffer = new double[size];

        if (useLastBuffer) for(int i=0; i<n; ++i) this.buffer[i] = this.lastbuffer[i];
        for(int i=start; i < size; ++i) {
            this.buffer[i] = inputData.get(i);
            if (ssize-i <= this.n) {
                this.lastbuffer[i-ssize+this.n] = inputData.get(i);
            }
        }

        double[] output = convolution.convoluate(this.buffer,0, buffer.length-this.n);
        ArrayList<Double> toPush = new ArrayList<>();
        toPush.ensureCapacity(output.length);
        for(int i=0; i<output.length; ++i) toPush.add(output[i]);

        this.datasource.push(toPush);
    }


    @Override
    public void subscribe(DataListenerInterface<Double> listener) {
        this.datasource.unsubscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<Double> listener) {
        this.datasource.unsubscribe(listener);
    }
}
