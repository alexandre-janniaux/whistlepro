package fr.enst.pact34.whistlepro.api2.threading;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mms on 16/04/16.
 */
public class ThreadPool implements WorkerManagerI {

    public ThreadPool(int nbThread)
    {
        for (int i = 0; i < nbThread; i++) {
            workers.add(new Worker(this));
        }
    }

    HashSet<Worker> workers = new HashSet<>();
    List<Runnable> worksToDo = Collections.synchronizedList(new LinkedList<Runnable>());

    public int worksToDoCount()
    {
        return worksToDo.size();
    }

    @Override
    public synchronized Runnable done(Worker w, Runnable r) {
        if(worksToDo.isEmpty()) return null;
        return worksToDo.remove(0);
    }


    public synchronized void addWorkToDo(Runnable r)
    {
        for (Worker w: workers
             ) {
            if(w.isWorking() == false)
            {
                w.addWork(r);
                return;
            }
        }

        worksToDo.add(r);
    }
}
