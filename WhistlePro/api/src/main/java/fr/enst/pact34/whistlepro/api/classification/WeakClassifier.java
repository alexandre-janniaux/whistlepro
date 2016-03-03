package fr.enst.pact34.whistlepro.api.classification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * Represent a weak classifier used in Adaboost algorithm which 
 * return true or false according to a threshold.
 * 4 values are stored : the threshold, the number of the feature 
 * concerned, the direction of the comparison.
 * An other value is stored, it is a coefficient calculated by Adaboost
 * algorithm. It will be required by the strong classifier.
 * 
 * @author  Mohamed
 *
 */

public class WeakClassifier implements ClassifierInterface<FeaturedObjectInterface>{

	private double threshold;
	private int featureIndex;
	private boolean left; 
	private double coef;
	   
	public WeakClassifier(Builder builder) {
		this.threshold = builder.threshold;
		this.featureIndex = builder.featureIndex;
		this.left = builder.left; 
		this.coef = builder.coef;
	}

	/***
	 * @return a string containing the data to save them
	 * they formated as following : "feature=xx;threshold=xx;isLeft=xx;coef=xx;"
	 * where "xx" are the corresponding values.
	 */
	public String toString()
	{
		return new String(    "feature=" + featureIndex + ";"
							+ "threshold=" + threshold + ";"
							+ "isLeft=" + left + ";" 
							+ "coef=" + coef + ";");
		
	}
	
	public double getthreshold() {
		return threshold;
	}

	public int getFeatureIndex() {
		return this.featureIndex;
	}

	public boolean isLeft() {
		return left;
	}  

	public double getCoef() {
		return coef;
	}
 
	
	/***
	 * Applies the comparison to sample
	 * @param sample is the element to test
	 * @return 1, if left is true, if the value is superior to the threshold,
	 * or if left is false, if the value is inferior to the threshold,
	 * otherwise it returns -1
	 */
	public double classify(FeaturedObjectInterface sample)
	{
		double toTest = sample.getFeature(featureIndex);
		if(left==true)
		{
			if(threshold <= toTest) return 1;
		}
		else
		{
			if(toTest <= threshold) return 1;
		}
		return  -1;
	}
	
	
	public static class Builder
	{
		private double threshold = 0;
		private int featureIndex = 0;
		private boolean left = false; 
		private double coef = 0;
		
		/*** 
		 * @return the new WeakClassifier built.
		 */
		public WeakClassifier build()
		{
			return new WeakClassifier(this);
		}
		 
		/***
		 * Changes data by the new ones in the string str
		 * @param str is the string containing the data
		 * and should be formatted as in toString() function.
		 */
		public Builder fromString(String str)
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
				case "coef":
					coef = Double.parseDouble(strs[1]);
					break;
				} 
				
			}
			return this;
		}

		public Builder setThreshold(double threshold) {
			this.threshold = threshold;
			return this;
		}

		public Builder setFeatureIndex(int featureIndex) {
			this.featureIndex = featureIndex;
			return this;
		}

		public Builder setLeft(boolean left) {
			this.left = left;
			return this;
		}

		public Builder setCoef(double coef) {
			this.coef = coef;
			return this;
		}
	}
	
}
