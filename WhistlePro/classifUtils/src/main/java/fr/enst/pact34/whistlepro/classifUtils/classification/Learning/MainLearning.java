package fr.enst.pact34.whistlepro.classifUtils.classification.Learning;


import fr.enst.pact34.whistlepro.api.common.FileOperator;
import fr.enst.pact34.whistlepro.api.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api.classification.TrainExampleInterface;

import java.util.ArrayList;

/**
 * Created by  Mohamed on 27/02/16.
 */
public class MainLearning {

    private static final int NB_CLASSIFIERS_TO_CREATE = 5;
    private static final int NB_FEATURES = 13;
    private static String classifierSavefileName = "data/voyelles.scs";

    public static void main(String[] args) {

        System.out.println("Start learning ... ");

        //reading file
        System.out.println("Reading examples in database : "+MfccDataBaseGenerator.databaseFileName);
        ArrayList<String> strs = FileOperator.getLinesFromFile(MfccDataBaseGenerator.databaseFileName);
        System.out.println(strs.size() + " line read from database.");

        //building example for learning
        System.out.println("Building list of examples.");
        ArrayList<TrainExampleInterface> examples = new ArrayList<>();
        ArrayList<String> classes = new ArrayList<>();

        for (String str: strs ) {
            Example ex = new Example.Builder().fromString(str).build();
            if(ex == null) continue;

            examples.add(ex);

            int i ;
            for(i = 0; i < classes.size(); i++)
            {
                if(classes.get(i).equalsIgnoreCase(ex.getClasse())==true) break;
            }

            if(i == classes.size())
            {
                classes.add(ex.getClasse());
            }
        }

        System.out.println(examples.size() + " examples built in the following classes :");
        for (int i = 0; i < classes.size(); i++) {
            System.out.print(classes.get(i));
            if(i==classes.size()-1) System.out.println();
            else System.out.print(", ");
        }

        //Build classifiers

        System.out.println("Starting learning on examples.");

        MultipleStrongClassifiers classifiers =
                MultipleStrongClassifiersLearner.buildClassifiers(
                        classes,
                        examples,
                        NB_FEATURES,
                        NB_CLASSIFIERS_TO_CREATE);

        FileOperator.saveToFile(classifierSavefileName, classifiers.toString());

        System.out.println("Classifiers built.");

        System.out.println("mean = "
                +MultipleStrongClassifiersLearner.buildAndValidate(
                classes,
                examples,
                NB_FEATURES,
                NB_CLASSIFIERS_TO_CREATE
        ));



        System.out.println("End of learning.");
    }


}
