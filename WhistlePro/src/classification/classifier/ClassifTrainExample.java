package classifier;

public interface ClassifTrainExample extends FeatureProviderInterface
{

	//return 1 or -1
	int isValid();
	 
	//souhld be 1 or -1
	void setValid(int valid);
	
}