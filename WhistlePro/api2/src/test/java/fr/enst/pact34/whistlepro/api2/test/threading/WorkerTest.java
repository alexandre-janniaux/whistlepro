package fr.enst.pact34.whistlepro.api2.test.threading;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.threading.Worker;
import fr.enst.pact34.whistlepro.api2.threading.WorkerManagerI;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class WorkerTest implements WorkerManagerI {

    boolean workDone ;

    @Test
    public void testWorker()
    {
        Thread thisThread = Thread.currentThread();
        final Thread[] runnignThread = {null};
        final boolean[] modifiedByRun = {false};

        workDone = false;

        Worker worker = new Worker(this);

        worker.addWork(new Runnable() {
            @Override
            public void run() {
                runnignThread[0] = Thread.currentThread();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println("ruuun");
                modifiedByRun[0] =true;
            }
        });

        //System.out.println("before while");
        while(worker.isWorking())
        {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("after while");

        assertTrue(modifiedByRun[0]);
        assertTrue(workDone);
        assertNotEquals(thisThread,runnignThread[0]);
    }

    @Override
    public Runnable done(Worker w,Runnable r) {
        //System.out.println("WorkDone");
        workDone = true;
        return null;
    }
}
