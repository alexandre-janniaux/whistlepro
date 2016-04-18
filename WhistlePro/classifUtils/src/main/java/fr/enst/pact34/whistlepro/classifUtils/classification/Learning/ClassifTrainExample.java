package fr.enst.pact34.whistlepro.classifUtils.classification.Learning;

import fr.enst.pact34.whistlepro.api.classification.TrainExampleInterface;

public class ClassifTrainExample implements TrainObjectInterface
{
	TrainExampleInterface sample = null;
	int valid;
	double weight;
	
	public ClassifTrainExample(TrainExampleInterface sample)
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
	public int countFeatures() {
		if(sample != null) 
			return sample.countFeatures();
		return 0;
	}

	@Override
	public String getClasse() {
		return sample.getClasse();
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