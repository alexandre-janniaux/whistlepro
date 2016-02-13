package classification;

import java.util.Enumeration;
import java.util.Hashtable;

public class MultipleStrongClassifier {
	private Hashtable<String,StrongClassifier> data;
	
	public MultipleStrongClassifier(Builder builder) {
		this.data = builder.data;
	}
	
	public String toString()
	{
		String str = "";
		 
		for(Enumeration<String> classes = data.keys(); classes.hasMoreElements(); )
		{
			String classe = classes.nextElement();
			
			str += "<StongClassifier class='"+classe+"' >\n";
			str += data.get(classe).toString()+"\n";
			str += "</StongClassifier>\n";
		}
		
		return str;
	}

	public static class Builder
	{
		private Hashtable<String,StrongClassifier> data = new Hashtable<String,StrongClassifier>();
		
		public MultipleStrongClassifier build()
		{
			return new MultipleStrongClassifier(this);
		}
		
		public Builder addClassifier(String classe, StrongClassifier sc)
		{
			data.put(classe, sc);
			
			return this;
		}
	}
}
