package classification;

public class ClassifTrainExample implements TrainExampleInterface
{
	FeatureProviderInterface sample = null;
	int valid;
	double weight;
	
	public ClassifTrainExample(FeatureProviderInterface sample)
	{
		this.sample = sample;
	}
	
	//return 1 or -1
	@Override
	public int isValid()
	{
		return valid;
	}

	@Override
	public void setValid(boolean valid)
	{
		if(valid == true)
			this.valid = 1;
		else
			this.valid = -1;
	}

	@Override
	public double getFeature(int number) {
		if(sample != null) 
			return sample.getFeature(number);
		return 0;
	}

	@Override
	public int getFeatureNumber() {
		if(sample != null) 
			return sample.getFeatureNumber();
		return 0;
	}

	@Override
	public String getClasse() {
		if(sample != null) 
			return sample.getClasse();
		return "";
	}

	@Override
	public void setWeight(double w) {
		this.weight=w;
	}

	@Override
	public double getWeight() {
		return weight;
	}
	
	
	
}