package classification; 
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/***
 * Represents a strong classifier built with Adaboost.
 * It contains a list of weak classifiers and the 
 * methods to build them.
 * @author  Mohamed
 *
 */
public class StrongClassifier implements ClassifierInterface<FeatureProviderInterface>{
	 
	
	private ArrayList<WeakClassifier> classifiers = null;
	
	public StrongClassifier(Builder builder)
	{ 
		classifiers = builder.classifiers;
	}
	
	/***
	 * @return a string which contains the data of 
	 * all the weak classifiers following this format :
	 * the first line is "X classifiers" where x is the 
	 * number of classifiers, the following lines contains 
	 * "<weakClassifier>XX</weakClassifier>" where XX 
	 * is the data of the weak classifier.
	 */
	public String toString()
	{
		String res = "";
		res += classifiers.size()+" classifiers\n";
		for(WeakClassifier c : classifiers)
		{
			res = res + "<weakClassifier>" + c.toString() + "</weakClassifier>\n";
		}
		return res;
	}
	
	
	/***
	 * Applies the classifier to the sample.
	 * @param sample to test.
	 * @return -1 if it is rejected by the classifier,
	 * otherwise returns 1.
	 */
	public double classify(FeatureProviderInterface sample)
	{
		double res = 0;
		for(WeakClassifier c : classifiers)
		{
			res = res + c.classify(sample)*c.getCoef();
		}
		
		return res;
	} 
		
	public static class Builder
	{
		private ArrayList<WeakClassifier> classifiers = new ArrayList<WeakClassifier>();
   
		/***
		 * Parse the data in a string to fill a list of weak classifiers.
		 * @param str is the string containing the data.
		 */
		public  Builder fromString(String str)
		{
			ArrayList<WeakClassifier> classifiers = new ArrayList<WeakClassifier>();
			
			Pattern pattern = Pattern.compile("<weakClassifier>.*?</weakClassifier>", Pattern.DOTALL);
			Matcher matcher = pattern.matcher(str);
			
			while (matcher.find())
			{ 
				String tmp = matcher.group();
				tmp = tmp.replaceAll("<weakClassifier>|</weakClassifier>", "");  
				classifiers.add(new WeakClassifier.Builder().fromString(tmp).build());
			}
			
			this.classifiers  = classifiers;
			
			return this;
		}
  
		public  Builder add(WeakClassifier wc)
		{
			 classifiers.add(wc);
			
			return this;
		}
		
		public StrongClassifier build()
		{
			return new StrongClassifier(this);
		}
		
	}
	
}
