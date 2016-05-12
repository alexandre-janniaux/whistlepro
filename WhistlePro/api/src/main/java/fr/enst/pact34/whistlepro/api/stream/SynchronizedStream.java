package fr.enst.pact34.whistlepro.api.stream;

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
        public void fillIn(DataSource<F> source, F inputData) {
            this.root.output.fillOut(inputData);
        }

    }

    public SynchronizedStream(DataStreamInterface<E,F> io) {
        assert(io != null);
        this.iostream = io;
    }

    @Override
    public void fillIn(DataSource<E> source, E inputData) {
        this.input.fillOut(inputData);//FIXME: bypass, need to implement synchronization
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
