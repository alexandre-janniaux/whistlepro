package fr.enst.pact34.whistlepro.api2.attaque;


import fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.stream.StreamProcessInterface;

public class AttackDetectorProcess implements StreamProcessInterface<Signal,AttackTimes> {

	int n ;
	int nLen ;
	double[] x ;
	double[] e ;

	double max ;
	double min ;
	int last_v ;

	public AttackDetectorProcess(int inputLength)
	{
		n = 20;
		x = new double[inputLength];
		nLen = inputLength/n;
		e = new double[n];

		max = Double.MIN_VALUE;
		min = Double.MAX_VALUE;
		//max = 0.5;
		//min = 0.5;
		last_v = 0;
	}

	//extraction de l'enveloppe par la methode du reservoir d'energie
	public void process(Signal input, AttackTimes attackTimes) {

		input.fillArray(x);
		attackTimes.clear();

		// moyenne sur n intervalles ~~ sous echantillonnage
		for(int i = 0; i < n; i ++)
		{
			double sum = 0;
			for (int j = 0; j < nLen; j++) {
				sum += Math.abs(x[i*nLen+j]);
			}
			e[i] = sum/nLen;
			if(e[i]>max) max = e[i];
			if(e[i]<min) min = e[i];
		}

		if(min>max) return;

		int last_v_tmp = last_v;
		double up_threshold = min+(max-min)*0.2;
		double down_threshold = min+(max-min)*0.02;
		double tps = ((double)input.length())/(input.getSamplingFrequency()*n);
		for(int i = 0; i < e.length; i ++) {
			if(last_v==0)
			{
				if (e[i] > up_threshold)
				{
					e[i] = 1;
				}
				else
				{
					e[i] = 0;
				}
			}
			else // last_v == 1
			{
				if (e[i] < down_threshold)
				{
					e[i] = 0;
				}
				else
				{
					e[i] = 1;
				}
			}

			last_v = (int) e[i];
		}

		//corrections
		for (int i = 1; i < e.length -1; i++) {
			if(e[i] != e[i-1] && e[i-1]==e[i+1]) e[i]=e[i-1];
		}

		for (int i = 2; i < e.length -2; i++) {
			if((e[i] != e[i-1] || e[i] != e[i+1])
					&& e[i-2]==e[i+2]) {
				e[i-1]=e[i-2];
				e[i]=e[i-2];
				e[i+1]=e[i-2];
			}
		}

		//test attaque
		if(last_v_tmp == 0 && e[0] == 1)
		{
			attackTimes.addUp(0);
		}
		else if(last_v_tmp == 1 && e[0] == 0)
		{
			attackTimes.addDown(0);
		}
		for(int i = 1; i < e.length-1; i ++) {
			if(e[i-1] == 0 && e[i] == 1)
			{
				attackTimes.addUp(i*tps);
			}
			else if(e[i-1] == 1 && e[i] == 0)
			{
				attackTimes.addDown(i*tps);
			}
		}



		last_v = (int) e[e.length/2-1];

		}

	@Override
	public void reset() {
		max = Double.MIN_VALUE;
		min = Double.MAX_VALUE;
		//max = 0.5;
		//min = 0.5;
		last_v = 0;
	}

}
