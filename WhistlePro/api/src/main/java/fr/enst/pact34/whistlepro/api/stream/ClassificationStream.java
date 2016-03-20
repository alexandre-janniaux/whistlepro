package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.classification.SampleClassif;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2D;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2DInterface;
import fr.enst.pact34.whistlepro.api.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.transformers;

import java.util.ArrayList;

public class ClassificationStream
    implements
        DataSourceInterface<DoubleSignal2DInterface>,
        // Receive an arraylist of double as input (the features) 
        DataListenerInterface<DoubleSignal2DInterface>
{

    private DataSource<DoubleSignal2DInterface> datasource = new DataSource<>();
    private ArrayList<SampleClassif> storedData = new ArrayList<>();

    MultipleStrongClassifiers classifier = null;

    private ArrayList<String> classes = null;

    public ClassificationStream(String data) {
        classifier =
        new MultipleStrongClassifiers.Builder()
                .fromString(data)
                .build();
        classes = classifier.classes();
    }

    @Override
    public void subscribe(DataListenerInterface<DoubleSignal2DInterface> listener) {
        this.datasource.subscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<DoubleSignal2DInterface> listener) {
        this.datasource.unsubscribe(listener);
    }

    public ArrayList<String> getClasses()
    {
        return (ArrayList<String>) classes.clone();
    }

    public String getClasse(int index)
    {
        return classes.get(index);
    }

    @Override
    public void fillIn(DataSource<DoubleSignal2DInterface> source, DoubleSignal2DInterface data) {

        int signalLength = data.getSignal().length;

        for (int i = 0; i < data.getSignal().length; i++) {

            if(data.getSignal()[i].length != 13) continue; //FIXME: hardcoded size of features

            SampleClassif s =  new SampleClassif.Builder().fromValues(transformers.doubleToArray(data.getSignal()[i])).build();

            if(s!=null) storedData.add(s);
        }

        double[][] results = new double[signalLength][];

        for(int index=0; index < signalLength; ++index)
        {
            results[index] = transformers.arrayToDouble(classifier.classify(storedData.get(index)));
        }
        this.storedData.clear();

        DoubleSignal2DInterface outputData = new DoubleSignal2D(results, data.getNbPoints(), data.getFrequencySample());

        this.datasource.fillOut(outputData);
    }

}
