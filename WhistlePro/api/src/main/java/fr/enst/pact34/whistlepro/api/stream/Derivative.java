package fr.enst.pact34.whistlepro.stream;

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
        DataSourceInterface<ArrayList<Double>>,
        DataListenerInterface<ArrayList<Double>>,
        JobProviderInterface
{
    private DataSource<ArrayList<Double>> datasource = new DataSource<>();

    private ConvolutionInterface convolution;
    private int n;

    private ArrayList<ArrayList<Double>> storedData = new ArrayList<>();
    private double[] buffer;

    public Derivative(int nbMean) {
        this.n = nbMean;
        double[] kernel = new double[nbMean*2];
        
        for(int i=0; i<nbMean*2; ++i) kernel[i] = i <nbMean ? -1 : 1;

        convolution = new Convolution1D(2*nbMean, kernel);
    }

    public void onPushData(DataSource<ArrayList<Double>> source, ArrayList<ArrayList<Double>> data) {
        this.storedData.addAll(data);
        // TODO: store the data to compute the derivative incrementally
    }

    @Override
    public void subscribe(DataListenerInterface<ArrayList<Double>> listener) {
        this.datasource.unsubscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<ArrayList<Double>> listener) {
        this.datasource.unsubscribe(listener);
    }

    @Override
    public void onCommit(DataSource<ArrayList<Double>> source) {
        // TODO: unlock data processing
    }

    @Override
    public void onTransaction(DataSource<ArrayList<Double>> source) {
        // TODO: use previous array 
        this.buffer = new double[this.storedData.size()];
        for(int i=0; i < this.storedData.size(); ++i) this.buffer[i] = (double) this.storedData.get(0).get(i); //FIXME get(0)
        this.storedData.clear();   
       // TODO: lock data processing
    }

    @Override
    public void doWork() {
        // TODO: use previous array
        // -n is for boundsof the array
        double[] output = convolution.convoluate(this.buffer,0, buffer.length-this.n);

    }
    
    @Override
    public boolean isWorkAvailable() {
        return false;
    }
}
