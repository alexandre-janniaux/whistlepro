package fr.enst.pact34.whistlepro.api.stream;

import java.util.ArrayList;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.FactoryInterface;
import fr.enst.pact34.whistlepro.api.common.JobProviderInterface;


public class MultiplexerStream<E,F>
    implements
        DataSourceInterface<ArrayList<F>>,
        DataListenerInterface<ArrayList<E>>,
        JobProviderInterface
{
    private int n;
    private DataSource<ArrayList<F>> datasource = new DataSource<>();

    private ArrayList<DataListenerInterface<E>> sources = new ArrayList<>();
    private ArrayList<FakeLoopStream> loops = new ArrayList<>();
    private ArrayList<ArrayList<F>> outputs = new ArrayList<>();

    @Override
    public void subscribe(DataListenerInterface<ArrayList<F>> listener) { this.datasource.subscribe(listener); }

    @Override
    public void unsubscribe(DataListenerInterface<ArrayList<F>> listener) { this.datasource.unsubscribe(listener);}

    private class FakeLoopStream extends DataSource<E> implements DataListenerInterface<F> {

        private int index;
        private MultiplexerStream<E,F> root;

        public FakeLoopStream(int index, MultiplexerStream<E,F> root) {
            this.index = index;
            this.root = root;
        }

        @Override
        public void onPushData(DataSource<F> source, ArrayList<F> inputData) {
            this.root.outputs.get(this.index) = inputData;
        }

    }


    public MultiplexerStream(int n, FactoryInterface<DataListenerInterface<E>> source) {
        this.n = n;
        this.sources.ensureCapacity(n);
        this.outputs.ensureCapacity(n);

        for (int i=0; i<n; ++i) {
            this.sources.add(source.create());
            //this.listeners.add(listener.create());
        }
    }

    @Override
    public void onPushData(DataSource<ArrayList<E>> source, ArrayList<ArrayList<E>> inputData) {
        assert(inputData.size() == this.n);

        for(int i=0; i<n; ++i) {
            ArrayList<E> data = inputData.get(i);
            this.sources.get(i).onPushData(null, data); // TODO: put a real datasource
        }
    }

    @Override
    public void onCommit(DataSource<ArrayList<E>> source) {

    }

    @Override
    public void onTransaction(DataSource<ArrayList<E>> source) {

    }

    @Override
    public boolean isWorkAvailable() {
        return false;
    }

    @Override
    public void doWork() {

    }
}
