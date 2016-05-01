package fr.enst.pact34.whistlepro.api2.main;

import java.util.List;

/**
 * Created by mms on 25/04/16.
 */
public abstract class  Piste {
    

    public abstract TypePiste getTypePiste();
/*
    private String title;

    public final void setTitle(String title)
    {
        this.title=title;
    }

    public String getTitle() {
        return title;
    }
    */
    /*
    private int id =-1;

    public final void setId(int id) {
        this.id = id;
    }

    protected final int getId() {
        return id;
    }
    */
    private double totalTime = 0 ;

    public double getTotalTime()
    {
        return totalTime;
    }

    public void setTotalTime(double totTime) {
        this.totalTime=totTime;
    }

    public abstract String getSaveString();
 

    public enum TypePiste {
        Melodie,
        Percussions
    }
}
