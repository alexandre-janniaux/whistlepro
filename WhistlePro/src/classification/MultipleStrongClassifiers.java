package classification;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern; 

public class MultipleStrongClassifiers implements MultipleClassifierInterface<FeatureProviderInterface>{

	private StrongClassifierItem[] classifiersList = null;
	
	private static class StrongClassifierItem
	{
		private String classe;
		private StrongClassifier sc;
		
		public StrongClassifierItem(String classe, StrongClassifier sc) {
			super();
			this.classe = classe;
			this.sc = sc;
		}
		
	}
	
	public MultipleStrongClassifiers(Builder builder) {
		
		this.classifiersList = new StrongClassifierItem[builder.classifiersList.size()];
		
		for(int i = 0; i < builder.classifiersList.size(); i++)
		{
			this.classifiersList[i] = builder.classifiersList.get(i);
		}
	}
	
	public String toString()
	{ 
		String str = "";
		 
		for(int i = 0; i < classifiersList.length; i ++)
		{  
			str += "<MultipleClassifierItem classe='"+classifiersList[i].classe+"' >\n";
			str += classifiersList[i].sc.toString() ;
			str += "</MultipleClassifierItem>\n";
		}
		
		return str;
	}

 
	
	public static class Builder
	{
		private ArrayList<StrongClassifierItem> classifiersList = new ArrayList<StrongClassifierItem>();
		
		public MultipleStrongClassifiers build()
		{
			return new MultipleStrongClassifiers(this);
		}
		
		public Builder addClassifier(String classe, StrongClassifier sc)
		{
			classifiersList.add(new StrongClassifierItem(classe,sc));
			
			return this;
		}
		
		public Builder fromString(String data)
		{ 
			Pattern pattern = Pattern.compile("<MultipleClassifierItem [ ]*classe[ ]*=[ ]*'[a-zA-Z0-9]*'[ ]*>.*?</MultipleClassifierItem>", Pattern.DOTALL);
			Matcher matcher = pattern.matcher(data);
			
			Pattern patternClasse = Pattern.compile("classe[ ]*=[ ]*'[a-zA-Z0-9]*'");
					
			while (matcher.find())
			{ 

				String tmp = matcher.group();
				
				Matcher matcherClasse = patternClasse.matcher(tmp);
				
				while (matcherClasse.find())
				{

					String found = matcherClasse.group();
					System.out.println(found);
					String[] strs = found.split("[ ]*=[ ]*'|'");

					if(strs[0].equals("classe") && strs.length == 2)
					{
						//System.out.println("classe = "+strs[1]);
						tmp = tmp.replaceAll("<MultipleClassifierItem[a-zA-Z0-9=\' .-]*>|</MultipleClassifierItem>", "");  
						//System.out.println("data = "+tmp);
						classifiersList.add( new StrongClassifierItem(strs[1],
								new StrongClassifier.Builder().fromString(tmp).build()));
					}
				}
			}
			return this;
			
		}
		 
	}

	@Override
	public double[] classify(FeatureProviderInterface sample) {
		double[] rets= new double[classifiersList.length];
		
		for(int i = 0; i < classifiersList.length; i ++)
		{  
			rets[i] = classifiersList[i].sc.classify(sample);
		}
		
		return rets;
	}

	@Override
	public String classifyStr(FeatureProviderInterface sample)
	{
		double max = -Double.MAX_VALUE;
		String reco = null;
		for(int i = 0; i < classifiersList.length; i ++)
		{
			double res = classifiersList[i].sc.classify(sample);
			if(res > max)
			{
				max = res;
				reco = classifiersList[i].classe;
			}
		}
		return reco;
	}

	@Override
	public int nbOfClassifiers() { 
		return classifiersList.length;
	}

	@Override
	public String[] classes() {
		String[] rets= new String[classifiersList.length];
		
		for(int i = 0; i < classifiersList.length; i ++)
		{  
			rets[i] = classifiersList[i].classe;
		}
		
		return rets;
	}
 
}
 