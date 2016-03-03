package classification.Learning;

import classification.StrongClassifier;
import classification.WeakClassifier;

import java.util.ArrayList;

public class StrongClassifierLearner{

	/***
	 * Method that builds the strong classifier by computing the weak classifiers
	 * following the Adaboost algorithm on the examples given.
	 * @param examples used to train the classifier.
	 * @param nbFeatures is the number of features of an example.
	 * @param nbWeakClassifer is the number of weak classifier to build.
	 * @return a StrongClassifier objet containning the datas
	 */
	public static StrongClassifier buildClassifier(ArrayList<TrainObjectInterface> examples, int nbFeatures, int nbWeakClassifer)
	{
 
		StrongClassifier.Builder builder = new StrongClassifier.Builder();
		 
		int examplesNb = examples.size();
		double defWeight = 1.0/examplesNb;
		
		for(TrainObjectInterface ex : examples)
		{
			ex.setWeight(defWeight);
		}
		 

		for(int r = 0; r < nbWeakClassifer; r++)
		{ 
			WeakClassifier.Builder classifierBuilder = new WeakClassifier.Builder();
			double epsilon = trainWeakClassifier(classifierBuilder, examples,nbFeatures);
			
			double alpha = Math.log((1-epsilon)/epsilon)/2;
			classifierBuilder.setCoef(alpha);
			
			WeakClassifier classifier = classifierBuilder.build();
			builder.add(classifier);  
			
			for(TrainObjectInterface ex : examples)
			{  
				ex.setWeight(
						ex.getWeight()*Math.exp(-alpha*ex.isValid()*classifier.classify(ex))
							/(2*Math.sqrt(epsilon*(1-epsilon)))
							);
				 
			}
			 
		}
		  
		return builder.build();
	}
	
	/***
	 * Builds a weak classifier using the examples associated with their weights.
	 * @param examples used to train the classifier.
	 * @param nbFeatures is the number of features of an example.
	 * @return a weak classifier.
	 */
	private static double trainWeakClassifier(WeakClassifier.Builder classifierBuilder, ArrayList<TrainObjectInterface> examples,int nbFeatures)
	{ 
		int nbExamples = examples.size();
		int minPivotIndex = -1;
		int minFeatureIndex = -1;
		boolean minIsLeft = false;
		double minError = Double.MAX_VALUE,errLeft,errRight;
		double x_ij,x_kj,y_i,w;
		
		for(int k = 0 ; k < nbExamples; k++)
		{
			for(int j = 0 ; j < nbFeatures; j++)
			{ 
				errLeft = 0;
				errRight = 0;
				
				for(int i = 0 ; i < nbExamples; i++)
				{
					x_ij = examples.get(i).getFeature(j);
					x_kj = examples.get(k).getFeature(j);
					y_i = examples.get(i).isValid();
					w = examples.get(i).getWeight();

					if( (x_ij > x_kj && y_i == -1) 
							|| (x_ij < x_kj && y_i == 1) )
					{
						errLeft = errLeft + w;
						 
					}
					
					if( (x_ij > x_kj && y_i == 1) 
							|| (x_ij < x_kj && y_i == -1) )
					{
						errRight = errRight + w;
						 
					}
					
				}
 
				
				if(errLeft < minError)
				{
					minPivotIndex = k;
					minFeatureIndex = j;
					minIsLeft = true;
					minError = errLeft;
				}

				if(errRight < minError)
				{
					minPivotIndex = k;
					minFeatureIndex = j;
					minIsLeft = false;
					minError = errRight;
				}
			}
		}
		
		classifierBuilder.setFeatureIndex(minFeatureIndex)
							.setThreshold(examples.get(minPivotIndex).getFeature(minFeatureIndex))
							.setLeft(minIsLeft);
		
		return minError;
		
	}
}
