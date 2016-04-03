package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;

import fr.enst.pact34.whistlepro.api.classification.TrainExampleInterface;
import fr.enst.pact34.whistlepro.api.common.FileOperator;
import fr.enst.pact34.whistlepro.classifUtils.classification.Learning.Example;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by mms on 03/04/16.
 */
public class ClassifierLearner {

    MfccDb db = new MfccDb();

    public ClassifierLearner()
    {
    }

    public ClassifierLearner(String dbData)
    {
        loadFromFile(dbData);
    }

    public void loadFromFile(String e)
    {
        String ret = FileOperator.getDataFromFile(e);
        if(ret.isEmpty()==false)
            db.fromString(ret);
    }

    public void buildExampleList()
    {
        ArrayList<TrainExampleInterface> examples = new ArrayList<>();
        HashSet<String> classes = new HashSet<>();

        for (String classe: db.getClassesList()
             ) {
            classes.add(classe);
            MfccDb.ElementInterface[] elements = db.getElementsList(classe);
            for (int i = 0; i < elements.length; i++) {
                ArrayList<String> mfccs = elements[i].getMfccs();
                for (int j = 0; j < mfccs.size(); j++) {
                    Example ex = new Example.Builder().fromString(mfccs.get(i)+classe).build();
                    if(ex == null) continue;
                    examples.add(ex);
                }
            }
        }

    }

}
