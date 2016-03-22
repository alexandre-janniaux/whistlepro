package fr.enst.pact34.whistlepro.api2.main;

import fr.enst.pact34.whistlepro.api2.common.SpectrumProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.*;
import fr.enst.pact34.whistlepro.api2.features.MfccProcess;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeCorrection;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessCopy;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessOutValue;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeTranscription;
import fr.enst.pact34.whistlepro.api2.stream.*;
import fr.enst.pact34.whistlepro.api2.transcription.CorrectionBase;
import fr.enst.pact34.whistlepro.api2.transcription.TranscriptionBase;

/**
 * Created by mms on 15/03/16.
 */
public class ProccessingMachine {

    //Acquisition
    private StreamSourceBase<Signal> source = null;

    //power filter
    private StreamSimpleBase<Signal, Signal> powerFilterStream = null;

    //split stream
    private StreamSimpleBase<Signal, Signal> splitterStream = null;

    //Estimation hauteur
    private StreamSimpleBase<Signal, Frequency> estimationFreqStream = null;

    //Attaque
    private StreamSimpleBase<Signal, AttackTimes> attaqueStream = null;

    //FFT
    private StreamSimpleBase<Signal , Signal> fftStream = null;
    private StreamProcessInterface<Signal,Signal> fftProcess = new SpectrumProcess();

    //MFCC
    private StreamSimpleBase<Signal, Signal> mfccStream = null;
    private StreamProcessInterface<Signal,Signal> mfccProcess = new MfccProcess();


    //classif
    private StreamSimpleBase<Signal, ClassifResults> classifStream = null;

    //Ends of the stream
    private StreamDestBase<Frequency> destFreqs = null;
    private StreamDestBase<AttackTimes> destAttak = null;
    private StreamDestBase<ClassifResults> destClassif = null;

    //transcription module
    private TranscriptionBase transcriptionBase = null;

    //correction module
    private CorrectionBase correctionBase = null;

    public ProccessingMachine(StreamSourceBase<Signal> audioSignalSource) {

        //TODO initialisations

        this.source = audioSignalSource;

        powerFilterStream = new StreamSimpleBase<>(new Signal(),new Signal(), new FakeProcessCopy<Signal>());

        //split stream
        splitterStream = new StreamSimpleBase<>(new Signal(),new Signal(), new FakeProcessCopy<Signal>());

        //Estimation hauteur
        estimationFreqStream = new StreamSimpleBase<>(new Signal(),new Frequency(), new FakeProcessOutValue<Signal,Frequency>(new Frequency()));

        //Attaque
        attaqueStream = new StreamSimpleBase<>(new Signal(),new AttackTimes(), new FakeProcessOutValue<Signal,AttackTimes>(new AttackTimes()));

        //FFTp
        fftStream = new StreamSimpleBase<>(new Signal(),new Signal(), fftProcess);

        //MFCC
        mfccStream = new StreamSimpleBase<>(new Signal(),new Signal(), mfccProcess);


        //classif
        classifStream = new StreamSimpleBase<>(new Signal(),new ClassifResults(), new FakeProcessOutValue<Signal,ClassifResults>(new ClassifResults()));

        transcriptionBase = new FakeTranscription(new MusicTrack());
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
