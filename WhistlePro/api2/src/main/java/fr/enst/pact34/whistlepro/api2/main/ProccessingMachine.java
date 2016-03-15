package main.java.fr.enst.pact34.whistlepro.api2.main;

import main.java.fr.enst.pact34.whistlepro.api2.dataTypes.*;
import main.java.fr.enst.pact34.whistlepro.api2.phantoms.FakeCorrection;
import main.java.fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessCopy;
import main.java.fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessOutValue;
import main.java.fr.enst.pact34.whistlepro.api2.phantoms.FakeTranscription;
import main.java.fr.enst.pact34.whistlepro.api2.stream.DataListenerInterface;
import main.java.fr.enst.pact34.whistlepro.api2.stream.StreamSimple;
import main.java.fr.enst.pact34.whistlepro.api2.stream.StreamSource;
import main.java.fr.enst.pact34.whistlepro.api2.transcription.CorrectionBase;
import main.java.fr.enst.pact34.whistlepro.api2.transcription.TranscriptionBase;

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

    //FFTp
    StreamSimple<Signal, Signal> fftStream = null;

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
        fftStream = new StreamSimple<>(new Signal(),new Signal(), new FakeProcessCopy<Signal>());

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
