package fr.enst.pact34.whistlepro.api.classification.Learning;

import fr.enst.pact34.whistlepro.api.classification.*;

import java.util.ArrayList;


public class MultipleStrongClassifiersLearner {


	//retourne l'erreur moyenne
	// cree une partition des examples de n parties (n defini a 10)
	// construit un classifieur sur n-1 parties
	// calcul l'erreur sur la n-ième en pourcentage
	// renvoie la moyenne de ces erreurs.
	public static double buildAndValidate(ArrayList<String> classes,
										  ArrayList<TrainExampleInterface> samples, int nbFeatures, int nbWeakClassifer)
	{
		//building partitions of samples
		int n = 10;
		ArrayList<ArrayList<TrainExampleInterface>> groupes = makeTestParts(samples,n);

		double errors[] = new double[groupes.size()];
		//learn and verify
		for(int i = 0; i < groupes.size(); i++)
		{
			ArrayList<TrainExampleInterface> partTest = groupes.get(i);
			ArrayList<TrainExampleInterface> partsLearn = new ArrayList<>();
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
				TrainExampleInterface tmp = partTest.get(j);
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

	private static ArrayList<ArrayList<TrainExampleInterface>> makeTestParts(ArrayList<TrainExampleInterface> samples, int n) {
		ArrayList<ArrayList<TrainExampleInterface>> parts = new ArrayList<>();

		int nbExamplesByPart = Math.round(samples.size()/n);
		ArrayList<TrainExampleInterface> sampleList = new ArrayList<TrainExampleInterface>(samples);

		for(int i = 0; i < n-1; i++)
		{
			ArrayList<TrainExampleInterface> part = new ArrayList<>();
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
			ArrayList<TrainExampleInterface> samples, int nbFeatures, int nbWeakClassifer)
	{
		
		ArrayList<TrainObjectInterface> examples = buildExampleList(samples);
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
	
	private static ArrayList<TrainObjectInterface> buildExampleList(ArrayList<TrainExampleInterface> samples)
	{
		ArrayList<TrainObjectInterface> tExamples= new ArrayList<>() ;
		
		for(TrainExampleInterface s : samples)
		{
			TrainObjectInterface t = new ClassifTrainExample(s);
			t.setValid(false);
			tExamples.add(t);
		}
		
		return tExamples;
	}
	
	private static void setupExamplesForClass(String classe, ArrayList<TrainObjectInterface> examples)
	{
		for(TrainObjectInterface ex : examples)
		{
			ex.setValid(ex.getClasse().equalsIgnoreCase(classe));
		}
	}	
		
		
}
