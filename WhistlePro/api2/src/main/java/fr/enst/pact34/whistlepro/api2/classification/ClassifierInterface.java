package fr.enst.pact34.whistlepro.api2.classification;

public interface ClassifierInterface<E> {
	
	public double classify(E sample);
	 
}
