package fr.enst.pact34.whistlepro.api.common;

import java.util.ArrayList;
import fr.enst.pact34.whistlepro.api.common.JobProviderInterface;
import java.util.Observer;
import java.util.Observable;

public class ThreadPool
    implements Observer
{
    private ArrayList<JobProviderInterface> jobs;
    private int poolSize;
    private ArrayList<Worker> workers;


    public ThreadPool(int poolSize) {
        setPoolSize(poolSize);
    }

    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize; // TODO: change number of threads
    }

    public int getPoolSize(int poolSize) {
        return this.poolSize;
    }

    public void addJobProvider(JobProviderInterface job) {
        if (!jobs.contains(job))
            this.jobs.add(job);
    }

    public void update(Observable observable, Object objet) {
        Worker worker = (Worker) observable;
        if (worker != null && worker.isFinished() && this.workers.contains(worker))
        {
            // TODO: find job and set it
            JobProviderInterface job = null;
            if (job != null)
                worker.start(job);
        }
    }
}
