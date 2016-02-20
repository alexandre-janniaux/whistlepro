package classification;

import java.util.ArrayList;


public class MultipleStrongClassifiersLearner {

	//retourne l'erreur moyenne
	// cree une partition des examples de n parties (n defini a 10)
	// construit un classifieur sur n-1 parties
	// calcul l'erreur sur la n-ième en pourcentage
	// renvoie la moyenne de ces erreurs.
	public static double buildAndValidate(ArrayList<String> classes,
		 ArrayList<FeatureProviderInterface> samples, int nbFeatures, int nbWeakClassifer)
	{
		//building partitions of samples
		int n = 10;
		ArrayList<ArrayList<FeatureProviderInterface>> groupes = makeTestParts(samples,n);

		double errors[] = new double[groupes.size()];
		//learn and verify
		for(int i = 0; i < groupes.size(); i++)
		{
			ArrayList<FeatureProviderInterface> partTest = groupes.get(i);
			ArrayList<FeatureProviderInterface> partsLearn = new ArrayList<>();
			for(int j = 0; j < groupes.size();j++)
			{
				if(j!=i) {
					partsLearn.addAll(groupes.get(j));
				}
			}
			//build
			MultipleStrongClassifiers classifier = buildClassifiers(classes,partsLearn,nbFeatures,nbWeakClassifer);

			int errorCounter = 0;
			//verfify
			for(int j = 0; j < partTest.size();j++)
			{
				FeatureProviderInterface tmp = partTest.get(j);
				String calcClasse = classifier.classifyStr(tmp);
				if(classifier.classifyStr(tmp).equals(tmp.getClasse())==false)
				{
					errorCounter++;
				}

			}
			errors[i] = (float)errorCounter/partTest.size();
			System.out.println( (float)errors[i]);
		}

		double mean=0;
		for(int i = 0; i < errors.length; i++) mean+= errors[i];
		mean= mean/errors.length;

		return mean;
	}

	private static ArrayList<ArrayList<FeatureProviderInterface>> makeTestParts(ArrayList<FeatureProviderInterface> samples, int n) {
		ArrayList<ArrayList<FeatureProviderInterface>> parts = new ArrayList<>();

		int nbExamplesByPart = Math.round(samples.size()/n);
		ArrayList<FeatureProviderInterface> sampleList = (ArrayList<FeatureProviderInterface>) samples.clone();

		for(int i = 0; i < n-1; i++)
		{
			ArrayList<FeatureProviderInterface> part = new ArrayList<>();
			for(int j=0; j < nbExamplesByPart; j++)
			{
				int index = (int) Math.floor(Math.random()*(sampleList.size()-0.001));
				part.add(sampleList.remove(index));
			}
			parts.add(part);
		}

		parts.add(sampleList);

		return parts;
	}

	public static MultipleStrongClassifiers buildClassifiers(ArrayList<String> classes,
			ArrayList<FeatureProviderInterface> samples, int nbFeatures, int nbWeakClassifer)
	{
		
		ArrayList<TrainExampleInterface> examples = buildExampleList(samples);
		MultipleStrongClassifiers.Builder msClassfier = new MultipleStrongClassifiers.Builder();
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
