package  fr.enst.pact34.whistlepro.api2.dataTypes;

import  fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 15/03/16.
 */
public class AttackTimes implements StreamDataInterface<AttackTimes> {
    @Override
    public void copyTo(AttackTimes attaqueTimes) {

        attaqueTimes.id=this.id;
        attaqueTimes.valid=this.valid;
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
}
