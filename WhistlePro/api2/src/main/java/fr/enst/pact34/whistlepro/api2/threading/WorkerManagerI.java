package fr.enst.pact34.whistlepro.api2.threading;

/**
 * Created by mms on 16/04/16.
 */
public interface WorkerManagerI {
    Runnable done(Worker w, Runnable r);
}
