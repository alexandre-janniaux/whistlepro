package fr.enst.pact34.whistlepro.api2.stream;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fr.enst.pact34.whistlepro.api2.threading.ThreadPool;
import fr.enst.pact34.whistlepro.api2.threading.Worker;

/**
 * Created by mms on 16/04/16.
 */
public class StreamManager extends ThreadPool {

    private final int streamWorkersSize ;
    public StreamManager(int nbThread) {
        super(nbThread);
        streamWorkersSize = nbThread*10;
        for (int i = 0; i < streamWorkersSize; i++) {
            streamWorkers.add(new streamWorker());
        }
    }

    List<StreamSimpleBase> streamsProcess = Collections.synchronizedList(new LinkedList<StreamSimpleBase>());
    List<StreamSimpleBase> streamsInput   = Collections.synchronizedList(new LinkedList<StreamSimpleBase>());
    List<StreamSimpleBase> streamsOutput  = Collections.synchronizedList(new LinkedList<StreamSimpleBase>());
    List<streamWorker> streamWorkers = Collections.synchronizedList(new LinkedList<streamWorker>());

    public synchronized void addStream(StreamSimpleBase stream)
    {
        //synchronized (streamsProcess) {
            streamsProcess.add(stream);
        //}
        //synchronized (streamsInput) {
            streamsInput.add(stream);
        //}
        //synchronized (streamsOutput) {
            streamsOutput.add(stream);
        //}
    }

    @Override
    public synchronized Runnable done(Worker w, Runnable r) {
        streamWorker worker=(streamWorker) r;
        switch (worker.getWorkType())
        {
            case PROCESS:
                //synchronized (streamsProcess) {
                    streamsProcess.add(worker.getStream());
                //}
                break;

            case FILL_OUTPUT:
                //synchronized (streamsOutput) {
                    streamsOutput.add(worker.getStream());
                //}
                break;

            case PUSH:
                //synchronized (streamsInput) {
                    streamsInput.add(worker.getStream());
                //}
                break;
        }
        streamWorkers.add(worker);
        findWork();
        return super.done(w, r);
    }

    public boolean isWorking()
    {
        return (worksToDoCount() > 0);
    }

    public void notifyWork()
    {
        findWork();
    }

    private void findWork()
    {
        findOutputWork();
        findProcessWork();
        findInputWork();
    }


    private  void findInputWork( ) {
        List<StreamSimpleBase> streams = streamsInput;
        //synchronized (streams) {
            for (int i = streams.size(); i > 0; i--) {
                StreamSimpleBase s = streams.get(i - 1);
                if (streamWorkers.size() <= 0) break;
                if (s.getOutputState() == States.OUTPUT_BUSY) {
                    // on push si on peut
                    HashSet<StreamDataListenerInterface> sub = s.getSubscriberList();
                    boolean ok = true;
                    Iterator it = sub.iterator();
                    while (it.hasNext()) {
                        StreamDataListenerInterface n = (StreamDataListenerInterface) it.next();
                        if (n.getInputState() == States.INPUT_BUSY) {
                            ok = false;
                            break;
                        }
                    }

                    if (ok) {
                        streamWorker r = streamWorkers.remove(0);
                        r.setUpWorker(s, streamWorker.WorkTypes.PUSH);
                        addWorkToDo(r);
                        streamsOutput.remove(s);
                    }
                }
            }
        //}
    }

    private  void findProcessWork() {
        List<StreamSimpleBase> streams = streamsProcess;
        //synchronized (streams) {
            for (int i = streams.size(); i > 0; i--) {
                StreamSimpleBase s = streams.get(i - 1);
                if (streamWorkers.size() <= 0) break;
                if (s.getProcessState() == States.PROCESS_WAITING && s.getInputState() == States.INPUT_BUSY) {
                    // on passe les data du buffer in au process
                    streamWorker r = streamWorkers.remove(0);
                    r.setUpWorker(s, streamWorker.WorkTypes.PROCESS);
                    addWorkToDo(r);
                    streamsOutput.remove(s);
                }
            }
        //}
    }

    private  void findOutputWork() {
        List<StreamSimpleBase> streams = streamsOutput;
        //synchronized (streams) {
            for (int i = streams.size(); i > 0; i--) {
                StreamSimpleBase s = streams.get(i - 1);
                if (streamWorkers.size() <= 0) break;
                if (s.getProcessState() == States.PROCESS_DONE && s.getOutputState() == States.OUTPUT_WAITING) {
                    // on passe les data du process au buffer
                    streamWorker r = streamWorkers.remove(0);
                    r.setUpWorker(s, streamWorker.WorkTypes.FILL_OUTPUT);
                    addWorkToDo(r);
                    streamsOutput.remove(s);
                }
            }
        //}
    }

}
