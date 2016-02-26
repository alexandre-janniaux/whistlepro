package classification;

import java.util.ArrayList;

public class MultipleStrongClassifierLearner {

	public static MultipleStrongClassifier buildClassifiers(ArrayList<String> classes,
			ArrayList<FeatureProviderInterface> samples, int nbFeatures, int nbWeakClassifer)
	{
		
		ArrayList<TrainExampleInterface> examples = buildExampleList(samples);
		MultipleStrongClassifier.Builder msClassfier = new MultipleStrongClassifier.Builder();
		for(String s : classes)
		{
			setupExamplesForClass(s,examples);
			StrongClassifier sClassfier = StrongClassifierLearner.buildClassifier(examples, 
					nbFeatures, nbWeakClassifer);
			msClassfier.addClassifier(s, sClassfier);
		}
		
		return msClassfier.build();
	}
	
	private static ArrayList<TrainExampleInterface> buildExampleList(ArrayList<FeatureProviderInterface> samples)
	{
		ArrayList<TrainExampleInterface> tExamples= new ArrayList<TrainExampleInterface>() ;
		
		for(FeatureProviderInterface s : samples)
		{
			TrainExampleInterface t = new ClassifTrainExample(s);
			t.setValid(false);
			tExamples.add(t);
		}
		
		return tExamples;
	}
	
	private static void setupExamplesForClass(String classe, ArrayList<TrainExampleInterface> examples)
	{
		for(TrainExampleInterface ex : examples)
		{
			ex.setValid(ex.getClasse().equalsIgnoreCase(classe));
		}
	}	
		
		
}
