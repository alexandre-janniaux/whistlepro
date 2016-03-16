package  fr.enst.pact34.whistlepro.api2.stream;


/**
 * Created by mms on 14/03/16.
 */
public class StreamSimpleBase<E extends StreamDataInterface<E>,F extends StreamDataInterface<F>>
        extends StreamSourceBase<F> implements DataListenerInterface<E>
{

    //Shouldn't be edited internally
    E bufferIn = null;
    //Should be filled by process
    F bufferOut = null;
    ProcessInterface<E, F> processor = null;

    @Override
    public void fillBufferIn(E data) {
        //synchronized internally
        receiverDelegate.fillBufferIn(data);

        synchronized (bufferIn)
        {
            synchronized (bufferOut)
            {
                processor.process(bufferIn,bufferOut);
            }
        }
        super.pushData();
    }

    StreamDestBase<E> receiverDelegate = null;

    public StreamSimpleBase(E bufferIn, F bufferOut, ProcessInterface<E, F> processor) {
        this.bufferIn = bufferIn;
        this.bufferOut = bufferOut;
        this.processor = processor;

        receiverDelegate = new StreamDestBase<>(bufferIn);

    }

    @Override
    protected F getBufferOut() {
        return bufferOut;
    }

}
