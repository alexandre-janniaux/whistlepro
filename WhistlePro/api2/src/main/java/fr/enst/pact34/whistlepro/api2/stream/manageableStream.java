package fr.enst.pact34.whistlepro.api2.stream;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by mms on 17/04/16.
 */
public interface manageableStream<E> {

    int getInputState();

    int getProcessState();

    int getOutputState();

    void reset();

    void process();

    void endProcess();

    void pushData();

    HashSet<StreamDataListenerInterface<E>> getSubscriberList();

    void setManager(StreamManager sm);
}
