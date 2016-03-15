package main.java.fr.enst.pact34.whistlepro.api2.main;

import main.java.fr.enst.pact34.whistlepro.api2.DataTypes.*;
import main.java.fr.enst.pact34.whistlepro.api2.stream.StreamDestination;
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
    StreamDestination<Frequency> destFreqs = null;
    StreamDestination<AttackTimes> destAttak = null;
    StreamDestination<ClassifResults> destClassif = null;

    //transcription module
    TranscriptionBase transcriptionBase = null;

    //correction module
    CorrectionBase correctionBase = null;

    public ProccessingMachine() {

        //TODO initialisations

        //Set up connexions

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
