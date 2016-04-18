package fr.enst.pact34.whistlepro.api2.test.main;

import org.junit.Test;

import fr.enst.pact34.whistlepro.api2.classification.ClassifProcess;
import fr.enst.pact34.whistlepro.api2.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api2.common.FileOperator;
import fr.enst.pact34.whistlepro.api2.common.PowerFilterProcess;
import fr.enst.pact34.whistlepro.api2.common.SpectrumProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.ClassifResults;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.dataTypes.Spectrum;
import fr.enst.pact34.whistlepro.api2.features.MfccProcess;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;
import fr.enst.pact34.whistlepro.api2.test.utils.StreamDataEnd;
import fr.enst.pact34.whistlepro.api2.test.utils.StreamDataPutter;
import fr.enst.pact34.whistlepro.api2.test.utils.TestUtils;

import static org.junit.Assert.assertEquals;

/**
 * Created by mms on 16/04/16.
 */
public class FullStreamTest {

    @Test
    public void fftMfccClassifclassifStreamTest()
    {
        String inputDataFile = "../testData/features/signal.data";
        Signal inputData = TestUtils.createSignalFromFile(inputDataFile);
        ClassifResults output = new ClassifResults();

        StreamDataPutter<Signal> start = new StreamDataPutter<>(inputData);
        StreamDataEnd<ClassifResults> end = new StreamDataEnd<>(output);

        //FFT
        StreamProcessInterface<Signal,Spectrum> fftProcess = new SpectrumProcess();
        StreamSimpleBase<Signal , Spectrum> fftStream = new StreamSimpleBase<>(new Signal(),new Spectrum(), fftProcess);

        //MFCC
        StreamProcessInterface<Spectrum,Signal> mfccProcess = new MfccProcess();
        StreamSimpleBase<Spectrum, Signal> mfccStream = new StreamSimpleBase<>(new Spectrum(),new Signal(), mfccProcess);


        //classif
        StreamProcessInterface<Signal, ClassifResults> classifProcess = new ClassifProcess(new MultipleStrongClassifiers.Builder()
                .fromString(FileOperator.getDataFromFile("../testData/classification/voyelles.scs"))
                .build());
        StreamSimpleBase<Signal, ClassifResults> classifStream = new StreamSimpleBase<>(new Signal(),new ClassifResults(), classifProcess);

        start.subscribe(fftStream);
        fftStream.subscribe(mfccStream);
        mfccStream.subscribe(classifStream);
        classifStream.subscribe(end);

        start.pushData();
        fftStream.process();
        fftStream.endProcess();
        fftStream.pushData();
        mfccStream.process();
        mfccStream.endProcess();
        mfccStream.pushData();
        classifStream.process();
        classifStream.endProcess();
        classifStream.pushData();

        assertEquals("a",output.getRecoClass());
    }


    @Test
    public void powerFilterfftMfccClassifclassifStreamTest()
    {
        String inputDataFile = "../testData/features/signal.data";
        Signal inputData = TestUtils.createSignalFromFile(inputDataFile);
        ClassifResults output = new ClassifResults();

        StreamDataPutter<Signal> start = new StreamDataPutter<>(inputData);
        StreamDataEnd<ClassifResults> end = new StreamDataEnd<>(output);

        //power filter
        StreamProcessInterface<Signal,Signal> powFilterProcess = new PowerFilterProcess();
        StreamSimpleBase<Signal , Signal> powerFilterStream = new StreamSimpleBase<>(new Signal(),new Signal(), powFilterProcess);

        //FFT
        StreamProcessInterface<Signal,Spectrum> fftProcess = new SpectrumProcess();
        StreamSimpleBase<Signal , Spectrum> fftStream = new StreamSimpleBase<>(new Signal(),new Spectrum(), fftProcess);

        //MFCC
        StreamProcessInterface<Spectrum,Signal> mfccProcess = new MfccProcess();
        StreamSimpleBase<Spectrum, Signal> mfccStream = new StreamSimpleBase<>(new Spectrum(),new Signal(), mfccProcess);


        //classif
        StreamProcessInterface<Signal, ClassifResults> classifProcess = new ClassifProcess(new MultipleStrongClassifiers.Builder()
                .fromString(FileOperator.getDataFromFile("../testData/classification/voyelles.scs"))
                .build());
        StreamSimpleBase<Signal, ClassifResults> classifStream = new StreamSimpleBase<>(new Signal(),new ClassifResults(), classifProcess);

        start.subscribe(powerFilterStream);
        powerFilterStream.subscribe(fftStream);
        fftStream.subscribe(mfccStream);
        mfccStream.subscribe(classifStream);
        classifStream.subscribe(end);

        start.pushData();
        powerFilterStream.process();
        powerFilterStream.endProcess();
        powerFilterStream.pushData();
        fftStream.process();
        fftStream.endProcess();
        fftStream.pushData();
        mfccStream.process();
        mfccStream.endProcess();
        mfccStream.pushData();
        classifStream.process();
        classifStream.endProcess();
        classifStream.pushData();

        assertEquals("a",output.getRecoClass());
    }

}
