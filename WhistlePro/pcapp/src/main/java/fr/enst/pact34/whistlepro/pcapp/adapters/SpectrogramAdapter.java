package fr.enst.pact34.whistlepro.pcapp.adapters;

import java.util.ArrayList;

import fr.enst.pact34.whistlepro.api2.dataTypes.Spectrum;
import fr.enst.pact34.whistlepro.api2.common.FFTCalculator;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.pcapp.interfaces.adapters.SpectrogramAdapterInterface;

import static java.lang.StrictMath.ceil;


public class SpectrogramAdapter implements SpectrogramAdapterInterface {

    private ArrayList<Spectrum> windows;
    private double samplingFrequency;
    private int windowSize;

    public SpectrogramAdapter(double samplingFrequency, int windowSize) {
        this.samplingFrequency = samplingFrequency;
        this.windowSize = windowSize;
        this.windows = new ArrayList<>();
    }

    public SpectrogramAdapter(double samplingFrequency, int windowSize, ArrayList<Spectrum> windows) {
        this.windows = new ArrayList<>();
        this.windows.addAll(windows);
        this.samplingFrequency = samplingFrequency;
        this.windowSize = windowSize;
    }

    @Override
    public boolean isFinite() {
        return true;
    }

    @Override
    public double getValue(int window, int freqNum) {
        if (getNbWindows() < window) throw new IndexOutOfBoundsException("Spectrum window doesn't exist");
        if (this.windows.get(window).getSpectrumValues().length < freqNum) throw new IndexOutOfBoundsException("Frequency doesn't exist");
        return this.windows.get(window).getSpectrumValues()[freqNum];
    }

    @Override
    public double getFrequency(int freqNum) {
        return freqNum*this.samplingFrequency/this.windowSize;
    }

    @Override
    public int getNbWindows() {
        return this.windows.size();
    }

    @Override
    public double getWindowLength() {
        return this.windowSize;
    }

    @Override
    public double getMaxAmplitude() {
        return 0;
    }

    public static SpectrogramAdapter createFromSignal(Signal signal, int windowSize, int overlap) {
        double freq = signal.getSamplingFrequency();
        int length = signal.length();
        int nbWindow = length / (windowSize-overlap);

        ArrayList<Spectrum> windows = new ArrayList<>();
        windows.ensureCapacity(nbWindow);

        FFTCalculator fft = new FFTCalculator(windowSize);

        for(int i=0; i<nbWindow; ++i) {
            int start = (windowSize-overlap)*i;
            int stop = start + windowSize-1;

            Spectrum spectrum = new Spectrum();
            fft.fft(signal, spectrum, start, stop);
            windows.add(spectrum);
        }


        SpectrogramAdapter adapter = new SpectrogramAdapter(freq, windowSize, windows);
        return adapter;
    }

    @Override
    public int getNbFrequencies() {
        return (int) ceil((this.windowSize + 1) / 2);
    }
}
