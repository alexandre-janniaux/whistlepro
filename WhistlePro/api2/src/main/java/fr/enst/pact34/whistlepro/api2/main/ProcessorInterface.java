package fr.enst.pact34.whistlepro.api2.main;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;

/**
 * Created by mms on 25/04/16.
 */
public interface ProcessorInterface extends  DoubleDataListener{

    //preparation d'un nouvel enregistrement
    void init(TypePiste typePiste);

    void startRecProcessing();  //is supposed to clear old data if any

    void setTitle(String title);

    void correct();

    Piste getPiste();

    void waitEnd();

    void setEventLister(ProcessorEventListener l);

    Signal synthetisePiste(Piste piste);
}
