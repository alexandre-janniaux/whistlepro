package  fr.enst.pact34.whistlepro.api2.transcription;

import fr.enst.pact34.whistlepro.api2.dataTypes.MusicTrack;
import fr.enst.pact34.whistlepro.api2.stream.StreamDestBase;

/**
 * Created by mms on 15/03/16.
 */
public abstract class CorrectionBase extends StreamDestBase<MusicTrack> {


    public CorrectionBase(MusicTrack bufferIn) {
        super(bufferIn);
    }
}
