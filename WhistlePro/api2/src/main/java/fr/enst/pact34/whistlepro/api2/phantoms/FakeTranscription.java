package  fr.enst.pact34.whistlepro.api2.phantoms;

import  fr.enst.pact34.whistlepro.api2.dataTypes.MusicTrack;
import  fr.enst.pact34.whistlepro.api2.transcription.TranscriptionBase;

/**
 * Created by mms on 15/03/16.
 */
public class FakeTranscription extends TranscriptionBase {

    public FakeTranscription(MusicTrack bufferOut) {
        super(bufferOut);
    }
}
