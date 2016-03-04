package fr.enst.pact34.whistlepro.api.stream;

import java.util.ArrayList;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.DataStreamInterface;
import fr.enst.pact34.whistlepro.api.common.FactoryInterface;
import fr.enst.pact34.whistlepro.api.common.JobProviderInterface;


public class SynchronizedStream<E,F>
    implements
        DataSourceInterface<F>,
        DataListenerInterface<E>,
        JobProviderInterface
{


    public SynchronizeStream(DataStreamInterface<E,F> io) {

    }

    @Override
    public void onPushData(DataSource<E> source, ArrayList<E> inputData) {

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
