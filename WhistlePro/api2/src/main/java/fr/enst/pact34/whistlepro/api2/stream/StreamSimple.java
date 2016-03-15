package main.java.fr.enst.pact34.whistlepro.api2.stream;


/**
 * Created by mms on 14/03/16.
 */
public class StreamSimple<E extends StreamDataInterface,F extends StreamDataInterface> extends StreamBase{

    //Shouldn't be edited internally
    E bufferIn = null;
    //Should be filled by process
    F bufferOut = null;
    ProcessInterface<E, F> processor = null;

    public StreamSimple(E bufferIn, F bufferOut, ProcessInterface<E, F> processor) {
        this.bufferIn = bufferIn;
        this.bufferOut = bufferOut;
        this.processor = processor;
    }

    @Override
    protected F getBufferOut() {
        return bufferOut;
    }

    @Override
    public E getBufferToFill() {
        return bufferIn;
    }

    @Override
    public void bufferFilled() {
        processor.process(bufferIn,bufferOut);
        super.bufferFilled();
    }


}
