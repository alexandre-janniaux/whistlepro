package main.java.fr.enst.pact34.whistlepro.api2.transcription;

import main.java.fr.enst.pact34.whistlepro.api2.DataTypes.AttackTimes;
import main.java.fr.enst.pact34.whistlepro.api2.DataTypes.ClassifResults;
import main.java.fr.enst.pact34.whistlepro.api2.DataTypes.Frequency;
import main.java.fr.enst.pact34.whistlepro.api2.DataTypes.MusicTrack;
import main.java.fr.enst.pact34.whistlepro.api2.stream.StreamDestination;
import main.java.fr.enst.pact34.whistlepro.api2.stream.StreamSource;

/**
 * Created by mms on 15/03/16.
 */

public abstract class TranscriptionBase extends StreamSource<MusicTrack>
{
    StreamDestination<Frequency> destFreqs = null;
    StreamDestination<AttackTimes> destAttak = null;
    StreamDestination<ClassifResults> destClassif = null;


    public StreamDestination<Frequency> getDestFreqs() { return destFreqs;}

    public StreamDestination<AttackTimes> getDestAttak() { return destAttak;}

    public StreamDestination<ClassifResults> getDestClassif() { return destClassif;}
}
