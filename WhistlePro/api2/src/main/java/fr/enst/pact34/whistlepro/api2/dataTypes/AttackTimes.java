package  fr.enst.pact34.whistlepro.api2.dataTypes;

import java.util.LinkedList;
import java.util.List;

import  fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 15/03/16.
 */
public class AttackTimes implements StreamDataInterface<AttackTimes> {

    @Override
    public void copyTo(AttackTimes attaqueTimes) {

        attaqueTimes.id=this.id;
        attaqueTimes.valid=this.valid;
        //TODO remove NEW HERE
        attaqueTimes.timesUp = new LinkedList<>(this.timesUp);
        attaqueTimes.timesDown = new LinkedList<>(this.timesDown);
    }

    @Override
    public AttackTimes getNew() {
        //TODO
        return new AttackTimes();
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


    List<Double> timesUp = new LinkedList<>();
    List<Double> timesDown  = new LinkedList<>();

    public void addUp(double v) {
        timesUp.add(v);
    }

    public void addDown(double v) {
        timesDown.add(v);
    }

    public List<Double> getUpTimes() {
        return timesUp;
    }

    public List<Double> getDownTimes() {
        return timesDown;
    }

    public void clear() {
        timesDown.clear();
        timesUp.clear();
    }
}
