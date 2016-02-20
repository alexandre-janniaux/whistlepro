package classification.test;

import classification.FeatureProviderInterface;
import classification.FileOperator;
import classification.MultipleStrongClassifiersLearner;

import java.util.ArrayList;

/**
 * Created by mms on 20/02/16.
 */
public class TestValidation {


    public static void main(String[] args) {

        //reading file
        ArrayList<String> strs = FileOperator.getLinesFromFile(TestClassifierLearning.datafileName);

        //creating samples
        ArrayList<FeatureProviderInterface> samples = new ArrayList<>();
        ArrayList<String> classes = new ArrayList<String>();

        for (String s : strs) {

            int i = 0;
            Example.Builder exB = new Example.Builder().fromString(s);
            if (exB.isValid() == false)
                continue;

            Example ex = exB.build();
            samples.add(ex);

            for (i = 0; i < classes.size(); i++) {
                if (classes.get(i).equalsIgnoreCase(ex.getClasse()) == true) break;
            }

            if(i == classes.size())
            {
                classes.add(ex.getClasse());
            }
        }

        System.out.println("mean = "+MultipleStrongClassifiersLearner.buildAndValidate(classes,samples,
                TestClassifierLearning.NB_FEATURES,
                10 //TestClassifierLearning.NB_CLASSIFIERS_TO_CREATE
        ));
    }
}