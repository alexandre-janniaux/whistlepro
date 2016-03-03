package classification.Learning;

import classification.TrainExampleInterface;

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
