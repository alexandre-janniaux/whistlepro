package  fr.enst.pact34.whistlepro.api2.dataTypes;

import  fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 15/03/16.
 */
public class Frequency implements StreamDataInterface<Frequency> {


    private double frequency;

    @Override
    public void copyTo(Frequency o) {

        o.id = this.id;
        o.valid=this.valid;
        o.frequency = this.frequency;
    }

    @Override
    public Frequency getNew() {
        //TODO
        return new Frequency();
    }

    int id = -1;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }


    boolean valid = true;

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public void setValid(boolean v) {
        valid =v;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getFrequency() {
        return frequency;
    }
}
