package fr.enst.pact34.whistlepro.api2.main;

import java.util.LinkedList;

import fr.enst.pact34.whistlepro.api2.classification.ClassifProcess;
import fr.enst.pact34.whistlepro.api2.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api2.common.SpectrumProcess;
import fr.enst.pact34.whistlepro.api2.common.SplitterProcess;
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


    //TODO initialisations

    //Acquisition filled by constructor
    private StreamSourceInput<double[]> source = null;

    private int nbSample; //TODO fill values
    private double Fs;
    //split stream
    private StreamProcessInterface<LinkedList<double[]>,LinkedList<Signal>> splitterProcess = new SplitterProcess(nbSample, Fs);
    private StreamInputWraper<double[], Signal> splitterStream = new StreamInputWraper<>(new Signal(), splitterProcess);

    //power filter
    private StreamProcessInterface<Signal,Signal> powFilterProcess = new FakeProcessCopy<>(); //TODO put real process
    private StreamSimpleBase<Signal, Signal> powerFilterStream = new StreamSimpleBase<>(new Signal(),new Signal(), powFilterProcess);


    //Estimation hauteur
    private StreamProcessInterface<Signal,Frequency> estFreqProcess = new FakeProcessOutValue<>(new Frequency()); //TODO put real process
    private StreamSimpleBase<Signal, Frequency> estFreqStream = new StreamSimpleBase<>(new Signal(),new Frequency(), estFreqProcess);

    //Attaque
    private StreamProcessInterface<Signal,AttackTimes> attackProcess = new FakeProcessOutValue<>(new AttackTimes()); //TODO put real process
    private StreamSimpleBase<Signal, AttackTimes> attackStream = new StreamSimpleBase<>(new Signal(),new AttackTimes(), attackProcess);

    //FFT
    private StreamProcessInterface<Signal,Spectrum> fftProcess = new SpectrumProcess();
    private StreamSimpleBase<Signal , Spectrum> fftStream = new StreamSimpleBase<>(new Signal(),new Spectrum(), fftProcess);

    //MFCC
    private StreamProcessInterface<Spectrum,Signal> mfccProcess = new MfccProcess();
    private StreamSimpleBase<Spectrum, Signal> mfccStream = new StreamSimpleBase<>(new Spectrum(),new Signal(), mfccProcess);


    //classif
    //TODO find a way to initialise from string
    private MultipleStrongClassifiers classifier = new MultipleStrongClassifiers.Builder().fromString("").build();
    private StreamProcessInterface<Signal, ClassifResults> classifProcess = new ClassifProcess(classifier);
    private StreamSimpleBase<Signal, ClassifResults> classifStream = new StreamSimpleBase<>(new Signal(),new ClassifResults(), classifProcess);


    //transcription module
    private TranscriptionBase transcriptionBase = new FakeTranscription(new MusicTrack());


    //Ends of the stream
    private StreamDestBase<Frequency> destFreqs = transcriptionBase.getStreamDestBaseFreqs();
    private StreamDestBase<AttackTimes> destAttak = transcriptionBase.getStreamDestBaseAttak();
    private StreamDestBase<ClassifResults> destClassif = transcriptionBase.getStreamDestBaseClassif();

    //correction module
    private CorrectionBase correctionBase = new FakeCorrection();

    public ProccessingMachine(StreamSourceInput<double[]> audioSignalSource) {

        this.source = audioSignalSource;                            //           Audio
                                                                    //            ||
        source.subscribe(splitterStream);                           //         Splitter
                                                                    //            ||
        splitterStream.subscribe(powerFilterStream);                //         PowerFilter ======||==========||
                                                                    //            ||             ||          ||
        powerFilterStream.subscribe(estFreqStream);                 //            ||             ||        estFreq
        powerFilterStream.subscribe(attackStream);                  //            ||           attack
        powerFilterStream.subscribe(fftStream);                     //            FFT
                                                                    //            ||
        fftStream.subscribe(mfccStream);                            //           MFCC
                                                                    //            ||
        mfccStream.subscribe(classifStream);                        //          Classif =========||==========||
                                                                    //  |=====    ||             ||          ||     ======|
        estFreqStream.subscribe(destFreqs);                         //  ||        ||             ||       destFreqs      ||
        attackStream.subscribe(destAttak);                          //  ||        ||          destAttack                 ||   Transcription
        classifStream.subscribe(destClassif);                       //  ||     destClassif                               ||         ||
                                                                    //  |=====                                      ======|         ||
        transcriptionBase.subscribe(correctionBase);                //                                                          Correction

    }
}
