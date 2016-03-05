package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.common.*;

import java.util.ArrayList;

public class SpectrumStream
    implements
        // Output an arraylist of double (the spectrum)
        DataSourceInterface<Spectrum>,
        // Receive an arraylist of double as input (the signal itself, smoothed or not)
        DataListenerInterface<SignalSample>,
        // Define a possible parallel job 
        JobProviderInterface
{
    private DataSource<Spectrum> datasource = new DataSource<>();

    private ArrayList<SignalSample> storedData = new ArrayList<>();

    @Override
    public void unsubscribe(DataListenerInterface<Spectrum> listener) {
        datasource.unsubscribe(listener);
    }

    @Override
    public void subscribe(DataListenerInterface<Spectrum> listener) {
        datasource.subscribe(listener);
    }

    @Override
    public void onPushData(DataSource<SignalSample> source, ArrayList<SignalSample> data) {
       synchronized (this.storedData) {
           this.storedData.addAll(data);
       }
    }

    @Override
    public void doWork() {
        ArrayList<SignalSample> inputs= new ArrayList<>();
        synchronized (this.storedData) {
            while(storedData.size()>0){
                inputs.add(storedData.remove(0));
            }
        }

        ArrayList<Spectrum> spectrums_out= new ArrayList<>();
        while(inputs.size()>0) {
            SignalSample tmp = inputs.remove(0);
            double[] fft = transformers.fft(tmp.getSignal());
            double coefAdap = 2/tmp.getSignal().length;
            for(int i=0; i < fft.length; i++)
            {
                fft[i]=fft[i]*coefAdap;
            }
            spectrums_out.add(new Spectrum(tmp.getSignal().length,tmp.getFs(),fft));
        }

        this.datasource.transaction();
        this.datasource.push(spectrums_out);
        this.datasource.commit();
        //TODO: push output
    }

    @Override
    public boolean isWorkAvailable() {
        // TODO: If enough data is available for work, return true
        return (storedData.size()>0);
    }

    @Override
    public void onCommit(DataSource<SignalSample> source) {

    }

    @Override
    public void onTransaction(DataSource<SignalSample> source) {

    }

}
