package classification;

public interface TrainExampleInterface extends FeatureProviderInterface{

	//return 1 or -1
	public int isValid(); 
	public void setValid(boolean valid);
	
	public void setWeight(double w);
	public double getWeight();
}
