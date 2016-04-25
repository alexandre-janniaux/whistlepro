package fr.enst.pact34.whistlepro.api2.main;

/**
 * Created by mms on 25/04/16.
 */
public abstract class  Piste {
    

    public abstract TypePiste getTypePiste();

    private String title;

    public final void setTitle(String title)
    {
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    private int id =-1;

    public final void setId(int id) {
        this.id = id;
    }

    protected final int getId() {
        return id;
    }

}
