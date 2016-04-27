package  fr.enst.pact34.whistlepro.api2.transcription;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import  fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import  fr.enst.pact34.whistlepro.api2.dataTypes.ClassifResults;
import  fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import  fr.enst.pact34.whistlepro.api2.dataTypes.MusicTrack;
import fr.enst.pact34.whistlepro.api2.main.TypePiste;
import  fr.enst.pact34.whistlepro.api2.stream.StreamSourceBase;

/**
 * Created by mms on 15/03/16.
 */

public class TranscriptionBase extends StreamSourceBase<MusicTrack> implements PartialDataStreamParent {
    PartialDataStreamDest<Frequency> destFreqs = new PartialDataStreamDest<>(new Frequency(),this);
    PartialDataStreamDest<AttackTimes> destAttak = new PartialDataStreamDest<>(new AttackTimes(),this);
    PartialDataStreamDest<ClassifResults> destClassif = new PartialDataStreamDest<>(new ClassifResults(),this);
    private TypePiste typePiste;

    public TranscriptionBase(MusicTrack bufferOut) {
        super(bufferOut);
    }


    public PartialDataStreamDest<Frequency> getStreamDestBaseFreqs() { return destFreqs;}

    public PartialDataStreamDest<AttackTimes> getStreamDestBaseAttak() { return destAttak;}

    public PartialDataStreamDest<ClassifResults> getStreamDestBaseClassif() { return destClassif;}

    List<Frequency> freqs = Collections.synchronizedList(new LinkedList<Frequency>());
    List<AttackTimes> attacks = Collections.synchronizedList(new LinkedList<AttackTimes>());
    List<ClassifResults> classifs = Collections.synchronizedList(new LinkedList<ClassifResults>());

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

        switch (typePiste)
        {
            case Melodie:
                nbReceived =(freqs.size()<attacks.size())?freqs.size():attacks.size();
                break;
            case Percussions:
                nbReceived = (attacks.size() < classifs.size())?attacks.size():classifs.size();
                break;
        }
    }

    public void clear() {
        freqs.clear();
        attacks.clear();
        classifs.clear();
        nbReceived = 0;
    }

    public String getLastClassifElement() {
        String str = "_";
        int i = classifs.size();
        if(i>0) {
            ClassifResults c = classifs.get(i - 1);
            if (c.recoLevel() > 0.5)
                str = c.getRecoClass();
        }
        return str;
    }

    public AttackTimes getLastAttackElement()
    {
        AttackTimes tmp = null;
        int i = attacks.size();
        if(i>0) {
            tmp = new AttackTimes();
            attacks.get(i-1).copyTo(tmp);
        }
        return tmp;
    }

    public void setupFor(TypePiste typePiste) {
        this.typePiste = typePiste;
    }
}
