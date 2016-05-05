package fr.enst.pact34.whistlepro.api2.Synthese;

/**
 * Created by mms on 28/04/16.
 */
public class Instru extends SynthetisableElmt {

    private double freq = 0.0;

    public double getFreq() {
        return  freq;
    }

    public void setFreq(double freq) {
        this.freq=freq;
    }

    private Type type;

    public void setType(Type type)
    {
        this.type = type;
    }

    public Type getType() {
        return  type;
    }

    public enum Type{
        Cuivre, Boise, Piano
        //TODO fill with percu names
    }

}
