package main.java.fr.enst.pact34.whistlepro.api2.transcription;

import main.java.fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import main.java.fr.enst.pact34.whistlepro.api2.dataTypes.ClassifResults;
import main.java.fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import main.java.fr.enst.pact34.whistlepro.api2.dataTypes.MusicTrack;
import main.java.fr.enst.pact34.whistlepro.api2.phantoms.FakeStreamDest;
import main.java.fr.enst.pact34.whistlepro.api2.stream.DataListenerInterface;
import main.java.fr.enst.pact34.whistlepro.api2.stream.StreamSource;

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
