package fr.enst.pact34.whistlepro.api2.main;

import java.util.List;

import fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import fr.enst.pact34.whistlepro.api2.dataTypes.ClassifResults;

/**
 * Created by mms on 25/04/16.
 */
public class PistePercu extends Piste {
    List<ClassifResults> onomatopees;
    List<AttackTimes> attackTimes;
    @Override
    public TypePiste getTypePiste() {
        return TypePiste.Percussions;
    }
}
