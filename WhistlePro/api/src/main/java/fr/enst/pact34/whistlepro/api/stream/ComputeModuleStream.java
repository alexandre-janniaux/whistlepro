package fr.enst.pact34.whistlepro.api.stream;

import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DataStreamInterface;
import fr.enst.pact34.whistlepro.api.common.OutputPolicyInterface;
import fr.enst.pact34.whistlepro.api.common.SchedulingPolicy;
import fr.enst.pact34.whistlepro.api.common.StreamProcessInterface;


public class ComputeModuleStream<E,F> implements DataStreamInterface<E,F> {

    private final DataSource<F> outputSource;
    private final StreamProcessInterface<E, F> computer;
    private final SchedulingPolicy schedule;
    private final OutputPolicyInterface<F> outputPolicy;
    private E inputData;
    private F outputData;

    public ComputeModuleStream(StreamProcessInterface<E,F> computer, SchedulingPolicy schedule, OutputPolicyInterface<F> outputPolicy) {
        this.outputSource = new DataSource<>();
        this.computer = computer;
        this.schedule = schedule;
        this.outputPolicy = outputPolicy;
    }

    @Override
    public void fillIn(DataSource<E> source, E inputData) {
        synchronized (this.schedule) {
            if (this.schedule.getState() != SchedulingPolicy.States.WAITING_DATA)
                throw new AssertionError();
            this.schedule.setState(SchedulingPolicy.States.WAITING_PROCESSING);
        }
        this.inputData = inputData;
    }

    public void process() {
        synchronized (this.schedule) {
            if (this.schedule.getState() != SchedulingPolicy.States.WAITING_PROCESSING)
                throw new AssertionError();
            this.schedule.setState(SchedulingPolicy.States.PROCESSING);
        }
        //this.outputData = this.outputPolicy.getOutput(); // TODO FIXME
        // FIXME Add methods aimed at computing the correct size ? or let the outputPolicy being explicitely filled in outside ?
        this.outputData = this.computer.process(inputData, this.outputPolicy);
        // TODO: allow a good scheduling policy to avoid module being blocked too much
        this.schedule.setState(SchedulingPolicy.States.WAITING_OUTPUT);
    }

    public void fillOut() {
        synchronized (this.schedule) {
            if (this.schedule.getState() != SchedulingPolicy.States.WAITING_OUTPUT)
                throw new AssertionError();
            this.schedule.setState(SchedulingPolicy.States.PROCESSING);
        }
            this.outputSource.fillOut(this.outputData);

        synchronized (this.schedule) {
            if (this.schedule.getState() != SchedulingPolicy.States.PROCESSING)
                throw new AssertionError();
                this.schedule.setState(SchedulingPolicy.States.WAITING_DATA);
        }

    }

    @Override
    public void subscribe(DataListenerInterface<F> listener) {
        this.outputSource.subscribe(listener);
    }

    @Override
    public void unsubscribe(DataListenerInterface<F> listener) {
        this.outputSource.unsubscribe(listener);
    }
}
