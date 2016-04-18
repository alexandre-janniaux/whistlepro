package fr.enst.pact34.whistlepro.classifUtils.classification.Learning;

import fr.enst.pact34.whistlepro.api.classification.TrainExampleInterface;

/**
 * Created by mms on 27/02/16.
 */
public interface TrainObjectInterface extends TrainExampleInterface {
    //return 1 or -1
    int isValid();
    void setValid(boolean valid);

    void setWeight(double w);
    double getWeight();
}
