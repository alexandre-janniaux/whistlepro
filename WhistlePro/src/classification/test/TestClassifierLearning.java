package classification.test;

import java.util.ArrayList;

import classification.FeatureProviderInterface;
import classification.FileOperator;
import classification.MultipleStrongClassifiers;
import classification.MultipleStrongClassifiersLearner;

public class TestClassifierLearning {

	protected static int NB_CLASSIFIERS_TO_CREATE = 100;
	protected static int NB_FEATURES= 20;
	protected static String datafileName = "src/classification/test/instrument_features.csv";
	protected static String classifierSavefileName = "src/classification/test/instrument_features.scs";
	
	public static void main(String[] args) {
		
		//reading file
		ArrayList<String> strs = FileOperator.getLinesFromFile(datafileName);
				
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
		 
		MultipleStrongClassifiers classifiers = MultipleStrongClassifiersLearner.buildClassifiers(classes, samples, NB_FEATURES, NB_CLASSIFIERS_TO_CREATE);
		
		FileOperator.saveToFile(classifierSavefileName, classifiers.toString());
		
		System.out.println("Done.");
		  
	}
	

}
