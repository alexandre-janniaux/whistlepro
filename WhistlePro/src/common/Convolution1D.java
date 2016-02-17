package common;

import java.util.ArrayList;

import common.ConvolutionInterface;

public class Convolution1D//<E extends Number> 
    implements ConvolutionInterface
{

    private double[] kernel;
    private int shift;


    public Convolution1D(int shift, double[] kernel) 
    {
        this.kernel = kernel;
        this.shift = shift;
    }


    public double[] convoluate(double[] signal, int start, int stop) 
    {
        assert(stop <= signal.length);
        assert(start >= 0);

        double[] output = new double[signal.length]; // TODO : compute correct length

        for(int i=start; i < stop; ++i) {
            double v = 0;
            for(int j=0; j< kernel.length; ++j) {
                if (i-j+shift >= start && i-j+shift < stop)
                   v += kernel[j]*signal[i-j+shift]; // TODO: use shift too
            }
            output[i] = v;
        }

        return output;
    }
}
