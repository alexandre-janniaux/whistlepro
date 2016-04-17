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


    private States inputState = States.INPUT_WAITING;

    //private AtomicInteger processState = new AtomicInteger(States.PROCESS_WAITING);
    //private AtomicInteger outputState = new AtomicInteger(States.OUTPUT_WAITING);
    private States processState = States.PROCESS_WAITING;
    private States outputState = States.OUTPUT_WAITING;


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

    @Override
    public final void unsubscribe(StreamDataListenerInterface<F> listener) {
        sourceDelegate.unsubscribe(listener);
    }

    public final void process()
    {

            if (processState != States.PROCESS_WAITING) {
                throw new RuntimeException("process wasn't waiting");
            }
            processState = States.PROCESS_BUSY;


            if (inputState != States.INPUT_BUSY) {
                throw new RuntimeException("data were not ready for the process");
            }

        bufferIn.copyTo(bufferInD);

            inputState = States.INPUT_WAITING;

        // TODO add stream treatment (timestamping ...)
        processor.process(bufferInD, bufferOutD);

            processState = States.PROCESS_DONE;



    }

    public final void endProcess()
    {

            if (outputState == States.OUTPUT_BUSY) {
                throw new RuntimeException("output was not ready to get data from process");
            }

        bufferOutD.copyTo(bufferOut);

            outputState = States.OUTPUT_BUSY;
            processState = States.PROCESS_WAITING;
    }

    public final void pushData()
    {

            if (outputState != States.OUTPUT_BUSY) {
                throw new RuntimeException("data were not ready to be pushed");
            }
        //synchronized internally
        sourceDelegate.pushData();
            outputState = States.OUTPUT_WAITING;
    }

    @Override
    public final void fillBufferIn(E data) {

            if (inputState == States.INPUT_BUSY) {
                throw new RuntimeException("Buffer was busy");
            }
            //synchronized internally
            receiverDelegate.fillBufferIn(data);

            inputState = States.INPUT_BUSY;
    }


    public States getInputState() {
            return inputState;
    }

    public States getProcessState() {
            return processState;
    }

    public States getOutputState() {
            return outputState;
    }

    public HashSet<StreamDataListenerInterface<F>> getSubscriberList() {
        return sourceDelegate.getListeners();
    }
}
