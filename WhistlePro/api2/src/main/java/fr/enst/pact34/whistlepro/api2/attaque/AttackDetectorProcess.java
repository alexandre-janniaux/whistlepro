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

		max = -Double.MAX_VALUE;
		min = Double.MAX_VALUE;
		last_v = 0;
	}

	//extraction de l'enveloppe par la methode du reservoir d'energie
	public void process(Signal input, AttackTimes attackTimes) {

		input.fillArray(x);



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

		double up_threshold = min+max*0.1;
		double down_threshold = min+max*0.05;
		double tps = ((double)input.length())/(input.getSamplingFrequency()*n);
		for(int i = 0; i < e.length; i ++) {
			if(last_v==0)
			{
				if (e[i] > up_threshold)
				{
					e[i] = 1;
					//new up
					attackTimes.addUp(i*tps);
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
					// new down
					attackTimes.addDown(i*tps);
				}
				else
				{
					e[i] = 1;
				}
			}

			last_v = (int) e[i];
		}



	}

}
