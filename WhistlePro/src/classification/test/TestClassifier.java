package classification.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import classification.ClassifTrainExample;
import classification.FeatureProviderInterface;
import classification.FileOperator;
import classification.MultipleStrongClassifier;
import classification.MultipleStrongClassifierLearner;
import classification.StrongClassifier;
import classification.StrongClassifierLearner;
import classification.TrainExampleInterface;
 

public class TestClassifier {

	public static void main(String[] args) {
		
		//reading file
		ArrayList<String> strs = FileOperator.getLinesFromFile("src/classification/test/instrument_features.csv");
				
		//creating samples
		ArrayList<FeatureProviderInterface> samples = new ArrayList<FeatureProviderInterface>();
		ArrayList<String> classes = new ArrayList<String>();

		for(String s : strs)
		{	 
			
			int i = 0;
			Example.Builder exB = new Example.Builder().fromString(s) ;
			if(exB.isValid() == false)
				continue;
				
			Example ex = exB.build();
			samples.add(ex);
			
			for(i = 0; i < classes.size(); i++)
			{
				if(classes.get(i).equalsIgnoreCase(ex.getClasse())==true) break;
			}
			
			if(i == classes.size())
			{ 
				classes.add(ex.getClasse());
			}
		}
		 
		System.out.println("Liste des classes : ");
		for(String s : classes)
		{
			System.out.println(s);
		}
		
		System.out.println("Nombre d'échantillons : "+ samples.size());
		 
		MultipleStrongClassifier classifiers = MultipleStrongClassifierLearner.buildClassifiers(classes, samples, 20, 10);
		
		FileOperator.saveToFile("src/classification/test/instrument_features.scs", classifiers.toString());
		 
	}
	

}
