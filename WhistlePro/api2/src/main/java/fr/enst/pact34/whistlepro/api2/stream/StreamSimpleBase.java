package  fr.enst.pact34.whistlepro.api2.stream;


import fr.enst.pact34.whistlepro.api2.threading.JobProviderInterface;

/**
 * Created by mms on 14/03/16.
 */
public class StreamSimpleBase<E extends StreamDataInterface<E>,F extends StreamDataInterface<F>>
        implements StreamDataListenerInterface<E>, StreamDataSourceInterface<F>, JobProviderInterface
{

    private E bufferIn = null;
    private F bufferOut = null;
    private StreamProcessInterface<E, F> processor = null;
    private StreamDestBase<E> receiverDelegate = null;
    private StreamSourceBase<F> sourceDelegate = null;

    public StreamSimpleBase(E bufferIn, F bufferOut, StreamProcessInterface<E, F> processor) {
        this.bufferIn = bufferIn;
        this.bufferOut = bufferOut;
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

    private final void processAndPush()
    {
        // TODO add stream treatment (timestamping ...)

        synchronized (bufferIn)
        {
            synchronized (bufferOut)
            {
                processor.process(bufferIn,bufferOut);
            }
        }

        //synchronized internally
        sourceDelegate.pushData();

    }

    private boolean inputValid = false;

    @Override
    public final void fillBufferIn(E data) {
        //synchronized internally
        receiverDelegate.fillBufferIn(data);
        inputValid = true;
    }


    @Override
    public boolean isWorkAvailable() {
        return inputValid;
    }

    @Override
    public void doWork() {
        processAndPush();
        inputValid = false;
    }
}
