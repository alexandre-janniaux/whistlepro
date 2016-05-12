package fr.enst.pact34.whistlepro.api2.test.classification;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.classification.ClassifProcess;
import fr.enst.pact34.whistlepro.api2.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api2.common.FileOperator;
import fr.enst.pact34.whistlepro.api2.dataTypes.ClassifResults;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;
import fr.enst.pact34.whistlepro.api2.test.utils.TestBuilder;
import fr.enst.pact34.whistlepro.api2.test.utils.TestUtils;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 16/04/16.
 */
public class ClassifProcessTest {
    @Test
    public void test()
    {
        String inputDataFile = "../testData/features/mfcc.data";
        Signal inputData = TestUtils.createSignalFromFile(inputDataFile);
        ClassifResults outputData = new ClassifResults();

        // test setup
        ClassifProcess c = new ClassifProcess(new MultipleStrongClassifiers.Builder()
                .fromString(FileOperator.getDataFromFile("../testData/classification/voyelles.scs"))
                .build());
        TestBuilder<Signal,ClassifResults> test = new TestBuilder<>(inputData,outputData,
                new StreamSimpleBase<>(new Signal(), new ClassifResults(), c)
        );

        // test start
        test.startTest();

        //outputData verification

        assertEquals("a",outputData.getRecoClass());



    }
}
