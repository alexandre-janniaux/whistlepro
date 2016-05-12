package fr.enst.pact34.whistlepro.api2.stream;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class StreamInputWraper<E,F extends StreamDataInterface<F>>
        implements StreamDataListenerInterface<E>, StreamDataSourceInterface<F>, manageableStream<F>
{

    private F bufferOutM = null;
    private LinkedList<E> bufferIn = new LinkedList<>();
    private LinkedList<F> bufferOut =  new LinkedList<>();
    private LinkedList<E> bufferInD = new LinkedList<>();
    private LinkedList<F> bufferOutD = new LinkedList<>();

    private StreamProcessInterface<LinkedList<E>, LinkedList<F>> processor = null;
    private StreamSourceBase<F> sourceDelegate = null;



    private AtomicInteger inputState = new AtomicInteger(States.INPUT_WAITING);
    private AtomicInteger processState = new AtomicInteger(States.PROCESS_WAITING);
    private AtomicInteger outputState = new AtomicInteger(States.OUTPUT_WAITING);
    //private States inputState = States.INPUT_WAITING;
    //private States processState = States.PROCESS_WAITING;
    //private States outputState = States.OUTPUT_WAITING;


    public StreamInputWraper(F bufferOut, StreamProcessInterface<LinkedList<E>, LinkedList<F>> processor) {
        this.bufferOutM = bufferOut;
        this.processor = processor;
        sourceDelegate = new StreamSourceBase<>(bufferOutM);

    }

    @Override
    public final void subscribe(StreamDataListenerInterface<F> listener) {
        sourceDelegate.subscribe(listener);
    }


    @Override
    public final void unsubscribe(StreamDataListenerInterface<F> listener) {
        sourceDelegate.unsubscribe(listener);
    }

    @Override
    public synchronized final void process()
    {
        if (processState.get() != States.PROCESS_WAITING || inputState.get() != States.INPUT_BUSY) {
            //throw new RuntimeException("process wasn't waiting");
            return;
        }
        //System.out.println("Entre process");
        //if (inputState.get() != States.INPUT_BUSY) {
            //throw new RuntimeException("data were not ready for the process");
        //    return;
        //}
        processState.set(States.PROCESS_BUSY);

        //new id calcul todo
        //id = bufferIn.getId();
        synchronized (bufferIn) {
        bufferInD.add(bufferIn.remove(0));

        if (bufferIn.size() <= 0)
        {
            inputState.set(States.INPUT_WAITING);
        }
        }

        //TODO timestamp ...
        processor.process(bufferInD, bufferOutD);

        bufferInD.clear();
        synchronized (bufferOut) {
            bufferOut.addAll(bufferOutD);
        }
        bufferOutD.clear();

        //processState.set(States.PROCESS_DONE); => will never go to end process not needed

        synchronized (bufferOut) {
            if (bufferOut.size() > 0)
                outputState.set(States.OUTPUT_BUSY);
        }

        processState.set(States.PROCESS_WAITING);

        //System.out.println("attente fin process");
    }

    @Override
    public final void endProcess()
    {
        // ! Should not come here
        //System.out.println("Entre fin process");
        //System.out.println("ending "+id);
        //if (outputState.get() == States.OUTPUT_BUSY) {
            //throw new RuntimeException("output was not ready to get data from process");
        //    return;
        //}

        //bufferOutD.copyTo(bufferOut);
        //bufferOut.setId(id);

        //outputState.set(States.OUTPUT_BUSY);

        //processState.set(States.PROCESS_WAITING);

        //System.out.println("fin fin process");
    }

    private int id = 0;

    public void resetIds()
    {
        id = 0;
    }
    @Override
    public final void pushData()
    {
        //System.out.println("push data");

        //if (outputState.get() != States.OUTPUT_BUSY) {
            //throw new RuntimeException("data were not ready to be pushed");
        //    return;
        //}

        synchronized (bufferOut) {
            bufferOut.remove(0).copyTo(bufferOutM);
        }
        bufferOutM.setId(id++);

        //synchronized internally
        sourceDelegate.pushData();


        if(bufferOut.size()<=0)
            outputState.set(States.OUTPUT_WAITING);

        //outputState.set( States.OUTPUT_WAITING);

        //System.out.println("waiting new data");
    }

    @Override
    public final void fillBufferIn(E data) {
        //System.out.println("new data in ");

        //if (inputState.get() == States.INPUT_BUSY) {
        //throw new RuntimeException("Buffer was busy");
        //    return;
        //}

        //synchronized internally
        synchronized (bufferIn) {
            bufferIn.add(data);

            //processState.set(States.PROCESS_WAITING);
            inputState.set(States.INPUT_BUSY);
        }
        //System.out.println("new data in waiting process");
        if(streamManager != null)
        {
            if(streamManager.isWorking()==false)
            {
                streamManager.notifyWork();
                //System.out.println("manager wakeup");
            }
        }
    }


    @Override
    public int getInputState() {
            return inputState.get();
    }

    @Override
    public int getProcessState() {
            return processState.get();
    }

    @Override
    public int getOutputState() {
            return outputState.get();
    }

    @Override
    public void reset() {
        processor.reset();
        bufferIn.clear();
        bufferOut.clear();
        bufferInD.clear();
        bufferOutD.clear();
        inputState.set(States.INPUT_WAITING);
        processState.set(States.PROCESS_WAITING);
        outputState.set(States.OUTPUT_WAITING);
    }

    @Override
    public HashSet<StreamDataListenerInterface<F>> getSubscriberList() {
        return sourceDelegate.getListeners();
    }

    private StreamManager streamManager = null;
    @Override
    public void setManager(StreamManager sm) {
        this.streamManager = sm;
    }

    public boolean hasWork() {
        if(bufferIn.size()>0 || bufferOut.size()>0 || processState.get() == States.PROCESS_BUSY) return true;
        return false;
    }
}
