package fr.enst.pact34.whistlepro.api.Synthese;

public class NotesDeMusique { //renvoie la fr�quence des notes jou�es
	
	private double frequency ;
	private double intensity ;

	public void setFrequency(double frequency) {
	this.frequence = frequence ;
	}

	public void setIntensity(double intensity) {
	this.intensity = intensity ; 
	}

	public double getIntensity() {
	return this.intensity ; 
	}

	public double getFrequency(){
	return this.frequency ;
	}

	public static double do3() {
		return 261.63;
	}
	
	public static double re3() {
		return 293.66;
	}
	
	public static double mi3() {
		return 329.63;
	}
	
	public static double fa3() {
		return 349.23;
	}
	
	public static double sol3() {
		return 392.00;
	}
	
	public static double la3() {
		return 440;
	}
	
	public static double si3() {
		return 493.88;
	}

}
