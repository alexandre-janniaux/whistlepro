package classifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Represent a weak classifier used in Adaboost algorithm which 
 * return true or false according to a threshold.
 * 4 values are stored : the threshold, the number of the feature 
 * concerned, the direction of the comparison and the error calculated.
 * An other value is stored, it is a coefficient calculated by Adaboost
 * algorithm. It will be required by the strong classifier.
 * 
 * @author  Mohamed
 *
 */

public class WeakClassifier {

	private double threshold;
	private int featureIndex;
	private boolean left;
	private double error;
	private double coef;
	
	/***
	 * Loads a weak classifier.
	 * @param datas is a string containing the data (see format in toString()).
	 */
	public WeakClassifier(String datas) {
		super();
		loadFromString(datas);
	}
	
	/***
	 * Loads a weak classifier.
	 * @param featureIndex is the number of the feature concerned.
	 * @param threshold is the threshold for the comparison.
	 * @param left is the direction of the comparison. 
	 * @param error obtained in training.
	 */
	public WeakClassifier( int featureIndex, double threshold,boolean left, double error) {
		super();
		this.threshold = threshold;
		this.featureIndex = featureIndex;
		this.left = left;
		this.error = error;
	}
	

	/***
	 * Loads a weak classifier.
	 * @param featureIndex is the number of the feature concerned.
	 * @param threshold is the threshold for the comparison.
	 * @param left is the direction of the comparison. 
	 * @param error obtained in training.
	 * @param coef is the coefficient to use by the strong classifier. 
	 * built by Adaboost algorithm.
	 */
	public WeakClassifier( int featureIndex, double threshold,boolean left, double error,double coef) {
		super();
		this.threshold = threshold;
		this.featureIndex = featureIndex;
		this.left = left;
		this.error = error;
		this.coef = coef;
	}
	
	/***
	 * @return a string containing the data to save them
	 * they formated as following : "feature=xx;threshold=xx;isLeft=xx;error=xx;coef=xx;" 
	 * where "xx" are the corresponding values.
	 */
	public String toString()
	{
		return new String(    "feature=" + featureIndex + ";"
							+ "threshold=" + threshold + ";"
							+ "isLeft=" + left + ";"
							+ "error=" + error + ";"
							+ "coef=" + coef + ";");
		
	}
	
	/***
	 * Changes data by the new ones in the string str
	 * @param str is the string containing the data
	 * and should be formatted as in toString() function.
	 */
	public void loadFromString(String str)
	{
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]*=[a-zA-Z0-9.-]*;");
		Matcher matcher = pattern.matcher(str);
		 
		while (matcher.find())
		{ 
			String tmp = matcher.group();
			tmp = tmp.replaceAll("<[a-zA-Z0-9]*>|</[a-zA-Z0-9]*>", "");
			String[] strs = tmp.split("=|;");
			switch(strs[0])
			{
			case "feature":
				featureIndex = (int) Double.parseDouble(strs[1]);
				break;
			case "threshold":
				threshold = Double.parseDouble(strs[1]);
				break;
			case "isLeft":
				left = Boolean.parseBoolean(strs[1]);
				break;
			case "error":
				error = Double.parseDouble(strs[1]);
				break;
			case "coef":
				coef = Double.parseDouble(strs[1]);
				break;
			} 
			
		}
	}
	
	public double getthreshold() {
		return threshold;
	}
	public void setthreshold(double threshold) {
		this.threshold = threshold;
	}
	public int getFeatureIndex() {
		return this.featureIndex;
	}
	public void setFeatureIndex(int featureIndex) {
		this.featureIndex = featureIndex;
	}
	public boolean isLeft() {
		return left;
	}
	public void setLeft(boolean left) {
		this.left = left;
	}
	public double getError() {
		return error;
	}
	public void setError(double error) {
		this.error = error;
	}

	public double getCoef() {
		return coef;
	}

	public void setCoef(double coef) {
		this.coef = coef;
	}
	
	/***
	 * Applies the comparison to sample
	 * @param sample is the element to test
	 * @return 1, if left is true, if the value is superior to the threshold,
	 * or if left is false, if the value is inferior to the threshold,
	 * otherwise it returns -1
	 */
	public int applyClassifier(FeatureProviderInterface sample)
	{
		double toTest = sample.getFeature(featureIndex);
		if(left==true)
		{
			if(threshold < toTest) return 1;
		}
		else
		{
			if(toTest < threshold) return 1;
		}
		return  -1;
	}
	
}
