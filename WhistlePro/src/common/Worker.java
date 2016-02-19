package common;

import java.lang.Thread;
import java.lang.Runnable;
import java.util.Observable;
import java.util.concurrent.locks.ReentrantLock;

import common.JobProviderInterface;

public class Worker
    extends Observable
{
    class WorkerImpl implements Runnable {
        private Worker worker;
        private JobProviderInterface job;

        public WorkerImpl(Worker worker) {
            this.worker = worker;
        }

        public void setJobProvider(JobProviderInterface job)
        {
            this.job = job;
        }

        public void run() {
            this.worker.lock.lock();
            this.job.doWork();
            this.worker.lock.unlock();
            this.worker.finished = true;
            this.worker.setChanged();
            this.worker.notify();
        }
    } 

    private Thread thread;
    private boolean running=true;
    private boolean finished=true;
    private ReentrantLock lock = new ReentrantLock();
    private WorkerImpl worker = new WorkerImpl(this);

    public Worker()
    {
        this.lock.lock();
        this.thread = new Thread(worker);
    }

    public void start(JobProviderInterface job)
    {
        // TODO: exception if already started
        this.worker.setJobProvider(job);
        this.finished = false; 
        this.lock.unlock();
    }

    public void stop() {
        this.running = false;
    }

    public boolean isFinished() {
        return this.finished;
    }


}
