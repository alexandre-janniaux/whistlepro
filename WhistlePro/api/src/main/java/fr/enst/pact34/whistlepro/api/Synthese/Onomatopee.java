package fr.enst.pact34.whistlepro.api.Synthese;

/**
 En entrée de la synthèse, on a un tableau du type défini ici. Voilà.
 Les attributs sont l'intenstité et l'instrument.
 */
public class Onomatopee
{
    private double intensity ;
    private int instrument ;

    public void setIntensity(double intensity) {
        this.intensity = intensity ;
    }

    public double getIntensity() {
        return this.intensity ;
    }

    public int getInstrument() {
        return this.instrument ;
    } //0 for nothing, 1 for caisse claire, 2 for charleston, 3 for cymbale, 4 for grosse caisse

    public void setInstrument(int instrument) {
        this.instrument = instrument;
    }
}
