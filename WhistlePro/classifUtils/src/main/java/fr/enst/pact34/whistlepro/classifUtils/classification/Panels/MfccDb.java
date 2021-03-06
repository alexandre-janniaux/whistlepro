package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;


import fr.enst.pact34.whistlepro.api.acquisition.WavFile;
import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;
import fr.enst.pact34.whistlepro.api.stream.MfccFeatureStream;
import fr.enst.pact34.whistlepro.api2.dataTypes.Spectrum;
import fr.enst.pact34.whistlepro.api2.common.PowerFilterProcess;
import fr.enst.pact34.whistlepro.api2.common.SpectrumProcess;
import fr.enst.pact34.whistlepro.api2.common.SplitterProcess;
import fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.features.MfccProcess;
import fr.enst.pact34.whistlepro.api2.phantoms.FakeProcessOutValue;
import fr.enst.pact34.whistlepro.api2.stream.States;
import fr.enst.pact34.whistlepro.api2.stream.StreamDataListenerInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamInputWraper;
import fr.enst.pact34.whistlepro.api2.stream.StreamManager;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;
import fr.enst.pact34.whistlepro.api2.stream.StreamSimpleBase;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mms on 03/04/16.
 */
public class MfccDb {

    public ElementInterface[] getElementsList(String classe) {
        ArrayList<MfccElement> elements = listeClasse.get(classe);

        ElementInterface[] i = new ElementInterface[elements.size()];
        elements.toArray(i);
        return i;
    }

    public void remove(String classe,String s) {
            for (MfccElement me : listeClasse.get(classe)) {
                if(me.name.equals(s))
                {
                    listeClasse.get(classe).remove(me);
                    return;
                }
            }
    }

    public interface ElementInterface
    {
        String getName();
        ArrayList<String> getMfccs();
    }

    private static class MfccElement implements ElementInterface
    {
        String path;
        String name;
        ArrayList<String> mfccs;
        boolean valid = false;

        @Override
        public String getName() {
            return name;
        }

        @Override
        public ArrayList<String> getMfccs() {
            return mfccs;
        }
    }

    private String expression = "< class='CLASS' name='NAME' mfcc='MFCCS'>";

    public void calculateMfccs()
    {
        for (Map.Entry<String,ArrayList<MfccElement>> e: listeClasse.entrySet()) {
            for (MfccElement me : e.getValue()) {
                if(me.valid==false)
                {
                    System.out.println(me.path);
                    me.mfccs = calcMfccOnFile(me.path);
                    me.valid = true;
                }
            }
        }
    }

    public String saveString() {
        calculateMfccs();
        String saveStr = "";
        for (Map.Entry<String,ArrayList<MfccElement>> e: listeClasse.entrySet()) {
            String classe = e.getKey();
            for (MfccElement me: e.getValue()) {
                String tmp = "";
                for (String mfcc: me.mfccs
                     ) {
                    tmp += mfcc + "/";
                }
                saveStr = saveStr +
                        expression.replace("CLASS",classe)
                                    .replace("NAME",me.name)
                                    .replace("MFCCS", tmp);
            }
        }
        return saveStr;
    }

    public void fromString(String str)
    {
        Pattern pattern = Pattern.compile(
                expression.replace("CLASS", "[0-9a-zA-Z._-]*")
                        .replace("NAME", "[0-9a-zA-Z._-]*")
                        .replace("MFCCS", ".*?"),
                Pattern.DOTALL);
        Matcher matcher = pattern.matcher(str);

        Pattern patternClass = Pattern.compile("class='[0-9a-zA-Z._-]*'");
        Pattern patternFilename = Pattern.compile("name='[0-9a-zA-Z._-]*'");

        listeClasse.clear();
        int  i = 0;

        while (matcher.find())
        {
        String tmp = matcher.group();
        System.out.println(i++ + tmp);
        Matcher matcherClass = patternClass.matcher(tmp);
        if(matcherClass.find())
        {
                    Matcher matcherFilename = patternFilename.matcher(tmp);
                    if(matcherFilename.find()) {
                        String classe = matcherClass.group();
                        String filename = matcherFilename.group();
                        classe=classe.replace("class='","");
                        classe=classe.replace("'","");
                        filename=filename.replace("name='","");
                        filename=filename.replace("'","");

                        String mfcc = tmp.substring(tmp.indexOf("mfcc='"),tmp.lastIndexOf("'"));
                        mfcc=mfcc.replace("mfcc='","");
                        mfcc=mfcc.replace("'","");
                        ArrayList<String> mfccs = new ArrayList<>();
                        for (String s: mfcc.split("/")) {
                            mfccs.add(s);
                        }

                        if(listeClasse.containsKey(classe)==false)
                            addClass(classe);
                        addSample(classe,filename,mfccs,true);

                    }

            }

        }

    }


