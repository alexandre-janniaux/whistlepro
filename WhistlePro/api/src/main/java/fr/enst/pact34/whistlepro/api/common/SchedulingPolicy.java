package fr.enst.pact34.whistlepro.api.common;

/**
 * Created by alexandre on 18/03/16.
 */
public class SchedulingPolicy implements SchedulingPolicyInterface {


    private States state;

    public void setState(States state) {
        this.state = state;
    }

    public enum States {WAITING_PROCESSING, PROCESSING, WAITING_OUTPUT, WAITING_DATA};

    public States getState() {
        return States.WAITING_DATA;
    }
}
