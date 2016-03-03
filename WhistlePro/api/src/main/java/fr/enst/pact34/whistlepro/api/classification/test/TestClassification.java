package fr.enst.pact34.whistlepro.api.classification.test;
 
import fr.enst.pact34.whistlepro.api.common.FileOperator;
import fr.enst.pact34.whistlepro.api.classification.MultipleStrongClassifiers;

import java.util.ArrayList;

public class TestClassification {

	static String ExampleData = "1.193907,2.819734,3.429199,-0.952259,-0.898797,0.949669,1.153581,0.286485,0.415623,2.310377,-1.462632,-0.619475,0.661276,0.952590,0.416698,2.079119,2.887582,2.968235,0.310646,-0.172956,Vl_8.wav,violin";

	public static void main(String[] args) {
		
		//loading the classifier
		MultipleStrongClassifiers classifiers = 
				new MultipleStrongClassifiers.Builder()
				.fromString(FileOperator.getDataFromFile(TestClassifierLearning.classifierSavefileName))
				.build();
		

		FileOperator.saveToFile(TestClassifierLearning.classifierSavefileName+"2", classifiers.toString());
		
		//Testing a sample
		Example sample = new Example.Builder().fromString(ExampleData).build();
		
		ArrayList<Double> vals = classifiers.classify(sample);
		System.out.print("Resultat : ");
		for(int i = 0; i < vals.size() ; i ++)
		{
			System.out.print(vals.get(i)+" / ");
		}
		System.out.println(".");
		
	}
	
}
