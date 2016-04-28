package fr.enst.pact34.whistlepro.api2.main;

import java.util.LinkedList;
import java.util.List;

import fr.enst.pact34.whistlepro.api2.Synthese.Instru;
import fr.enst.pact34.whistlepro.api2.Synthese.Percu;

/**
 * Created by mms on 25/04/16.
 */
public class PisteMelodie extends Piste {

    private List<Instru> instruList = new LinkedList<>();

    @Override
    public TypePiste getTypePiste() {
        return TypePiste.Melodie;
    }

    public List<Instru> getInstruList() {
        return instruList;
    }

    public void addInstru(Instru instru) {
        instruList.add(instru);
    }
}
