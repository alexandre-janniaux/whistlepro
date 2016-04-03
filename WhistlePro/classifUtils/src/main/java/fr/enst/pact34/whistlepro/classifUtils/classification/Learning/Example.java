package fr.enst.pact34.whistlepro.classifUtils.classification.Learning;

import fr.enst.pact34.whistlepro.api.classification.TrainExampleInterface;

import java.util.ArrayList;

public class Example implements TrainExampleInterface {

	String classe = "";
	ArrayList<Double> dbl = new ArrayList<>();
	 	
	public Example(Builder builder) {
		classe = builder.classe;
		dbl = builder.dbl;
	}


	@Override
	public String getClasse() {

		return classe ;
	}

	@Override
	public double getFeature(int number) {

		return dbl.get(number);
	}

	@Override
	public int countFeatures() {

		return dbl.size();
	}

	public static class Builder
	{

		private String classe = "";
		private ArrayList<Double> dbl = new ArrayList<Double>();
		
		public Builder fromString(String str)
		{
			String[] strs = str.split(";");

			if(strs.length!= 14) return this;

			dbl.clear();
			for(int i = 0; i < 13; i++)
			{
				dbl.add(i, new Double(strs[i]));
			}
			
			classe = strs[13];
			
			return this;
		}
		
		public Example build()
		{
			if(classe.isEmpty() || dbl.size()!=13 ) return null;

			return new Example(this);
		}


	}
}
