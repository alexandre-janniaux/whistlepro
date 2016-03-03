package fr.enst.pact34.whistlepro.api.common;

import fr.enst.pact34.whistlepro.api.common.ConvolutionInterface;
import fr.enst.pact34.whistlepro.api.common.Convolution1D;

public class TriangleConvolution
    implements ConvolutionInterface
{
    private Convolution1D filter;

    public TriangleConvolution(int start, int mid, int stop) {
        assert(start < mid);
        assert(mid < stop);
        int nbPoints = stop-start;
        double[] kernel = new double[nbPoints];

        double stepAscend = 1.0/(mid-start);
        double stepDescend = 1.0/(stop-mid);

        for(int i=0; i < nbPoints; ++i) 
        {
            if (i <= mid-start)
                kernel[i] = i*stepAscend;
            else
                kernel[i] = 1-(i-mid+start)*stepDescend;
        }
        filter = new Convolution1D(start, kernel);
    }

    public double[] convoluate(double[] signal, int start, int stop)
    {
        return this.filter.convoluate(signal, start, stop);
    }

}
