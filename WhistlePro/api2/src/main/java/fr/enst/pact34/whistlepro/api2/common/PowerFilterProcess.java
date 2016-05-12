package fr.enst.pact34.whistlepro.api2.common;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

/**
 * Created by mms on 17/04/16.
 */
public class PowerFilterProcess implements StreamProcessInterface<Signal,Signal> {

    private double max_audio = 0;

    @Override
    public void process(Signal inputData, Signal outputData) {

        double sum = 0;

        double tmp ;
        for(int i = 0; i < inputData.length(); i++)
        {
            tmp = Math.abs(inputData.getValue(i));
            sum+=tmp;
            if(tmp>max_audio) max_audio = tmp;
        }

        if( (max_audio > 0 && sum/inputData.length()<max_audio*0.05) || sum == 0) {
            outputData.setValid(false);
        }
        else
        {
            inputData.copyTo(outputData);
        }
    }

    @Override
    public void reset() {
        max_audio = 0;
    }
}
