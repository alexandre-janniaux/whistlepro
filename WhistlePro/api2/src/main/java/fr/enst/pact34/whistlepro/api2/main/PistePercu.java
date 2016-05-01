package fr.enst.pact34.whistlepro.api2.main;

import java.util.LinkedList;
import java.util.List;

import fr.enst.pact34.whistlepro.api2.Synthese.Percu;

/**
 * Created by mms on 25/04/16.
 */
public class PistePercu extends Piste {

    private List<Percu> percuList = new LinkedList<>();

    @Override
    public TypePiste getTypePiste() {
        return TypePiste.Percussions;
    }

    public List<Percu> getPercuList() {
        return percuList;
    }

    public void addPercu(Percu percu) {
        percuList.add(percu);
    }
}
