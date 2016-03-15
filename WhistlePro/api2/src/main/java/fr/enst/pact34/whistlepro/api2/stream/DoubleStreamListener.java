package main.java.fr.enst.pact34.whistlepro.api2.stream;


import java.util.Hashtable;
import java.util.Objects;

/**
 * Created by mms on 15/03/16.
 */
public abstract class DoubleStreamListener<E,F>
{

    private ListenerStream<E> le = null;
    private ListenerStream<F> lf = null;

    public DoubleStreamListener(E bufferE, F bufferF) {

        le = new ListenerStream<E>(bufferE) {

            @Override
            public void bufferFilled() {

            }
        };

        lf = new ListenerStream<F>(bufferF) {

            @Override
            public void bufferFilled() {

            }
        };
    }

    public ListenerStream<E> getListenerE(){return le;}
    public ListenerStream<F> getListenerF(){return lf;}

    //Should be called when all listeners have received theirs datas
    public abstract void bufferFilled();

}
