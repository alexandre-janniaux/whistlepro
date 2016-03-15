package main.java.fr.enst.pact34.whistlepro.api2.main;

import DataTypes.*;
import main.java.fr.enst.pact34.whistlepro.api2.stream.DoubleStreamListener;
import main.java.fr.enst.pact34.whistlepro.api2.stream.DoubleToSimpleStream;
import main.java.fr.enst.pact34.whistlepro.api2.stream.SimpleStream;
import main.java.fr.enst.pact34.whistlepro.api2.stream.SignalStreamSource;

/**
 * Created by mms on 15/03/16.
 */
public class ProccessingMachine {

    //Acquisition
    SignalStreamSource source = null;

    //power filter
    SimpleStream<Signal, Signal> powerFilterStream = null;

    //split stream
    SimpleStream<Signal, Signal> splitterStream = null;

    //Estimation hauteur
    SimpleStream<Signal, Frequency> estimationStream = null;

    //Attaque
    SimpleStream<Signal, AttackTimes> attaqueStream = null;

    //FFTp
    SimpleStream<Signal, Signal> fftStream = null;

    //MFCC
    SimpleStream<Signal, Signal> mfccStream = null;


    //classif
    SimpleStream<Signal, ClassifResults> classifStream = null;


    //resutlt merging attaque & hauteur
    DoubleToSimpleStream<Frequency,AttackTimes, FreqAttackPacket> freqAttackStream = null;

    DoubleStreamListener<ClassifResults,FreqAttackPacket> transcriptionReceiver = null;

    public ProccessingMachine() {

        source.subscribe(splitterStream);

        splitterStream.subscribe(powerFilterStream);

        powerFilterStream.subscribe(estimationStream);
        powerFilterStream.subscribe(attaqueStream);
        powerFilterStream.subscribe(fftStream);

        fftStream.subscribe(mfccStream);

        mfccStream.subscribe(classifStream);

        attaqueStream.subscribe(freqAttackStream.getListenerE());
        estimationStream.subscribe(freqAttackStream.getListenerF());

        classifStream.subscribe(transcriptionReceiver.getListenerE());
        freqAttackStream.subscribe(transcriptionReceiver.getListenerF());

    }
}
