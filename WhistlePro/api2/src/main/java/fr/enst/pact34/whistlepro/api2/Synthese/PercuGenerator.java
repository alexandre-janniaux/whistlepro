package fr.enst.pact34.whistlepro.api2.Synthese;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;

/**
 * Created by mms on 28/04/16.
 */
public class PercuGenerator {

    private double Fs ;
    private Hashtable<Percu.Type,PercuGenElmt> percuGenElmts = new Hashtable<>();

    public PercuGenerator(double Fs)
    {
        this.Fs = Fs;
    }

    public double getSamplingFreq() {
        return Fs;
    }

    public Signal getPercu(Percu.Type type)
    {  PercuGenElmt e = percuGenElmts.get(type);
        if(e == null)
            throw new RuntimeException(type.name()+"'s data weren't added to the generator.");
        return e.getData();
    }

    public void addPercu(Percu.Type typePercussion, Signal donnees)
    {
        PercuGenElmt e = new PercuGenElmt(typePercussion,donnees);
        percuGenElmts.put(typePercussion,e);
    }

    private class PercuGenElmt {
        Percu.Type typePercussion;
        Signal sound = null;

        public PercuGenElmt(Percu.Type typePercussion, Signal donnees) {
            this.typePercussion = typePercussion;
            if (donnees == null)
                throw new RuntimeException("PercuGenerator cannot be created, no data. (" + typePercussion.name() + ")");
            if (donnees.length() <= 0)
                throw new RuntimeException("PercuGenerator cannot be created, data empty. (" + typePercussion.name() + ")");
            sound = donnees;
        }

        public Signal getData() {
            return sound;
        }
    }
}
