package classification.test;

import java.util.ArrayList;

import classification.*;
import classification.Learning.MultipleStrongClassifiersLearner;
import common.FileOperator;

public class TestClassifierLearning {

	protected static int NB_CLASSIFIERS_TO_CREATE = 500;
	protected static int NB_FEATURES= 20;
	protected static String datafileName = "data/instrument_features.csv";
	protected static String classifierSavefileName = "data/instrument_features.scs";
	
	public static void main(String[] args) {
		System.out.println("Start");
		//reading file
		ArrayList<String> strs = FileOperator.getLinesFromFile(datafileName);
				
		//creating samples
		ArrayList<TrainExampleInterface> samples = new ArrayList<>();
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
		 
		MultipleStrongClassifiers classifiers = MultipleStrongClassifiersLearner.buildClassifiers(classes, samples, NB_FEATURES, NB_CLASSIFIERS_TO_CREATE);
		
		FileOperator.saveToFile(classifierSavefileName, classifiers.toString());
		
		System.out.println("Done.");
		  
	}
	

}
