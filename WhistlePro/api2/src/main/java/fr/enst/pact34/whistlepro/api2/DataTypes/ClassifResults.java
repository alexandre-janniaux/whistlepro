package  fr.enst.pact34.whistlepro.api2.dataTypes;

import  fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 15/03/16.
 */
public class ClassifResults implements StreamDataInterface<ClassifResults> {
    @Override
    public void copyTo(ClassifResults classifResults) {

        classifResults.id=this.id;
    }

    @Override
    public ClassifResults getNew() {
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
