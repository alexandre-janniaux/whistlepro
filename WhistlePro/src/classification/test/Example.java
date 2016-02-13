package classification.test;

import java.util.ArrayList;

import classification.FeatureProviderInterface;

public class Example implements FeatureProviderInterface {

	String classe = "";
	ArrayList<Double> dbl = new ArrayList<Double>();
	 	
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
	public int getFeatureNumber() {

		return dbl.size();
	}

	public static class Builder
	{

		private String classe = "";
		private ArrayList<Double> dbl = new ArrayList<Double>();
		
		public Builder fromString(String str)
		{
			String[] strs = str.split(",");
			
			dbl.clear();
			for(int i = 0; i < 20 && i < strs.length; i++)
			{
				dbl.add(i, new Double(strs[i]));
			}
			
			if(strs.length <= 22)
				classe = strs[21];
			
			return this;
		}
		
		public Example build()
		{
			return new Example(this);
		}
		
		public boolean isValid()
		{
			if(dbl.size()==20) return true;
				
			return false;
		}
	}
}
