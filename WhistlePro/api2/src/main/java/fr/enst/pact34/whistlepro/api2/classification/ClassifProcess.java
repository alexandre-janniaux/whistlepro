package fr.enst.pact34.whistlepro.api2.classification;

import fr.enst.pact34.whistlepro.api2.dataTypes.ClassifResults;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

/**
 * Created by mms on 16/04/16.
 */
public class ClassifProcess implements StreamProcessInterface<Signal, ClassifResults> {

    private MultipleStrongClassifiers classifier = null;
    private FeaturedObjectInterfaceWrapper objectInterface = new FeaturedObjectInterfaceWrapper();

    private static class FeaturedObjectInterfaceWrapper implements FeaturedObjectInterface {
        public void setA(Signal a) {
            this.a = a;
        }

        Signal a;

        @Override
        public double getFeature(int featureIndex) {
            return a.getValue(featureIndex);
        }

        @Override
        public int countFeatures() {
            return 13; //TODO ne pas noter en dur
        }
    }

    public ClassifProcess(MultipleStrongClassifiers c)
    {
        classifier = c;
        out = new double[classifier.nbOfClassifiers()];
    }

    private double out[] ;
    @Override
    public void process(Signal inputData, ClassifResults outputData) {
        objectInterface.setA(inputData);
        classifier.classify(objectInterface, out);
        outputData.setNbRes(classifier.nbOfClassifiers());
        outputData.fillClassesFromArray(classifier.classes());
        outputData.fillResFromArray(out);
    }
}
