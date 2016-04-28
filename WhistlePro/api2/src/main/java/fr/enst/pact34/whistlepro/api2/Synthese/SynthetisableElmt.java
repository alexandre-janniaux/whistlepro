package fr.enst.pact34.whistlepro.api2.Synthese;

/**
 * Created by mms on 28/04/16.
 */
public abstract class SynthetisableElmt {

    private double startTime =0 ;

    private double endTime =0 ;

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public double getStartTime() {
        return startTime;
    }
}
