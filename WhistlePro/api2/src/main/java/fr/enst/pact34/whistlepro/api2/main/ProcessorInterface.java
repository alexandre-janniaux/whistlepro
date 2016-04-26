package fr.enst.pact34.whistlepro.api2.main;

/**
 * Created by mms on 25/04/16.
 */
public interface ProcessorInterface {

    //preparation d'un nouvel enregistrement
    void init(TypePiste typePiste);

    void startRecProcessing();  //is supposed to clear old data if any

    void setTitle(String title);

    void correct();

    Piste getPiste();

    void waitEnd();

    void setEventLister(ProcessorEventListener l);
}
