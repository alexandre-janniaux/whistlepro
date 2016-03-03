package fr.enst.pact34.whistlepro.api.classification;

public interface ClassifierInterface<E> {
	
	public double classify(E sample);
	 
}
