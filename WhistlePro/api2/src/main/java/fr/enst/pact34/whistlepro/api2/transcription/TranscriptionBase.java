package  fr.enst.pact34.whistlepro.api2.transcription;

import  fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import  fr.enst.pact34.whistlepro.api2.dataTypes.ClassifResults;
import  fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import  fr.enst.pact34.whistlepro.api2.dataTypes.MusicTrack;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeStreamDest;
import fr.enst.pact34.whistlepro.api2.stream.StreamDestBase;
import  fr.enst.pact34.whistlepro.api2.stream.StreamSourceBase;

/**
 * Created by mms on 15/03/16.
 */

public abstract class TranscriptionBase extends StreamSourceBase<MusicTrack>
{
    StreamDestBase<Frequency> destFreqs = new FakeStreamDest<>(new Frequency());
    StreamDestBase<AttackTimes> destAttak = new FakeStreamDest<>(new AttackTimes());
    StreamDestBase<ClassifResults> destClassif = new FakeStreamDest<>(new ClassifResults());

    public TranscriptionBase(MusicTrack bufferOut) {
        super(bufferOut);
    }


    public StreamDestBase<Frequency> getStreamDestBaseFreqs() { return destFreqs;}

    public StreamDestBase<AttackTimes> getStreamDestBaseAttak() { return destAttak;}

    public StreamDestBase<ClassifResults> getStreamDestBaseClassif() { return destClassif;}
}
