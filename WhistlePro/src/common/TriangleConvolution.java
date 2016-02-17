package common;

import common.ConvolutionInterface;
import common.Convolution1D;

class TriangleConvolution
    implements ConvolutionInterface
{
    private Convolution1D filter;

    public TriangleConvolution(int nbPoints, int shift) {

        double[] kernel = new double[nbPoints];
        int middle = (int) (nbPoint/2);

        for(int i=0; i < nbPoints; ++i) 
        {
            if (i <= middle)
                kernel[i] = i/middle;
            else
                kernel[i] = 2-i/middle;
        }
        filter = new Convolution1D(shift, kernel);
    }

}
