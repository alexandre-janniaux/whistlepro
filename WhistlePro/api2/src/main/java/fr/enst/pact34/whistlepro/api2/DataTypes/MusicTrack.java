package  fr.enst.pact34.whistlepro.api2.dataTypes;

import  fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 15/03/16.
 */
public class MusicTrack implements StreamDataInterface<MusicTrack> {
    @Override
    public void copyTo(MusicTrack musicTrack) {

        musicTrack.id=this.id;
    }

    @Override
    public MusicTrack getNew() {
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
