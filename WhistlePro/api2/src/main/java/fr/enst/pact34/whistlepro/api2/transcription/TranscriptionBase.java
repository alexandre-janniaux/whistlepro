package  fr.enst.pact34.whistlepro.api2.transcription;

import  fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import  fr.enst.pact34.whistlepro.api2.dataTypes.ClassifResults;
import  fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import  fr.enst.pact34.whistlepro.api2.dataTypes.MusicTrack;
import  fr.enst.pact34.whistlepro.api2.phantoms.FakeStreamDest;
import  fr.enst.pact34.whistlepro.api2.stream.DataListenerInterface;
import  fr.enst.pact34.whistlepro.api2.stream.StreamSource;

/**
 * Created by mms on 15/03/16.
 */

public abstract class TranscriptionBase extends StreamSource<MusicTrack>
{
    DataListenerInterface<Frequency> destFreqs = new FakeStreamDest<>(new Frequency());
    DataListenerInterface<AttackTimes> destAttak = new FakeStreamDest<>(new AttackTimes());
    DataListenerInterface<ClassifResults> destClassif = new FakeStreamDest<>(new ClassifResults());


    public DataListenerInterface<Frequency> getStreamDestFreqs() { return destFreqs;}

    public DataListenerInterface<AttackTimes> getStreamDestAttak() { return destAttak;}

    public DataListenerInterface<ClassifResults> getStreamDestClassif() { return destClassif;}
}
