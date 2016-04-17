package  fr.enst.pact34.whistlepro.api2.dataTypes;

import  fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

import java.lang.reflect.Array;

import static java.lang.System.arraycopy;

/**
 * Created by  Mohamed on 14/03/16.
 */

public class Signal implements SignalGetInterface,SignalSetInterface,StreamDataInterface<Signal> {

    private int length = 100;
    private double[] datas = new double[length];
    private double Fs = 0;

    public void fromArray(double[] d)
    {
        setLength(d.length);
        arraycopy(d, 0, this.datas, 0, d.length);
    }

    public void fromArray(double[] d, int start, int len)
    {
        setLength(len);
        arraycopy(d, start, this.datas, 0, len);
    }

    @Override
    public void setValue(int i, double v) {
        datas[i] = v;
    }

    @Override
    public void setLength(int newLength) {
        if(newLength>length) {
            length = newLength;
            datas = new double[length];
        }
        length= newLength;
    }

    @Override
    public void setSamplingFrequency(double Fs) {
        this.Fs = Fs;
    }

    @Override
    public double getValue(int i) {
        return datas[i];
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public double getSamplingFrequency() {
        return Fs;
    }

    @Override
    public void copyTo(Signal signal) {

        signal.setSamplingFrequency(this.Fs);

        signal.setLength(this.length);

        arraycopy(this.datas, 0, signal.datas, 0, this.length);

        signal.id=this.id;
    }

    int id = -1;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Signal getNew() {
        // TODO
        Signal s = new Signal();
        return s;
    }
}
