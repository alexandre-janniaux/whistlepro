import stream.*;

class MainApplication {

    public static int main(String[] args) {
        AcquisitionStream acquisitionStream = new AcquisitionStream();
        SpectrumStream spectrumStream = new SpectrumStream();
        MfccFeatureStream mfccFeatureStream = new MfccFeatureStream();
        ClassificationStream classificationStream = new ClassificationStream();

        acquisitionStream.subscribe(spectrumStream);
        spectrumStream.subscribe(mfccFeatureStream);
        mfccFeatureStream.subscribe(classificationStream);
        classificationStream.addFeatureProvider(mfccFeatureStream);

        return 0;
    }
}
