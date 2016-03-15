package fr.enst.pact34.whistlepro.api2.main;

import fr.enst.pact34.whistlepro.api2.common.SpectrumProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.*;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeCorrection;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessCopy;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessOutValue;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeTranscription;
import fr.enst.pact34.whistlepro.api2.stream.DataListenerInterface;
import fr.enst.pact34.whistlepro.api2.stream.ProcessInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimple;
import fr.enst.pact34.whistlepro.api2.stream.StreamSource;
import fr.enst.pact34.whistlepro.api2.transcription.CorrectionBase;
import fr.enst.pact34.whistlepro.api2.transcription.TranscriptionBase;

/**
 * Created by mms on 15/03/16.
 */
public class ProccessingMachine {

    //Acquisition
    StreamSource<Signal> source = null;

    //power filter
    StreamSimple<Signal, Signal> powerFilterStream = null;

    //split stream
    StreamSimple<Signal, Signal> splitterStream = null;

    //Estimation hauteur
    StreamSimple<Signal, Frequency> estimationFreqStream = null;

    //Attaque
    StreamSimple<Signal, AttackTimes> attaqueStream = null;

    //FFT
    StreamSimple<Signal , Signal> fftStream = null;
    ProcessInterface<Signal,Signal> fftProcess = new SpectrumProcess();

    //MFCC
    StreamSimple<Signal, Signal> mfccStream = null;


    //classif
    StreamSimple<Signal, ClassifResults> classifStream = null;

    //Ends of the stream
    DataListenerInterface<ClassifResults> destFreqs = null;
    DataListenerInterface<AttackTimes> destAttak = null;
    DataListenerInterface<ClassifResults> destClassif = null;

    //transcription module
    TranscriptionBase transcriptionBase = null;

    //correction module
    CorrectionBase correctionBase = null;

    public ProccessingMachine(StreamSource<Signal> audioSignalSource) {

        //TODO initialisations

        this.source = audioSignalSource;

        powerFilterStream = new StreamSimple<>(new Signal(),new Signal(), new FakeProcessCopy<Signal>());

        //split stream
        splitterStream = new StreamSimple<>(new Signal(),new Signal(), new FakeProcessCopy<Signal>());

        //Estimation hauteur
        estimationFreqStream = new StreamSimple<>(new Signal(),new Frequency(), new FakeProcessOutValue<Signal,Frequency>(new Frequency()));

        //Attaque
        attaqueStream = new StreamSimple<>(new Signal(),new AttackTimes(), new FakeProcessOutValue<Signal,AttackTimes>(new AttackTimes()));

        //FFTp
        fftStream = new StreamSimple<>(new Signal(),new Signal(), fftProcess);

        //MFCC
        mfccStream = new StreamSimple<>(new Signal(),new Signal(), new FakeProcessCopy<Signal>());


        //classif
        classifStream = new StreamSimple<>(new Signal(),new ClassifResults(), new FakeProcessOutValue<Signal,ClassifResults>(new ClassifResults()));

        transcriptionBase = new FakeTranscription();
        correctionBase = new FakeCorrection();

        //Set up uconnexions

        source.subscribe(splitterStream);

        splitterStream.subscribe(powerFilterStream);

        powerFilterStream.subscribe(estimationFreqStream);
        powerFilterStream.subscribe(attaqueStream);
        powerFilterStream.subscribe(fftStream);

        fftStream.subscribe(mfccStream);

        mfccStream.subscribe(classifStream);

        estimationFreqStream.subscribe(destFreqs);
        attaqueStream.subscribe(destAttak);
        classifStream.subscribe(destClassif);

        transcriptionBase.subscribe(correctionBase);

    }
}
