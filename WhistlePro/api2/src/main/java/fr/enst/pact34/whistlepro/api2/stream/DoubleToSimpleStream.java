package main.java.fr.enst.pact34.whistlepro.api2.stream;

/**
 * Created by mms on 15/03/16.
 */
public class DoubleToSimpleStream<E,F,G extends StreamDataInterface<G>> extends SourceStreamBase<G>
{

    DoubleStreamListener<E,F> listeners= null;
    G bufferOut = null;
    ProcessDoubleInterface<E,F,G> processor = null;

    public DoubleToSimpleStream(final E bufferInE, final F bufferInF, final G bufferOut, ProcessDoubleInterface<E,F,G> process) {
        this.bufferOut = bufferOut;
        this.processor = process;
        final DoubleToSimpleStream<E,F,G> instance = this;
        this.listeners = new DoubleStreamListener<E, F>(bufferInE,bufferInF) {
            @Override
            public void bufferFilled() {
                processor.process(bufferInE,bufferInF,bufferOut);
                instance.getListenerE().bufferFilled();
                instance.getListenerF().bufferFilled();
                instance.bufferFilled();
            }
        };
    }

    @Override
    protected G getBufferOut() {
        return bufferOut;
    }

    public DataListenerInterface<E> getListenerE()
    {
        return listeners.getListenerE();
    }

    public DataListenerInterface<F> getListenerF()
    {
        return listeners.getListenerF();
    }
}
