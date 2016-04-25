package fr.enst.pact34.whistlepro.api2.main;

/**
 * Created by mms on 25/04/16.
 */
public interface ProcessorInterface {

    //preparation d'un nouvel enregistrement
    void init(TypeRec typeRec);

    void setTitle(String title);

    void correct();

    Piste getPiste();

    void waitEnd();

    void setEventLister(ProcessorEventListener l);
}
