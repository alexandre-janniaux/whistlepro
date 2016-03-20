package fr.enst.pact34.whistlepro.api.stream;

import java.util.ArrayList;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.DataStreamInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2DInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignalInterface;
import fr.enst.pact34.whistlepro.api.common.FactoryInterface;
import fr.enst.pact34.whistlepro.api.common.JobProviderInterface;

/*
public class DoubleMultiplexerStream
    implements
        DataSourceInterface<DoubleSignal2DInterface>,
        DataListenerInterface<DoubleSignal2DInterface>
{
    private int n;
    private DataSource<ArrayList<DoubleSignal2DInterface>> datasource = new DataSource<>();

    private ArrayList<DataStreamInterface<DoubleSignalInterface,DoubleSignalInterface>> sources = new ArrayList<>();
    private ArrayList<FakeLoopStream> loops = new ArrayList<>();
    private ArrayList<ArrayList<Double>> outputs = new ArrayList<>();

    private int computed = 0;

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
        public void fillIn(DataSource<F> source, ArrayList<F> inputData) {
            this.root.outputs.set(this.index,inputData);
            this.root.computed += 1;
            if (this.root.computed == this.root.n) {
                ArrayList<ArrayList<F>> outputs = new ArrayList<>();
                for (int i=0; i < inputData.size(); ++i) {
                    ArrayList<F> array = new ArrayList<>();
                    outputs.set(i, array);
                    for (int j=0; j < this.root.n; ++j) {
                        array.add(this.root.outputs.get(j).get(i));
                    }
                }
                this.root.datasource.fillOut(outputs);
            }
        }

    }


    public MultiplexerStream(int n, FactoryInterface<DataStreamInterface<E,F>> source) {
        this.n = n;
        this.sources.ensureCapacity(n);
        this.loops.ensureCapacity(n);
        this.outputs.ensureCapacity(n);

        for (int i=0; i<n; ++i) {
            DataStreamInterface<E,F> input = source.create();
            FakeLoopStream loop = new FakeLoopStream(i, this);
            this.sources.add(input);
            this.loops.add(loop);
            loop.subscribe(input);
            input.subscribe(loop);
        }
    }

    @Override
    public void fillIn(DataSource<ArrayList<E>> source, ArrayList<ArrayList<E>> inputData) {
        assert(inputData.size() == this.n);

        for(int i=0; i<n; ++i) {
            ArrayList<E> data = inputData.get(i);
            this.loops.get(i).fillOut(data);
        }
    }
}
*/