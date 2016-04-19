package fr.enst.pact34.whistlepro.api.Synthese;

public class NotesDeMusique { //renvoie la frequence des notes jouees (????)
    /* Est-ce que l'on garde l'attribut instrument ?*/
	
	private double frequency ;
	private double intensity ;
	private int instrument ;

	public void setFrequency(double frequency) {
	this.frequency = frequency ;
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

    public int getInstrument() {
        return this.instrument ;
    } //0 for grosse caisse, 1 for caisse claire, 2 for charleston, 3 for cymbale

    public void setInstrument(int instrument) {
        this.instrument = instrument ;
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
