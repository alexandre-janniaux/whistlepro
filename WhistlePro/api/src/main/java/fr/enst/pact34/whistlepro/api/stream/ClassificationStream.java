package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.classification.SampleClassif;
import fr.enst.pact34.whistlepro.api.common.FileOperator;
import fr.enst.pact34.whistlepro.api.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.JobProviderInterface;
import java.util.ArrayList;

public class ClassificationStream 
    extends
        // Output an arraylist of events
        DataSourceInterface<ArrayList<Double>>
    implements 
        // Receive an arraylist of double as input (the features) 
        DataListenerInterface<ArrayList<Double>>,
        // Define a possible parallel job 
        JobProviderInterface
{

    ArrayList<SampleClassif> storedData = new ArrayList<>();

    private String classifierFileName = "data/voyelles.scs";
    MultipleStrongClassifiers classifier =
            new MultipleStrongClassifiers.Builder()
                    .fromString(FileOperator.getDataFromFile(classifierFileName))
                    .build();


    private final ArrayList<String> classes = classifier.classes();

    public ArrayList<String> getClasses()
    {
        return (ArrayList<String>) classes.clone();
    }

    public String getClasse(int index)
    {
        return classes.get(index);
    }

    @Override
    public void onPushData(DataSourceInterface<ArrayList<Double>> source, ArrayList<ArrayList<Double>> data) {

        for (int i = 0; i < data.size(); i++) {

            if(data.get(i).size() != 13) continue;

            SampleClassif s =  new SampleClassif.Builder().fromValues(data.get(i)).build();

            if(s!=null) storedData.add(s);

        }

       // TODO: Store the data
    }

    @Override
    public void doWork() {
        // TODO: do the computation work and put the data (cf DataSourceInterface)

        ArrayList<ArrayList<Double>> results = new ArrayList<>();
        results.ensureCapacity(this.storedData.size());

        for(int index=0; index < this.storedData.size(); ++index)
        {
            results.add(classifier.classify(storedData.get(index)));
        }
        this.storedData.clear();

        transaction();
        push(results);
        commit();

    }

    @Override
    public boolean isWorkAvailable() {
        // TODO: If enough data is available for work, return true
        return (storedData.size()>0);
    }

    @Override
    public void onCommit(DataSourceInterface<ArrayList<Double>> source) {
    }

    @Override
    public void onTransaction(DataSourceInterface<ArrayList<Double>> source) {

    }

}
