package fr.enst.pact34.whistlepro.api.test.classification;

import fr.enst.pact34.whistlepro.api.classification.Learning.MultipleStrongClassifiersLearner;
import fr.enst.pact34.whistlepro.api.classification.TrainExampleInterface;
import fr.enst.pact34.whistlepro.api.classification.test.Example;
import fr.enst.pact34.whistlepro.api.common.FileOperator;

import static org.junit.Assert.assertTrue;
import org.junit.Test;


import java.util.ArrayList;

/**
 * Created by  Mohamed on 11/03/16.
 */
public class ClassifierBuildingTest {

    protected int NB_CLASSIFIERS_TO_CREATE = 50;
    protected int NB_FEATURES= 20;
    protected String datafileName = "../testData/classification/instrument_features.csv";

    @Test
    public void testBuilding() {

        ArrayList<String> strs = FileOperator.getLinesFromFile(datafileName);

        //creating samples
        ArrayList<TrainExampleInterface> samples = new ArrayList<>();
        ArrayList<String> classes = new ArrayList<String>();

        for(String s : strs)
        {

            int i = 0;
            Example.Builder exB = new Example.Builder().fromString(s) ;
            if(exB.isValid() == false)
                continue;

            Example ex = exB.build();
            samples.add(ex);

            for(i = 0; i < classes.size(); i++)
            {
                if(classes.get(i).equalsIgnoreCase(ex.getClasse())==true) break;
            }

            if(i == classes.size())
            {
                classes.add(ex.getClasse());
            }
        }

        double mean = MultipleStrongClassifiersLearner.buildAndValidate(classes, samples,
                    NB_FEATURES,
                    NB_CLASSIFIERS_TO_CREATE
            );

        // résultat normalement entre 5% et 20%;
        assertTrue(mean > 0.05);
        assertTrue(mean < 0.2);
    }


}
