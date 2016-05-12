package fr.enst.pact34.whistlepro.api2.Synthese;

/**
 * Created by mms on 28/04/16.
 */
public class Percu extends SynthetisableElmt {

    private Type type;

    public void setType(Type type)
    {
        this.type = type;
    }

    public Type getType() {
        return  type;
    }

    public enum Type{
        Kick,
        CaisseClaire,
        Charleston
        //TODO fill with percu names
    }
}
