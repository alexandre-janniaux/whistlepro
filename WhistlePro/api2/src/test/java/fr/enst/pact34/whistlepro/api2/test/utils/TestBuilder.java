package fr.enst.pact34.whistlepro.api2.test.utils;

import fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;

/**
 * Created by mms on 17/03/16.
 */
public class TestBuilder<E extends StreamDataInterface<E>,F extends StreamDataInterface<F>> {

        StreamDataPutter<E> start = null;

        StreamSimpleBase<E, F> streamToTest = null;

        StreamDataEnd<F> end = null;

        public TestBuilder(E input, F output, StreamSimpleBase<E,F> streamToTest ) {
                // setup
                start = new StreamDataPutter<>(input);

                end = new StreamDataEnd<>(output);

                this.streamToTest = streamToTest;

                start.subscribe(this.streamToTest);

                this.streamToTest.subscribe(end);

        }

        public void startTest()
        {
                start.pushData();

                while(streamToTest.isWorkAvailable())
                        streamToTest.doWork();
        }
}
