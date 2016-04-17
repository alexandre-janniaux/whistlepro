package fr.enst.pact34.whistlepro.api2.test.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

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
public class streamTest implements StreamDataListenerInterface<Signal> {

    @Test
    public void streamTest()
    {
        final StreamManager threadPool = new StreamManager(2);

        final StreamSimpleBase<Signal,Signal> copy1 = new StreamSimpleBase<>(
                new Signal(),new Signal(), new FakeProcessCopy<Signal>()
        );

        StreamSimpleBase<Signal,Signal> copy2 = new StreamSimpleBase<>(
                new Signal(),new Signal(), new FakeProcessCopy<Signal>()
        );
        copy1.subscribe(copy2);
        StreamSimpleBase<Signal,Signal> copy3 = new StreamSimpleBase<>(
                new Signal(),new Signal(), new FakeProcessCopy<Signal>()
        );
        copy2.subscribe(copy3);
        StreamSimpleBase<Signal,Signal> copy4 = new StreamSimpleBase<>(
                new Signal(),new Signal(), new FakeProcessCopy<Signal>()
        );
        copy3.subscribe(copy4);

        copy4.subscribe(this);

        threadPool.addStream(copy1);
        threadPool.addStream(copy2);
        threadPool.addStream(copy3);
        threadPool.addStream(copy4);

        final int nbPush = 10;

        final ArrayList<Signal> signals = new ArrayList<>();
        signals.ensureCapacity(nbPush);

        for (int i = 0; i < nbPush; i++) {
            Signal s =new Signal();
            s.setLength(1);
            s.setValue(0,Math.random());
            signals.add(s);
        }

        Thread tmpThread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < nbPush; i++) {
                            while(copy1.getInputState() != States.INPUT_WAITING);
                            copy1.fillBufferIn(signals.get(i));
                            System.out.println("pushed");
                            threadPool.notifyWork();

                        }
                    }
                }
        );
        tmpThread.start();
        threadPool.notifyWork();

        while(threadPool.isWorking() || true)
        {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadPool.notifyWork();
        }

        assertEquals(nbPush,ends.size());

        for (int i = 0; i < nbPush; i++) {
            assertEquals(signals.get(i),ends.get((i+1)%nbPush));
        }

    }

    LinkedList<Signal> ends = new LinkedList<>();
    @Override
    public synchronized void fillBufferIn(Signal data) {
        ends.add(data);
        System.out.print("er");
    }

    @Override
    public States getInputState() {
        return States.INPUT_WAITING;
    }
}
