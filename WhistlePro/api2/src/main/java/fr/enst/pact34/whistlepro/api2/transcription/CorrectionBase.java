package main.java.fr.enst.pact34.whistlepro.api2.transcription;

import main.java.fr.enst.pact34.whistlepro.api2.dataTypes.MusicTrack;
import main.java.fr.enst.pact34.whistlepro.api2.stream.DataListenerInterface;

/**
 * Created by mms on 15/03/16.
 */
public abstract class CorrectionBase implements DataListenerInterface<MusicTrack> {

    MusicTrack bufferIn = new MusicTrack();

    @Override
    public MusicTrack getBufferToFill() {
        return bufferIn;
    }
}
