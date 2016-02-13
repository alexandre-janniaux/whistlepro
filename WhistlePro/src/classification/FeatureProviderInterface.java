package classification;

public interface FeatureProviderInterface {

	public String getClasse();
	
	public double getFeature(int number);
	
	public int getFeatureNumber();
}
