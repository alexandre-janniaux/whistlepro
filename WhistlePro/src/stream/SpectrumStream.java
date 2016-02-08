package stream;

import common.DataListenerInterface;
import common.DataSourceInterface;
import java.util.ArrayList;

public class SpectrumStream 
    extends DataSourceInterface<ArrayList<Double>> 
    implements DataListenerInterface<ArrayList<Double>> 
{

    @Override
    public void onPushData(ArrayList<Double> data) {
       // TODO: create jobs to compute spectrum
    }

}
