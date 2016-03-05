package fr.enst.pact34.whistlepro.api.classification.Learning;

import fr.enst.pact34.whistlepro.api.acquisition.WavFile;
import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.FileOperator;
import fr.enst.pact34.whistlepro.api.common.DataListenerInterface;
import fr.enst.pact34.whistlepro.api.common.Spectrum;
import fr.enst.pact34.whistlepro.api.common.transformers;
import fr.enst.pact34.whistlepro.api.stream.MfccFeatureStream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by  Mohamed on 27/02/16.
 */
public class MfccDataBaseGenerator {

    public static final String examplesFolder = "data/examples/";
    public static final String databaseFileName = "data/examples/MFCC_tmp.db";

    public static void main(String[] args) {

        MfccFeatureStream mfccFeatureStream = new MfccFeatureStream();
        FakeSpectrumStream spectrumStream = new FakeSpectrumStream();
        FakeReceiverMFCC fakeReceiverMFCC = new FakeReceiverMFCC();

        spectrumStream.subscribe(mfccFeatureStream);

        mfccFeatureStream.subscribe(fakeReceiverMFCC);

        //create clean file
        FileOperator.saveToFile(databaseFileName,"");

        ArrayList<String> classesToLearn = new ArrayList<>();
        classesToLearn.add("a");
        classesToLearn.add("e");
        classesToLearn.add("i");
        classesToLearn.add("o");
        classesToLearn.add("u");

        System.out.println("Starting generation of MFCC database ... ");

        long time = System.currentTimeMillis();

        for(String classe : classesToLearn) {

            System.out.println("Working on classe '"+classe+"' in the directory : '"+examplesFolder + classe+"/'");

            spectrumStream.prepareData(examplesFolder + classe+"/");
            while (mfccFeatureStream.isWorkAvailable()) mfccFeatureStream.doWork();
            fakeReceiverMFCC.save(classe);
        }

        System.out.println("End of generation of MFCC database.");
        System.out.println("Done in " + (System.currentTimeMillis()-time)/1000 + "s.");

        // transaction
        // push
        // source

    }

    public static class FakeReceiverMFCC implements DataListenerInterface<ArrayList<Double>>
    {

        private ArrayList<String> mfccs = new ArrayList<>();

        public void save(String classe)
        {
            String content = "";
            for (String a : mfccs
                 ) {
                content+= a+classe+";\r";
            }

            FileOperator.appendToFile(databaseFileName, content);
            System.out.println(mfccs.size()+"lines written in the file : "+databaseFileName);
            mfccs.clear();
        }

        @Override
        public void onPushData(DataSource<ArrayList<Double>> source, ArrayList<ArrayList<Double>> inputData) {
            //System.out.print("pushdata ");
            for(int j = 0; j < inputData.size(); j++) {
                ArrayList<Double> dd = inputData.get(j);
                //if(dd.size()!=13)continue;

                //System.out.println("=>" );
                String tmp = "";
                for (int i = 0; i < dd.size(); i++) {
                    tmp += dd.get(i)+";";
                    //System.out.print(dd.get(i)+";");

                }
                mfccs.add(tmp);
            }
        }

        @Override
        public void onCommit(DataSource<ArrayList<Double>> source) {
            //System.out.println("source");
        }

        @Override
        public void onTransaction(DataSource<ArrayList<Double>> source) {
            //System.out.println("transaction");

        }
    }

    public static  class FakeSpectrumStream extends DataSource<Spectrum>
    {

        public void prepareData(String folderName) {

            ArrayList<String> files = FileOperator.listFiles(folderName);

            int nbfilesOk = 0;

            for(int i = 0; i < files.size(); i++)
            {
                if(files.get(i).endsWith(".wav"))
                {
                    workOnWav(folderName+files.get(i));
                    nbfilesOk++;
                }
            }
            System.out.println(nbfilesOk+" files found");
        }

        private void workOnWav(String fileName)
        {

            try {
                WavFile readWavFile = WavFile.openWavFile(new File(fileName));
                double Fs = readWavFile.getSampleRate();
                double Ts = 1.0/readWavFile.getSampleRate();
                double Tps = 20e-3;
                double Tstart =20e-3;
                int nbPts = (int)(Tps/Ts);
                int nbPtsIgnore =(int)(Tstart/Ts);

                //reading ignore part
                double[] buffer = new double[nbPtsIgnore];
                int i;
                readWavFile.readFrames(buffer,nbPtsIgnore);

                //reading useful part
                buffer = new double[nbPts];


                ArrayList<Spectrum> sps = new ArrayList<>();

                while(readWavFile.readFrames(buffer,nbPts) == nbPts)
                {

                    double[] fft = transformers.fft(buffer);

                    for(i=0; i < fft.length; i++)
                    {
                        fft[i]=2*(fft[i]/fft.length);
                    }

                    sps.add(new Spectrum(fft.length,Fs,fft));

                }

                readWavFile.close();

                this.push(sps);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (WavFileException e) {
                e.printStackTrace();
            }


        }
    }

}