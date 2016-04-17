package fr.enst.pact34.whistlepro.api2.stream;

import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;

/**
 * Created by mms on 16/04/16.
 */
public class streamWorker implements Runnable{

    private final int c;

    public streamWorker()
    {
         c = (int) (Math.random() * 100);
    }

    public StreamSimpleBase getStream() {
        return stream;
    }

    public WorkTypes getWorkType() {
        return toDo;
    }

    enum WorkTypes{
        PROCESS,
        FILL_OUTPUT,
        PUSH
    }

    WorkTypes toDo = null;
    StreamSimpleBase stream = null;
    boolean valid = false;

    void setUpWorker(StreamSimpleBase stream, WorkTypes toDo)
    {
        valid = true;
        this.toDo = toDo;
        this.stream = stream;
    }

    @Override
    public void run() {
        if(!valid)
        {
            return;
        }

        //System.out.print(Thread.currentThread().getId() + ", "+c+" : " );

        switch (toDo)
        {
            case PROCESS:
                //System.out.println("process");
                stream.process();
                break;

            case FILL_OUTPUT:
                //System.out.println("out");
                stream.endProcess();
                break;

            case PUSH:
                //System.out.println("push");
                stream.pushData();
                break;

        }
        valid = false;
    }
}
