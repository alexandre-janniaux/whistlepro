package main.java.fr.enst.pact34.whistlepro.api2.stream;

/**
 * Created by mms on 15/03/16.
 */
public abstract class SourceStreamBase<G extends StreamDataInterface<G>> extends DataSource<G>
{

    public void bufferFilled() {
        //signal that indicate to start process

        for (DataListenerInterface<G> listener: this.getListeners())
        {
            G buffer = listener.getBufferToFill();
            getBufferOut().copyTo(buffer);
            listener.bufferFilled();
        }
    }

    protected abstract G getBufferOut();

}
