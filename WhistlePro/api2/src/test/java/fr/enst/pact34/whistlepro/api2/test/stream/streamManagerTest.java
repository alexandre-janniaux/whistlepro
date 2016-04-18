package fr.enst.pact34.whistlepro.api2.test.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessCopy;
import fr.enst.pact34.whistlepro.api2.stream.States;
import fr.enst.pact34.whistlepro.api2.stream.StreamDataListenerInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamManager;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 16/04/16.
 */
public class streamManagerTest implements StreamDataListenerInterface<Signal> {

    @Test
    public void streamManagerTest() {
        //System.out.println("start");
        final StreamManager threadPool = new StreamManager(5);

        final StreamSimpleBase<Signal, Signal> copy1 = new StreamSimpleBase<>(
                new Signal(), new Signal(), new FakeProcessCopy<Signal>()
        );

        StreamSimpleBase<Signal, Signal> copy2 = new StreamSimpleBase<>(
                new Signal(), new Signal(), new FakeProcessCopy<Signal>()
        );
        copy1.subscribe(copy2);
        StreamSimpleBase<Signal, Signal> copy3 = new StreamSimpleBase<>(
                new Signal(), new Signal(), new FakeProcessCopy<Signal>()
        );
        copy2.subscribe(copy3);
        StreamSimpleBase<Signal, Signal> copy4 = new StreamSimpleBase<>(
                new Signal(), new Signal(), new FakeProcessCopy<Signal>()
        );
        copy3.subscribe(copy4);

        copy4.subscribe(this);

        threadPool.addStream(copy1);
        threadPool.addStream(copy2);
        threadPool.addStream(copy3);
        threadPool.addStream(copy4);

        final int nbPush = 100;

        final ArrayList<Signal> signals = new ArrayList<>();
        signals.ensureCapacity(nbPush);

        for (int i = 0; i < nbPush; i++) {
            Signal s = new Signal();
            s.setLength(1);
            s.setValue(0, Math.random());
            s.setId(i);
            signals.add(s);
        }

        Thread tmpThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        while ( i < nbPush ) {
                            if(copy1.getInputState() == States.INPUT_WAITING) {
                                copy1.fillBufferIn(signals.get(i));
                                //threadPool.notifyWork();
                                //System.out.println("pushed " + i);
                                i++;

                            }
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        tmpThread.start();
        //threadPool.notifyWork();
        //while (threadPool.isWorking() == false);


        while (threadPool.isWorking()){ try{ Thread.sleep(200);}catch (Exception e){}};


        while(ends.size() < nbPush){
            while (threadPool.isWorking() )
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //threadPool.notifyWork();
            }
            //threadPool.notifyWork();
        }

        assertEquals(nbPush,ends.size());

        for (int i = 0; i < nbPush; i++) {
            Signal ref = signals.get(i) ;
            Signal comp = ends.get(String.valueOf(ref.getId()));
            assertEquals(ref.length(), comp.length());

            for (int j = 0; j < ref.length(); j++) {
                assertEquals(ref.getValue(j),comp.getValue(j),Double.MIN_VALUE);
            }
        }

    }

    Hashtable<String,Signal> ends = new Hashtable<>();
    int i =0;
    @Override
    public synchronized void fillBufferIn(Signal data) {
        Signal s = new Signal();
        data.copyTo(s);
        //System.out.print("id recu " + String.valueOf(data.getId()));
        ends.put(String.valueOf(data.getId()),s);
        //System.out.println("end" + ++i);
    }

    @Override
    public synchronized int getInputState() {
        return States.INPUT_WAITING;
    }
}
