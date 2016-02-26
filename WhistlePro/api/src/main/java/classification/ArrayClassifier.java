package classification;

public class ArrayClassifier {

	public static double[][] classify(
			MultipleClassifierInterface<FeatureProviderInterface> classifiers, 
			FeatureProviderInterface[] samples)
	{
		double[][] res = new double[samples.length][classifiers.nbOfClassifiers()];
		
		for(int i = 0; i < samples.length; i ++)
		{
			res[i] = classifiers.classify(samples[i]);
		}
		
 		return res; 
	}

	public static String[] classifyStr(
			MultipleClassifierInterface<FeatureProviderInterface> classifiers,
			FeatureProviderInterface[] samples)
	{
		String[] res = new String[samples.length];

		for(int i = 0; i < samples.length; i ++)
		{
			res[i] = classifiers.classifyStr(samples[i]);
		}

		return res;
	}
}
