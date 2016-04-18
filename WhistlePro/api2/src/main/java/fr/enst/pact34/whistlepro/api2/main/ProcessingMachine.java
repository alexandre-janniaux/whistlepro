package fr.enst.pact34.whistlepro.api2.main;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import fr.enst.pact34.whistlepro.api2.classification.ClassifProcess;
import fr.enst.pact34.whistlepro.api2.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api2.common.PowerFilterProcess;
import fr.enst.pact34.whistlepro.api2.common.SpectrumProcess;
import fr.enst.pact34.whistlepro.api2.common.SplitterProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.*;
import fr.enst.pact34.whistlepro.api2.features.MfccProcess;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeCorrection;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessOutValue;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeTranscription;
import fr.enst.pact34.whistlepro.api2.stream.*;
import fr.enst.pact34.whistlepro.api2.stream.StreamManagerListener;
import fr.enst.pact34.whistlepro.api2.transcription.CorrectionBase;
import fr.enst.pact34.whistlepro.api2.transcription.PartialDataStreamDest;
import fr.enst.pact34.whistlepro.api2.transcription.TranscriptionBase;

/**
 * Created by mms on 15/03/16.
 */
public class ProcessingMachine implements DoubleDataListener {
    private static final double TIME_ANALYSE = 0.020;

    //Acquisition filled by constructor
    //private StreamSourceInput<double[]> source = null;

    //split stream
    private StreamProcessInterface<LinkedList<double[]>,LinkedList<Signal>> splitterProcess ;
    private StreamInputWraper<double[], Signal> splitterStream ;

    //power filter
    private StreamProcessInterface<Signal,Signal> powFilterProcess ;
    private StreamSimpleBase<Signal, Signal> powerFilterStream ;


    //Estimation hauteur
    private StreamProcessInterface<Signal,Frequency> estFreqProcess ;
    private StreamSimpleBase<Signal, Frequency> estFreqStream ;

    //Attaque
    private StreamProcessInterface<Signal,AttackTimes> attackProcess ;
    private StreamSimpleBase<Signal, AttackTimes> attackStream ;

    //FFT
    private StreamProcessInterface<Signal,Spectrum> fftProcess ;
    private StreamSimpleBase<Signal , Spectrum> fftStream ;

    //MFCC
    private StreamProcessInterface<Spectrum,Signal> mfccProcess ;
    private StreamSimpleBase<Spectrum, Signal> mfccStream ;


    //classif
    private MultipleStrongClassifiers classifier ;
    private StreamProcessInterface<Signal, ClassifResults> classifProcess ;
    private StreamSimpleBase<Signal, ClassifResults> classifStream ;


    //transcription module
    private TranscriptionBase transcriptionBase ;


    //Ends of the stream
    private PartialDataStreamDest<Frequency> destFreqs ;
    private PartialDataStreamDest<AttackTimes> destAttak ;
    private PartialDataStreamDest<ClassifResults> destClassif ;

    //correction module
    private CorrectionBase correctionBase ;

    //threadpool
    StreamManager streamMaster  ;

    public ProcessingMachine(int dataPushedSize, double Fs, String classifierData, int nbThread) {

        //initialisations
        streamMaster = new StreamManager(nbThread, new StreamManagerListener() {
            @Override
            public void oneJobDone() {
                if(listener != null)
                {
                    if(transcriptionEnded()) {
                        listener.newWorkEvent(ProcessingMachineEventListener.WorkEvent.AllWorkDone);
                    }
                    else
                    {
                        listener.newWorkEvent(ProcessingMachineEventListener.WorkEvent.OneWorkDone);
                    }
                }
                if(transcriptionEnded()) {
                    while(waitSem.hasQueuedThreads())
                    waitSem.release();
                }
            }
        });

        //this.source = new StreamSourceInput<>(new double[dataPushedSize]);

        //split stream
        splitterProcess = new SplitterProcess((int)(TIME_ANALYSE*Fs), Fs);
        splitterStream = new StreamInputWraper<>(new Signal(), splitterProcess);


        //power filter
        powFilterProcess = new PowerFilterProcess();
        powerFilterStream = new StreamSimpleBase<>(new Signal(),new Signal(), powFilterProcess);


        //Estimation hauteur
        estFreqProcess = new FakeProcessOutValue<>(new Frequency()); //TODO put real process
        estFreqStream = new StreamSimpleBase<>(new Signal(),new Frequency(), estFreqProcess);

        //Attaque
        attackProcess = new FakeProcessOutValue<>(new AttackTimes()); //TODO put real process
         attackStream = new StreamSimpleBase<>(new Signal(),new AttackTimes(), attackProcess);

        //FFT
        fftProcess = new SpectrumProcess();
        fftStream = new StreamSimpleBase<>(new Signal(),new Spectrum(), fftProcess);

        //MFCC
        mfccProcess = new MfccProcess();
        mfccStream = new StreamSimpleBase<>(new Spectrum(),new Signal(), mfccProcess);


        //classif
        classifier = new MultipleStrongClassifiers.Builder().fromString(classifierData).build();
        classifProcess = new ClassifProcess(classifier);
        classifStream = new StreamSimpleBase<>(new Signal(),new ClassifResults(), classifProcess);


        //transcription module
        transcriptionBase = new FakeTranscription(new MusicTrack());


        //Ends of the stream
        destFreqs = transcriptionBase.getStreamDestBaseFreqs();
        destAttak = transcriptionBase.getStreamDestBaseAttak();
        destClassif = transcriptionBase.getStreamDestBaseClassif();

        //correction module
        correctionBase = new FakeCorrection();

        //connexions                                                //            Audio
                                                                    //            ||
        //source.subscribe(splitterStream);                         //         Splitter
                                                                    //            ||
        splitterStream.subscribe(powerFilterStream);                //    ====PowerFilter ======||==========||
        splitterStream.subscribe(new StreamDataListenerInterface<Signal>() {
            @Override
            public void fillBufferIn(Signal data) {
                dataRecevied++;
            }

            @Override
            public int getInputState() {
                return States.INPUT_WAITING;
            }
        });                                                         //   this     ||             ||          ||
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

        //setup stream manager
        streamMaster.addStream(splitterStream);

        streamMaster.addStream(powerFilterStream);

        streamMaster.addStream(estFreqStream);

        streamMaster.addStream(attackStream);

        streamMaster.addStream(fftStream);

        streamMaster.addStream(mfccStream);

        streamMaster.addStream(classifStream);

    }


    public boolean transcriptionEnded()
    {
        return (transcriptionBase.getNbReceived() == dataRecevied) && (dataRecevied > 0);
    }

    private int dataRecevied = 0;

    public void newSong()
    {
        dataRecevied = 0;
        transcriptionBase.clear();
    }



    @Override
    public void pushData(double[] data) {
        splitterStream.fillBufferIn(data.clone());
        //TODO use memory pool
    }

    ProcessingMachineEventListener listener = null;

    void setEventLister(ProcessingMachineEventListener l)
    {
        listener = l;
    }

    private Semaphore waitSem = new Semaphore(0);
    public void waitEnd()
    {
        try {
            waitSem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
