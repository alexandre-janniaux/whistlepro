package classification;

public interface ClassifierInterface<E> {
	
	public double classify(E sample);
	 
}
