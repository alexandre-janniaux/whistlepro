package fr.enst.pact34.whistlepro.classifUtils.classification.Learning;

import fr.enst.pact34.whistlepro.api.acquisition.WavFile;
import fr.enst.pact34.whistlepro.api.acquisition.WavFileException;
import fr.enst.pact34.whistlepro.api.common.DataSource;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2D;
import fr.enst.pact34.whistlepro.api.common.DoubleSignal2DInterface;
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
            fakeReceiverMFCC.save(classe);
        }

        System.out.println("End of generation of MFCC database.");
        System.out.println("Done in " + (System.currentTimeMillis()-time)/1000 + "s.");

        // transaction
        // fillOut
        // source

    }

    public static class FakeReceiverMFCC implements DataListenerInterface<DoubleSignal2DInterface>
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
        public void fillIn(DataSource<DoubleSignal2DInterface> source, DoubleSignal2DInterface inputData) {
            double[][] signal = inputData.getSignal();

            for(int j = 0; j < signal.length; j++) {
                double[] dd = signal[j];
                //if(dd.size()!=13)continue;

                //System.out.println("=>" );
                String tmp = "";
                for (int i = 0; i < dd.length; i++) {
                    tmp += dd[i]+";";
                    //System.out.print(dd.get(i)+";");

                }
                mfccs.add(tmp);
            }
        }

    }

    public static  class FakeSpectrumStream extends DataSource<DoubleSignal2DInterface>
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

                    sps.add(new Spectrum(nbPts,Fs,fft));

                }

                readWavFile.close();

                double[][] output = new double[sps.size()][];
                for(int k=0; k< output.length; ++k) {
                    output[k] = sps.get(k).getSpectrumValues();
                }

                DoubleSignal2DInterface outputData = new DoubleSignal2D(
                        output,
                        nbPts,
                        readWavFile.getSampleRate()
                );


                this.fillOut(outputData);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (WavFileException e) {
                e.printStackTrace();
            }


        }
    }



}