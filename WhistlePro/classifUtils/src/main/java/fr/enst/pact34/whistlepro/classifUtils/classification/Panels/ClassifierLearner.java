package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;

import fr.enst.pact34.whistlepro.api.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api.classification.TrainExampleInterface;
import fr.enst.pact34.whistlepro.api.common.FileOperator;
import fr.enst.pact34.whistlepro.classifUtils.classification.Learning.Example;
import fr.enst.pact34.whistlepro.classifUtils.classification.Learning.MultipleStrongClassifiersLearner;

import java.util.ArrayList;

/**
 * Created by mms on 03/04/16.
 */
public class ClassifierLearner {

    public static void buildClassifier(String dbFile, int nb_classifier,String classifierFilename)
    {
        MfccDb db = new MfccDb();
        // load db
        String ret = FileOperator.getDataFromFile(dbFile);
        if(ret.isEmpty()==false)
            db.fromString(ret);

        // creation examples
        ArrayList<TrainExampleInterface> examples = null;
        ArrayList<String> classes = null;
        examples = new ArrayList<>();
        classes = new ArrayList<>();

        for (String classe: db.getClassesList()
             ) {
            classes.add(classe);
            MfccDb.ElementInterface[] elements = db.getElementsList(classe);
            for (int i = 0; i < elements.length; i++) {
                ArrayList<String> mfccs = elements[i].getMfccs();
                for (int j = 0; j < mfccs.size(); j++) {
                    Example ex = new Example.Builder().fromString(mfccs.get(j)+classe).build();
                    if(ex == null) continue;
                    examples.add(ex);
                }
            }
        }

        //apprentissage
        MultipleStrongClassifiers classifiers = MultipleStrongClassifiersLearner.buildClassifiers(classes, examples,
                examples.get(0).countFeatures(), nb_classifier);

        FileOperator.saveToFile(classifierFilename, classifiers.toString());
    }

}
