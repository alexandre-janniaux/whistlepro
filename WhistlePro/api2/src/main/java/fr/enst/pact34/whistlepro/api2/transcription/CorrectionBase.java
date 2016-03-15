package main.java.fr.enst.pact34.whistlepro.api2.transcription;

import main.java.fr.enst.pact34.whistlepro.api2.DataTypes.MusicTrack;
import main.java.fr.enst.pact34.whistlepro.api2.stream.StreamDestination;

/**
 * Created by mms on 15/03/16.
 */
public  class CorrectionBase extends StreamDestination<MusicTrack>
{

    MusicTrack buffer = null;

    public CorrectionBase() {
        super(new MusicTrack());
        buffer = getBufferToFill();
    }

    protected MusicTrack getMusikTrack() {return buffer;}

    @Override
    public void bufferFilled() {

    }
}
