package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.acquisition.acquisition;
import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataSourceInterface;
import fr.enst.pact34.whistlepro.api.common.DataStreamInterface;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal;
import fr.enst.pact34.whistlepro.api.common.DoubleSignalInterface;

// TODO: documentation
public class AcquisitionStream
        implements DataSourceInterface<DoubleSignalInterface>
{
    private final Recorder recorder;
    private DataSource<DoubleSignalInterface> dataSource;

    Thread thread;

    private class Recorder implements Runnable{
        private final DataSource<DoubleSignalInterface> source;
        private final acquisition acq;
        private boolean isRunning = false;

        public Recorder(DataSource<DoubleSignalInterface> source) {
            this.source = source;
            this.acq = new acquisition();
        }


        @Override
        public void run() {
            while(this.isRunning) {
                double[] buffer =this.acq.readData();
                DoubleSignal signal = new DoubleSignal(
                        buffer,
                        buffer.length,
                        16000 // FIXME
                );


                this.source.push(signal); // TODO: parallelize
            }
        }

        public void start() {
            this.isRunning = true;
        }

        public void stop() {
            this.isRunning = false;
        }
    }

    public AcquisitionStream()
    {
        this.recorder = new Recorder(this.dataSource);
    }

    @Override
    public void subscribe(DataListenerInterface<DoubleSignalInterface> listener) {
        this.dataSource.subscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<DoubleSignalInterface> listener) {
        this.dataSource.unsubscribe(listener);
    }

    public void startRecording()  {
        assert(this.thread != null);

        this.recorder.start();
        this.thread = new Thread(new Recorder(this.dataSource));
        this.thread.start();
    }

    public void stopRecording() {
        this.recorder.stop();
        this.thread = null;
    }

    //TODO: implement recording [ACQUISITION]
    //
    //TODO: utiliser un jobs ? ou alors on peut simplement mettre l'enregistrement dans un nouveau thread, et un jobs qui envoie régulièrement les données dans le flux ?
    
}
