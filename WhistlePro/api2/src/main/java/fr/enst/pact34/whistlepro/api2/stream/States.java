package fr.enst.pact34.whistlepro.api2.stream;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mms on 16/04/16.
 */
public class States {
        //enum States  {
    /*
    INPUT_BUSY, // input filled waiting to be used
    INPUT_WAITING, // input used waiting new input
    PROCESS_WAITING, // process waiting data to work
    PROCESS_BUSY, // process working on data
    PROCESS_DONE, // process waiting output to be ready
    OUTPUT_BUSY, // output filled waiting to be pushed
    OUTPUT_WAITING //output pushed waiting data from process
    /**/

    public static final int INPUT_BUSY = 0, // input filled waiting to be used
            INPUT_WAITING = 1, // input used waiting new input
            PROCESS_WAITING = 2, // process waiting data to work
            PROCESS_BUSY = 3, // process working on data
            PROCESS_DONE = 4, // process waiting output to be ready
            OUTPUT_BUSY = 5, // output filled waiting to be pushed
            OUTPUT_WAITING = 6; //output pushed waiting data from process
     //*/
}
