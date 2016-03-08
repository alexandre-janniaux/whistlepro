import javax.swing.JFrame;
import java.util.*;

import org.math.plot.Plot2DPanel;

public class Affichage {

	public static void affichage(double[] y, String name){
		double[] x = new double[y.length];
		for(int i=0;i<y.length;i++)
		{
			x[i] = (double)i; 
		}

		if(x.length == y.length){
			Plot2DPanel plot = new Plot2DPanel();

			plot.addLinePlot("Plot",x,y);

			// put the PlotPanel in a JFrame, as a JPanel
			JFrame frame = new JFrame(name);
			frame.setContentPane(plot);
			frame.setVisible(true);
		}
		else{System.out.println("Erreur: dimension des vecteurs non valide !");}
	}
}


