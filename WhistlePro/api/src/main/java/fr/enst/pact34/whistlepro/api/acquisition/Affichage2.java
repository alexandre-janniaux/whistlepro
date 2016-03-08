import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

public class Affichage2 {
	
	public static void affichage(double[] x, String name) {
		
		double[] t = new double[x.length];
		
		for (int i = 0; i<x.length; i++) {
			t[i] = (double) i;
		}
		
		if (t.length == x.length) {
			Plot2DPanel plot = new Plot2DPanel();
			
			plot.addLinePlot("Plot", t, x);
			
			JFrame frame = new JFrame(name);
			frame.setContentPane(plot);
			frame.setVisible(true);
		}
		
		else {
			System.out.println("Erreur sur les dimensions des vecteurs");
		}
	}

}
