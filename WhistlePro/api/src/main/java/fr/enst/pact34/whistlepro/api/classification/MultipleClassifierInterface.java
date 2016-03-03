package classification;

import java.util.ArrayList;

public interface MultipleClassifierInterface<E> {

	//classify et classes retournes les elements dans le meme ordre
	//classify()[i] correspond a la classe classes()[i]
	ArrayList<Double> classify(E sample);
	String classifyStr(E sample);
	ArrayList<String> classes();
	String toString(); // "<MultipleClassifierItem>" + classifier.toString() + "</MultipleClassifierItem>"

	int nbOfClassifiers();
	 
	
}
