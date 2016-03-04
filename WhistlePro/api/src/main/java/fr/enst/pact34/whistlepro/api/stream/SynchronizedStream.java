package fr.enst.pact34.whistlepro.api.stream;

import java.util.ArrayList;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.DataStreamInterface;
import fr.enst.pact34.whistlepro.api.common.JobProviderInterface;


public class SynchronizedStream<E,F>
    implements
        DataSourceInterface<F>,
        DataListenerInterface<E>,
        JobProviderInterface
{
    private DataSource<F> output;
    private FakeInternStream input;
    private DataStreamInterface<E,F> iostream;

    private class FakeInternStream extends DataSource<E> implements DataListenerInterface<F> {

        private SynchronizedStream<E,F> root;

        public FakeInternStream(SynchronizedStream<E,F> root) {
            this.root = root;
        }

        @Override
        public void onPushData(DataSource<F> source, ArrayList<F> inputData) {
            this.root.output.push(inputData);
        }

    }

    public SynchronizedStream(DataStreamInterface<E,F> io) {
        assert(io != null);
        this.iostream = io;
    }

    @Override
    public void onPushData(DataSource<E> source, ArrayList<E> inputData) {
        this.input.push(inputData);
    }

    @Override
    public void subscribe(DataListenerInterface<F> listener) {
        output.subscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<F> listener) {
        output.unsubscribe(listener);
    }

    @Override
    public boolean isWorkAvailable() {
        return false;
    }

    @Override
    public void doWork() {

    }
}
