package fr.enst.pact34.whistlepro.api.common;


/*
 * Defines a computing module in the application
 * @param E the input type
 * @param F the output type 
 */
public interface StreamProcessInterface<E, F> {

    /*
     * Computing function
     * @param inputData the inputData of type E
     * @param outputData the outputData of type F
     */
    F process(E inputData, OutputPolicyInterface<F> outputData);
}
