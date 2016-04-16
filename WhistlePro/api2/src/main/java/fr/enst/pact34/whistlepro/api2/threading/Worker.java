package fr.enst.pact34.whistlepro.api2.threading;

public class Worker {

    public Worker(WorkerManagerI manager)
    {
        this.manager = manager;
        thread.start();
    }

    private WorkerManagerI manager ;
    private Runnable toDo = null;
    private Worker thisWorker = this;
    private boolean isWorking = false;

    public boolean isWorking()
    {
        return isWorking;
    }

    public synchronized void addWork(Runnable runnable)
    {
        if(isWorking) return;
        toDo = runnable;
        isWorking = true;
        synchronized (thread) {
            thread.notify();
        }
    }

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            Runnable tmp ;
            while(true) {
                if(toDo==null) {
                    isWorking = false;
                    try {
                        synchronized (thread) {
                            thread.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                toDo.run();
                toDo = manager.done(thisWorker,toDo);
            }
        }
    });


}
