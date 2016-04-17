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
        if(isWorking ) return;
        toDo = runnable;
        isWorking = true;
        synchronized (thread) {
            thread.notify();
        }
    }

    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            //while(true) {
            //    try {
                    while (true) {
                        if (toDo == null) {
                            try {
                                synchronized (thread) {
                                    isWorking = false;
                                    thread.wait();
                                }
                                //Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (toDo != null) {
                            toDo.run();
                            toDo = manager.done(thisWorker, toDo);
                        }
                    }

            //    } catch (RuntimeException e) {
            //        System.out.print("Run error threadpool");
            //    }
            //}
        }
    });


}
