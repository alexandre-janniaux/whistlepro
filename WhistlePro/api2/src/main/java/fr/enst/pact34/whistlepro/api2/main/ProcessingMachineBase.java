package fr.enst.pact34.whistlepro.api2.main;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

import fr.enst.pact34.whistlepro.api2.attaque.AttackDetectorProcess;
import fr.enst.pact34.whistlepro.api2.classification.ClassifProcess;
import fr.enst.pact34.whistlepro.api2.classification.MultipleStrongClassifiers;
import fr.enst.pact34.whistlepro.api2.common.FreqProcess;
import fr.enst.pact34.whistlepro.api2.common.PowerFilterProcess;
import fr.enst.pact34.whistlepro.api2.common.SpectrumProcess;
import fr.enst.pact34.whistlepro.api2.common.SplitterProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.*;
import fr.enst.pact34.whistlepro.api2.features.MfccProcess;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeTranscription;
import fr.enst.pact34.whistlepro.api2.stream.*;
import fr.enst.pact34.whistlepro.api2.stream.StreamManagerListener;
import fr.enst.pact34.whistlepro.api2.transcription.PartialDataStreamDest;
import fr.enst.pact34.whistlepro.api2.transcription.TranscriptionBase;

/**
 * Created by mms on 15/03/16.
 */
public abstract class ProcessingMachineBase implements  ProcessorInterface {
    private static final double TIME_ANALYSE = 0.020;

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

    //threadpool
    private StreamManager streamMaster  ;

    private int last_nb_done = 0;

    private LinkedList<manageableStream> streamList = new LinkedList<>();

    public ProcessingMachineBase(double Fs, String classifierData, int nbThread) {
        int sampleLen = (int)(TIME_ANALYSE*Fs);
        //initialisations
        streamMaster = new StreamManager(nbThread, new StreamManagerListener() {
            @Override
            public void oneJobDone() {

                if(transcriptionEnded()) {
                    if(listener != null)listener.newWorkEvent(ProcessorEventListener.WorkEvent.AllWorkDone);
                }
                else
                {
                    int nb_done = transcriptionBase.getNbReceived();
                    if(last_nb_done < nb_done) {
                        if(listener != null)listener.newWorkEvent(ProcessorEventListener.WorkEvent.OneWorkDone);
                        last_nb_done = nb_done;
                    }
                } 
                endWaintings();
            }
        });

        //this.source = new StreamSourceInput<>(new double[dataPushedSize]);

        //split stream
        splitterProcess = new SplitterProcess(sampleLen, Fs);
        splitterStream = new StreamInputWraper<>(new Signal(), splitterProcess);
        streamList.add(splitterStream);

        //power filter
        powFilterProcess = new PowerFilterProcess();
        powerFilterStream = new StreamSimpleBase<>(new Signal(),new Signal(), powFilterProcess);
        streamList.add(powerFilterStream);

        //Estimation hauteur
        estFreqProcess =  new FreqProcess((int) Fs,sampleLen);
        estFreqStream = new StreamSimpleBase<>(new Signal(),new Frequency(), estFreqProcess);
        streamList.add(estFreqStream);

        //Attaque
        attackProcess = new AttackDetectorProcess(sampleLen);//new FakeProcessOutValue<>(new AttackTimes()); //TODO put real process
        attackStream = new StreamSimpleBase<>(new Signal(),new AttackTimes(), attackProcess);
        streamList.add(attackStream);

        //FFT
        fftProcess = new SpectrumProcess(sampleLen);
        fftStream = new StreamSimpleBase<>(new Signal(),new Spectrum(), fftProcess);
        streamList.add(fftStream);

        //MFCC
        mfccProcess = new MfccProcess();
        mfccStream = new StreamSimpleBase<>(new Spectrum(),new Signal(), mfccProcess);
        streamList.add(mfccStream);

        //classif
        classifier = new MultipleStrongClassifiers.Builder().fromString(classifierData).build();
        classifProcess = new ClassifProcess(classifier);
        classifStream = new StreamSimpleBase<>(new Signal(),new ClassifResults(), classifProcess);
        streamList.add(classifStream);

        //transcription module
        transcriptionBase = new FakeTranscription(new MusicTrack());


        //Ends of the stream
        destFreqs = transcriptionBase.getStreamDestBaseFreqs();
        destAttak = transcriptionBase.getStreamDestBaseAttak();
        destClassif = transcriptionBase.getStreamDestBaseClassif();

        //connexions                                                //            Audio
                                                                    //            ||
        //source.subscribe(splitterStream);                         //         Splitter =====================||
                                                                    //            ||                         ||
        splitterStream.subscribe(powerFilterStream);                //    ====PowerFilter ======||           ||
        splitterStream.subscribe(new StreamDataListenerInterface<Signal>() {
            @Override
            public void fillBufferIn(Signal data) {
                dataRecevied.incrementAndGet();
            }

            @Override
            public int getInputState() {
                return States.INPUT_WAITING;
            }
        });                                                         //   this     ||             ||          ||
                                                                    //            ||             ||          ||
        //powerFilterStream.subscribe(estFreqStream);               //            ||             ||        attack
        splitterStream.subscribe(attackStream);     //to add        //            ||           estFreq
        //powerFilterStream.subscribe(fftStream);                   //            FFT
                                                                    //            ||
        fftStream.subscribe(mfccStream);                            //           MFCC
                                                                    //            ||
        mfccStream.subscribe(classifStream);                        //          Classif =========||==========||
                                                                    //  |=====    ||             ||          ||     ======|
        estFreqStream.subscribe(destFreqs);                         //  ||        ||             ||       destFreqs      ||
        attackStream.subscribe(destAttak);                          //  ||        ||          destAttack                 ||   Transcription
        classifStream.subscribe(destClassif);                       //  ||     destClassif                               ||
                                                                    //  |=====                                      ======|

        //setup stream manager
        streamMaster.addStream(splitterStream);

        streamMaster.addStream(powerFilterStream);

        streamMaster.addStream(estFreqStream);

        streamMaster.addStream(attackStream);

        streamMaster.addStream(fftStream);

        streamMaster.addStream(mfccStream);

        streamMaster.addStream(classifStream);

    }


