package classification;

import java.util.ArrayList;

public class ArrayClassifier {

	public static ArrayList<ArrayList<Double>> classify(
			MultipleClassifierInterface<FeatureProviderInterface> classifiers, 
			FeatureProviderInterface[] samples)
	{
		ArrayList<ArrayList<Double>> res = new ArrayList<>();
		
		for(int i = 0; i < samples.length; i ++)
		{
			res.add(i,classifiers.classify(samples[i]));
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
