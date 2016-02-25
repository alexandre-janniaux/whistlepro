package fr.enst.pact34.main;

import stream.*;

class MainApplication {

    public static void main(String[] args) {

        //////////////////////////////
        // STREAM INITIALIZATION
        //////////////////////////////
        AcquisitionStream acquisitionStream = new AcquisitionStream();
        SpectrumStream spectrumStream = new SpectrumStream();
        MfccFeatureStream mfccFeatureStream = new MfccFeatureStream();
        ClassificationStream classificationStream = new ClassificationStream();

        //////////////////////////////
        // STREAM INPUT-OUTPUT CONFIGURATION
        //////////////////////////////
        
        // ACQUISITION ---> SPECTRUM
        acquisitionStream.subscribe(spectrumStream);

        // SPECTRUM ---> MFCC
        spectrumStream.subscribe(mfccFeatureStream);

        // MFCC ---> CLASSIFICATION
        mfccFeatureStream.subscribe(classificationStream);

        //////////////////////////////
        // CLASSIFICATION FEATURES CONFIGURATION
        //////////////////////////////
        classificationStream.addFeatureProvider(mfccFeatureStream);

    }
}