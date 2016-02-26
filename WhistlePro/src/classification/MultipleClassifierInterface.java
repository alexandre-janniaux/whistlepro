package classification;

public interface MultipleClassifierInterface<E> {

	//classify et classes retournes les elements dans le meme ordre
	//classify()[i] correspond a la classe classes()[i]
	public double[] classify(E sample);
	public String classifyStr(E sample);
	public String[] classes();
	public String toString(); // "<MultipleClassifierItem>" + classifier.toString() + "</MultipleClassifierItem>"
	
	public int nbOfClassifiers();
	 
	
}