    Hashtable<String,ArrayList<MfccElement>> listeClasse = new Hashtable<>();

    private String[] classes = new String[1];

    public String[] getClassesList()
    {
        if(listeClasse.size()!=classes.length)
        {
            classes = Arrays.copyOf(classes,listeClasse.size());
        }
        listeClasse.keySet().toArray(classes);
        return classes;
    }

    public boolean addClass(String classe) {

        if(listeClasse.containsKey(classe))
            return false;

        listeClasse.put(classe,new ArrayList<MfccElement>());
        return true;
    }

    public boolean contains(String classe) {
        return listeClasse.containsKey(classe);
    }

    private String addSample(String classe, String path, ArrayList<String> mfcc, boolean b)
    {
        MfccElement e = new MfccElement();
        e.path = path;
        e.mfccs = mfcc;
        e.name = path.substring(path.lastIndexOf("/")+1);
        e.valid = b;

        String name = e.name;

        int nb = 0;
        boolean continuee = true;
        int i = 0;
        ArrayList<MfccElement> l = listeClasse.get(classe);

        while(continuee) {
            continuee =false;
            for (i = 0 ; i < l.size() ; i++ ) {
                if(l.get(i).name.equals(name))
                {
                    nb++;
                    name = e.name +"_"+nb;
                    continuee =true;
                    break;
                }
            }

        }

        e.name=name;
        listeClasse.get(classe).add(e);

        return e.name;
    }

    public String addSample(String classe, String path) {
        return addSample(classe,path,null,false);
    }

    double Fs = 16000;
    double Ts = 1.0/Fs;
    double Tps = 20e-3;
    double Tstart =20e-3;
    int nbPts = (int)(Tps/Ts);
    int nbPtsIgnore =(int)(Tstart/Ts);

    //split stream
    StreamProcessInterface<LinkedList<double[]>,LinkedList<Signal>> splitterProcess = new SplitterProcess(nbPts,Fs);//(int) (TIME_ANALYSE * Fs), Fs);
    StreamInputWraper<double[], Signal> splitterStream = new StreamInputWraper<>(new Signal(), splitterProcess);


    //power filter
    PowerFilterProcess powFilterProcess = new PowerFilterProcess();
    StreamSimpleBase<Signal, Signal> powerFilterStream = new StreamSimpleBase<>(new Signal(), new Signal(), powFilterProcess);

    //FFT
    SpectrumProcess fftProcess = new SpectrumProcess(nbPts);
    StreamSimpleBase<Signal, Spectrum> fftStream = new StreamSimpleBase<>(new Signal(), new Spectrum(), fftProcess);

    //MFCC
    MfccProcess mfccProcess = new MfccProcess();
    StreamSimpleBase<Spectrum,Signal> mfccStream = new StreamSimpleBase<>(new Spectrum(),new Signal(), mfccProcess);

    StreamManager sm = new StreamManager(2,null);
    public MfccDb()
    {
        splitterStream.subscribe(powerFilterStream);
        powerFilterStream.subscribe(fftStream);
        fftStream.subscribe(mfccStream);

        sm.addStream(splitterStream);
        sm.addStream(powerFilterStream);
        sm.addStream(fftStream);
        sm.addStream(mfccStream);
    }
    public ArrayList<String> calcMfccOnFile(String file)
    {
        //MfccFeatureProvider mfcc = new MfccFeatureProvider();
        ArrayList<String> str = new ArrayList<>();
        try {

            WavFile readWavFile = WavFile.openWavFile(new File(file));


            //reading ignore part
            double[] buffer = new double[nbPtsIgnore];
            readWavFile.readFrames(buffer, nbPtsIgnore);

            //reading useful part
            buffer = new double[(int) readWavFile.getNumFrames()];

            readWavFile.readFrames(buffer, buffer.length);

            System.gc();

            final LinkedList<double[]> mfccs = new LinkedList<>();

            mfccStream.subscribe(new StreamDataListenerInterface<Signal>() {
                @Override
                public void fillBufferIn(Signal data) {
                    if (data.isValid() == false) return;
                    double[] in = new double[data.length()];
                    data.fillArray(in);
                    mfccs.add(in);
                }

                @Override
                public int getInputState() {
                    return States.INPUT_WAITING;
                }
            });


            splitterStream.fillBufferIn(buffer);


            while (sm.isWorking()){ try{ Thread.sleep(200);}catch (Exception e){}};


            for (double[] coef: mfccs
                    ) {

                String tmp = "";
                for (int i = 0; i < coef.length; i++) {
                    tmp += coef[i]+";";
                }

                str.add(tmp);
            }

        } catch (WavFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }
}
