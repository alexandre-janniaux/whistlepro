package  fr.enst.pact34.whistlepro.api2.stream;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class StreamSimpleBase<E extends StreamDataInterface<E>,F extends StreamDataInterface<F>>
        implements StreamDataListenerInterface<E>, StreamDataSourceInterface<F>
{

    private E bufferIn = null;
    private F bufferOut = null;
    private E bufferInD = null;
    private F bufferOutD = null;

    private StreamProcessInterface<E, F> processor = null;
    private StreamDestBase<E> receiverDelegate = null;
    private StreamSourceBase<F> sourceDelegate = null;



    private AtomicInteger inputState = new AtomicInteger(States.INPUT_WAITING);
    private AtomicInteger processState = new AtomicInteger(States.PROCESS_WAITING);
    private AtomicInteger outputState = new AtomicInteger(States.OUTPUT_WAITING);
    //private States inputState = States.INPUT_WAITING;
    //private States processState = States.PROCESS_WAITING;
    //private States outputState = States.OUTPUT_WAITING;


    public StreamSimpleBase(E bufferIn, F bufferOut, StreamProcessInterface<E, F> processor) {
        this.bufferIn = bufferIn;
        this.bufferOut = bufferOut;
        this.bufferInD = bufferIn.getNew();
        this.bufferOutD = bufferOut.getNew();
        this.processor = processor;

        receiverDelegate = new StreamDestBase<>(bufferIn);
        sourceDelegate = new StreamSourceBase<>(bufferOut);

    }

    @Override
    public final void subscribe(StreamDataListenerInterface<F> listener) {
        sourceDelegate.subscribe(listener);
    }

    int id;

    @Override
    public final void unsubscribe(StreamDataListenerInterface<F> listener) {
        sourceDelegate.unsubscribe(listener);
    }

    public final void process()
    {
        if (processState.get() != States.PROCESS_WAITING) {
            //throw new RuntimeException("process wasn't waiting");
            return;
        }
        //System.out.println("Entre process");
        if (inputState.get() != States.INPUT_BUSY) {
            //throw new RuntimeException("data were not ready for the process");
            return;
        }
        processState.set(States.PROCESS_BUSY);

        id = bufferIn.getId();
        bufferIn.copyTo(bufferInD);

        inputState .set(States.INPUT_WAITING);


        //TODO timestamp ...
        processor.process(bufferInD, bufferOutD);

        processState.set(States.PROCESS_DONE);

        //System.out.println("attente fin process");
    }

    public final void endProcess()
    {
        //System.out.println("Entre fin process");
        //System.out.println("ending "+id);
        if (outputState.get() == States.OUTPUT_BUSY) {
            //throw new RuntimeException("output was not ready to get data from process");
            return;
        }

        bufferOutD.copyTo(bufferOut);
        bufferOut.setId(id);

        outputState.set(States.OUTPUT_BUSY);

        processState.set(States.PROCESS_WAITING);

        //System.out.println("fin fin process");
    }

    public final void pushData()
    {
        //System.out.println("push data");

        if (outputState.get() != States.OUTPUT_BUSY) {
            //throw new RuntimeException("data were not ready to be pushed");
            return;
        }

        //synchronized internally
        sourceDelegate.pushData();

        outputState.set( States.OUTPUT_WAITING);

        //System.out.println("waiting new data");
    }

    @Override
    public final void fillBufferIn(E data) {
        //System.out.println("new data in ");

        if (inputState.get() == States.INPUT_BUSY) {
            //throw new RuntimeException("Buffer was busy");
            return;
        }

        //synchronized internally
        receiverDelegate.fillBufferIn(data);

        inputState.set(States.INPUT_BUSY);
        //System.out.println("new data in waiting process");
    }


    public int getInputState() {
            return inputState.get();
    }

    public int getProcessState() {
            return processState.get();
    }

    public int getOutputState() {
            return outputState.get();
    }

    public HashSet<StreamDataListenerInterface<F>> getSubscriberList() {
        return sourceDelegate.getListeners();
    }
}
