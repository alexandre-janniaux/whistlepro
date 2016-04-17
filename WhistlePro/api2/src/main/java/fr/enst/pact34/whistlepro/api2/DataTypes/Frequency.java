package  fr.enst.pact34.whistlepro.api2.dataTypes;

import  fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 15/03/16.
 */
public class Frequency implements StreamDataInterface<Frequency> {

    @Override
    public void copyTo(Frequency o) {

        o.id = this.id;
    }

    @Override
    public Frequency getNew() {
        //TODO
        return null;
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
}
