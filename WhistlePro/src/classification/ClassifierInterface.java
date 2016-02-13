package classification;

public interface ClassifierInterface<E> {
	
	double classify(E sample);

}
