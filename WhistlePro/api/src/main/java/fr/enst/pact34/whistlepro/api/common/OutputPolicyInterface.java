package fr.enst.pact34.whistlepro.api.common;


public interface OutputPolicyInterface<F> {

    F getOutput(OutputRequestInterface request);

}