    private boolean transcriptionEnded()
    {
        if(processing==true) return false;
        return (transcriptionBase.getNbReceived() == dataRecevied.get())
                && splitterStream.hasWork() ==false ;
    }

    private AtomicLong dataRecevied = new AtomicLong(0);


    @Override
    public synchronized void pushData(double[] data) {
        if(processing) {
            splitterStream.fillBufferIn(data.clone());
            //TODO use memory pool
        }
    }

    ProcessorEventListener listener = null;

    public void setEventLister(ProcessorEventListener l)
    {
        listener = l;
    }

    private Semaphore waitSem = new Semaphore(0);
    public void waitEnd()
    {
        try {
            while (transcriptionEnded()==false && enableWaiting==true) {
                waitSem.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //temporaire
    public String getLastReco() {
        return transcriptionBase.getLastClassifElement();
    }

    public AttackTimes getLastAttack()
    {
        return transcriptionBase.getLastAttackElement();
    }
    private void clearData()
    {
        dataRecevied.set(0);
        last_nb_done = 0;
        transcriptionBase.clear();
        splitterStream.resetIds();
        for (manageableStream s:
             streamList) {
            s.reset();
        }
    }

    public void setupFor(Piste.TypePiste typePiste) {
        //clear
        clearData();
        //setup
        switch (typePiste)
        {
            case Melodie:
                powerFilterStream.subscribe(estFreqStream);
                powerFilterStream.unsubscribe(fftStream);
                break;
            case Percussions:
                powerFilterStream.unsubscribe(estFreqStream);
                powerFilterStream.subscribe(fftStream);
                break;
        }

        transcriptionBase.setupFor(typePiste);
    }

    private  boolean processing = false;
    private  boolean enableWaiting = false;

    protected void startProcessing() {
        clearData();
        enableWaiting = true;
        processing = true;
    }

    public List<AttackTimes> getAttacksList() {
        return transcriptionBase.getAttacksList();
    }

    public List<ClassifResults> getClassifList() {
        return transcriptionBase.getClassifList();
    }

    public List<Frequency> getFrequenciesList() {
        return transcriptionBase.getFrequenciesList();
    }

    public synchronized void stopProcessing() {
        processing = false;
        endWaintings();
    }

    private void endWaintings()
    {
        if(transcriptionEnded()) {
            enableWaiting = false;
            while(waitSem.hasQueuedThreads())
                waitSem.release();
        }
    }

    @Override
    public boolean isRecProcessing() {
        return enableWaiting;
    }

    @Override
    public boolean hasRecordedData() {
        return dataRecevied.get() > 0;
    }
}
