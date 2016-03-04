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
        DataListenerInterface<Double>,
        JobProviderInterface
{
    private DataSource<Double> datasource = new DataSource<>();

    private ConvolutionInterface convolution;
    private int n;

    private ArrayList<Double> storedData = new ArrayList<>();
    private double[] lastbuffer;
    private double[] buffer;

    public Derivative(int nbMean) {
        this.n = nbMean;
        this.lastbuffer = new double[this.n];
        double[] kernel = new double[nbMean*2];
        
        for(int i=0; i<nbMean*2; ++i) kernel[i] = i <nbMean ? -1 : 1;

        convolution = new Convolution1D(2*nbMean, kernel);
    }

    public void onPushData(DataSource<Double> source, ArrayList<Double> data) {
        this.storedData.addAll(data);
        // TODO: store the data to compute the derivative incrementally
    }

    @Override
    public void subscribe(DataListenerInterface<Double> listener) {
        this.datasource.unsubscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<Double> listener) {
        this.datasource.unsubscribe(listener);
    }

    @Override
    public void doWork() {
        // TODO: use previous array
        // -n is for boundsof the array
        assert(this.storedData.size()>this.n);

        boolean useLastBuffer = this.lastbuffer.length>this.n;
        int start = useLastBuffer ? this.n : 0;
        int ssize = this.storedData.size();
        int size = useLastBuffer ? this.n + this.storedData.size() : this.storedData.size();
        this.buffer = new double[size];

        if (useLastBuffer) for(int i=0; i<n; ++i) this.buffer[i] = this.lastbuffer[i];
        for(int i=start; i < size; ++i) {
            this.buffer[i] = this.storedData.get(i);
            if (ssize-i <= this.n) {
                this.lastbuffer[i-ssize+this.n] = this.storedData.get(i);
            }
        }
        this.storedData.clear();

        double[] output = convolution.convoluate(this.buffer,0, buffer.length-this.n);
        ArrayList<Double> toPush = new ArrayList<>();
        toPush.ensureCapacity(output.length);
        for(int i=0; i<output.length; ++i) toPush.add(output[i]);

        this.datasource.push(toPush);
    }
    
    @Override
    public boolean isWorkAvailable() {
        return this.storedData.size() > this.n;
    }
}
