package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.classification.SampleClassif;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.FileOperator;
import fr.enst.pact34.whistlepro.api.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.JobProviderInterface;
import java.util.ArrayList;

public class ClassificationStream
    implements
        DataSourceInterface<ArrayList<Double>>,
        // Receive an arraylist of double as input (the features) 
        DataListenerInterface<ArrayList<Double>>,
        // Define a possible parallel job 
        JobProviderInterface
{

    private DataSource<ArrayList<Double>> datasource = new DataSource<>();
    private ArrayList<SampleClassif> storedData = new ArrayList<>();

    private String classifierFileName = "data/voyelles.scs";
    MultipleStrongClassifiers classifier =
            new MultipleStrongClassifiers.Builder()
                    .fromString(FileOperator.getDataFromFile(classifierFileName))
                    .build();




    private final ArrayList<String> classes = classifier.classes();

    @Override
    public void subscribe(DataListenerInterface<ArrayList<Double>> listener) {
        this.datasource.subscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<ArrayList<Double>> listener) {
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
    public void onPushData(DataSource<ArrayList<Double>> source, ArrayList<ArrayList<Double>> data) {

        for (int i = 0; i < data.size(); i++) {

            if(data.get(i).size() != 13) continue;

            SampleClassif s =  new SampleClassif.Builder().fromValues(data.get(i)).build();

            if(s!=null) storedData.add(s);

        }
    }

    @Override
    public void doWork() {
        ArrayList<ArrayList<Double>> results = new ArrayList<>();
        results.ensureCapacity(this.storedData.size());

        for(int index=0; index < this.storedData.size(); ++index)
        {
            results.add(classifier.classify(storedData.get(index)));
        }
        this.storedData.clear();

        this.datasource.transaction();
        this.datasource.push(results);
        this.datasource.commit();
    }

    @Override
    public boolean isWorkAvailable() {
        // TODO: If enough data is available for work, return true
        return (storedData.size()>0);
    }

    @Override
    public void onCommit(DataSource<ArrayList<Double>> source) {
    }

    @Override
    public void onTransaction(DataSource<ArrayList<Double>> source) {

    }

}
