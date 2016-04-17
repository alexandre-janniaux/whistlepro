package  fr.enst.pact34.whistlepro.api2.transcription;

import java.util.LinkedList;

import  fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import  fr.enst.pact34.whistlepro.api2.dataTypes.ClassifResults;
import  fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import  fr.enst.pact34.whistlepro.api2.dataTypes.MusicTrack;
import  fr.enst.pact34.whistlepro.api2.stream.StreamSourceBase;

/**
 * Created by mms on 15/03/16.
 */

public class TranscriptionBase extends StreamSourceBase<MusicTrack> implements PartialDataStreamParent {
    PartialDataStreamDest<Frequency> destFreqs = new PartialDataStreamDest<>(new Frequency(),this);
    PartialDataStreamDest<AttackTimes> destAttak = new PartialDataStreamDest<>(new AttackTimes(),this);
    PartialDataStreamDest<ClassifResults> destClassif = new PartialDataStreamDest<>(new ClassifResults(),this);

    public TranscriptionBase(MusicTrack bufferOut) {
        super(bufferOut);
    }


    public PartialDataStreamDest<Frequency> getStreamDestBaseFreqs() { return destFreqs;}

    public PartialDataStreamDest<AttackTimes> getStreamDestBaseAttak() { return destAttak;}

    public PartialDataStreamDest<ClassifResults> getStreamDestBaseClassif() { return destClassif;}

    LinkedList<Frequency> freqs = new LinkedList<>();
    LinkedList<AttackTimes> attacks = new LinkedList<>();
    LinkedList<ClassifResults> classifs = new LinkedList<>();

    private int nbReceived = 0;
    public int getNbReceived()
    {
        return nbReceived;
    }

    @Override
    public synchronized void newData(PartialDataStreamDest emitter, Object c) {
        if (emitter == destClassif)
        {
            classifs.add((ClassifResults) c);
        }
        else if (emitter == destAttak)
        {
            attacks.add((AttackTimes) c);
        }
        else if (emitter == destFreqs)
        {
            freqs.add((Frequency) c);
        }

        int min =   (freqs.size()<attacks.size())?freqs.size():attacks.size();
        nbReceived = (min < classifs.size())?min:classifs.size();
    }

    public void clear() {
        freqs.clear();
        attacks.clear();
        classifs.clear();
        nbReceived = 0;
    }
}
