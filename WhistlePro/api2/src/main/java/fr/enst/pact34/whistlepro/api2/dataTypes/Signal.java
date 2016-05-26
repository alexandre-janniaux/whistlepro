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
        arraycopy(d, 0, this.datas, start, (start+len<this.datas.length)?len:(this.datas.length-start));
    }

    public void fromSignal(Signal source, int start_source, int start_dest, int len)
    {
        arraycopy(source.datas, start_source, this.datas, start_dest, (start_dest+len<this.datas.length)?len:(this.datas.length-start_dest));
    }

    @Override
    public void setValue(int i, double v) {
        datas[i] = v;
    }

    @Override
    public void setLength(int newLength) {
        if(newLength>length) {
            double[] newData = new double[newLength];
            arraycopy(this.datas,0,newData,0,length);
            length = newLength;
            datas = newData;
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
        signal.valid=this.valid;
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

    boolean valid = true;

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public void setValid(boolean v) {
        valid =v;
    }

    public void fillArray(double[] array ) {
        arraycopy(this.datas, 0, array, 0, (array.length<length)?array.length:length);
    }

    // set max to m
    public void maxToM(double M)
    {
        double max = 0;
        for (int i = 0; i < length; i++) {
            if(Math.abs(datas[i])>max) max = Math.abs(datas[i]);
        }
        if(max == 0) return;
        double ratio = M/max;// on ne met pas 1 par securite
        for (int i = 0; i < length; i++) {
            datas[i] = ratio*datas[i];
        }
    }
}
