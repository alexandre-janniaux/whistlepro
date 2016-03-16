package  fr.enst.pact34.whistlepro.api2.transcription;

import  fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import  fr.enst.pact34.whistlepro.api2.dataTypes.ClassifResults;
import  fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import  fr.enst.pact34.whistlepro.api2.dataTypes.MusicTrack;
import  fr.enst.pact34.whistlepro.api2.phantoms.FakeStreamDest;
import  fr.enst.pact34.whistlepro.api2.stream.StreamDest;
import  fr.enst.pact34.whistlepro.api2.stream.StreamSourceBase;

/**
 * Created by mms on 15/03/16.
 */

public abstract class TranscriptionBase extends StreamSourceBase<MusicTrack>
{
    StreamDest<Frequency> destFreqs = new FakeStreamDest<>(new Frequency());
    StreamDest<AttackTimes> destAttak = new FakeStreamDest<>(new AttackTimes());
    StreamDest<ClassifResults> destClassif = new FakeStreamDest<>(new ClassifResults());


    public StreamDest<Frequency> getStreamDestFreqs() { return destFreqs;}

    public StreamDest<AttackTimes> getStreamDestAttak() { return destAttak;}

    public StreamDest<ClassifResults> getStreamDestClassif() { return destClassif;}
}
