package main.java.fr.enst.pact34.whistlepro.api2.stream;

import main.java.fr.enst.pact34.whistlepro.api2.DataTypes.Signal;
import main.java.fr.enst.pact34.whistlepro.api2.DataTypes.SignalSetInterface;

/**
 * Created by  Mohamed on 15/03/16.
 */
public abstract class SignalStreamSourceStreamStream extends StreamStreamDataSource<SignalSetInterface> {

    SignalSetInterface bufferOut = new Signal();

    protected SignalSetInterface getBufferOut()
    {
        return  bufferOut;
    }

    public void bufferFilled() {
        //called when buffer is Filled

        for (DataListenerInterface<SignalSetInterface> listener: this.getListeners())
        {
            SignalSetInterface buffer = listener.getBufferToFill();
            if(buffer.length() != bufferOut.length())
                buffer.setLength(bufferOut.length());

            for(int i = 0; i < buffer.length(); i++)
            {
                buffer.setValue(i,bufferOut.getValue(i));
            }
            listener.bufferFilled();
        }
    }
}
