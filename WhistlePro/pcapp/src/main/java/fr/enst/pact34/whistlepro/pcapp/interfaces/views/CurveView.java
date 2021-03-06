package fr.enst.pact34.whistlepro.pcapp.interfaces.views;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import fr.enst.pact34.whistlepro.pcapp.interfaces.proxies.CurveViewProxy;
import fr.enst.pact34.whistlepro.pcapp.interfaces.adapters.CurveAdapterInterface;

public class CurveView extends JComponent {

    private ArrayList<CurveAdapterInterface> curves = new ArrayList<>();
    private ArrayList<CurveViewProxy> curveViews = new ArrayList<>();
    private double[] step = new double[2];
    private int[] gridStep = new int[2];
    private double[] scroll = new double[2];
    private int[] axisOffset = new int[2];

    public CurveView() {

        setGridStep(10,10);
    }

    /*
     *  Effectue le rendu du widget, en particulier :
     *  + fond du widget
     *  + grille
     *  + axes
     *  + courbes
     */
    @Override
    protected void paintComponent(Graphics g) {

        // Mise à jour des paramètres de dessin
        this.axisOffset[0] = 10;
        this.axisOffset[1] = getHeight()/2;

        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.DARK_GRAY);

        int nbLineVert = getWidth()/this.gridStep[0]+1;
        int nbLineHoriz = getHeight()/this.gridStep[1]+1;

        if (this.gridStep[1] > 0)
        for (int i=0; i<nbLineHoriz; ++i) {
            int y = (int) (this.gridStep[1]*i) + (int)(this.scroll[1] - ((int)(this.scroll[1]/this.gridStep[1]))*this.gridStep[1]);
            g.drawLine(0,y,getWidth(),y);
        }

        if (this.gridStep[0] > 0)
        for (int i=0; i<nbLineVert; ++i) {
            int x = (int) (this.gridStep[0]*i);
            g.drawLine(x,0,x,getHeight());
        }

        //TODO: draw axis

        g.setColor(Color.orange);

        int xVertAxis = this.axisOffset[0];
        int yHorizAxis = this.axisOffset[1];

        System.out.println("x " + xVertAxis + " / y " + yHorizAxis);

        g.drawLine(xVertAxis, 0, xVertAxis, getHeight());
        g.drawLine(0, yHorizAxis, getWidth(), yHorizAxis);

        //g.drawLine() twice

        //TODO: test curves


        for(int i=0; i<curves.size(); ++i) {
            paintCurve(g2d, i);
            System.out.println("Redraw curve " + i);
        }
    }


    /*
     *  Fonction auxiliaire qui effectue le dessin d'une courbe
     *  @param g l'outil Graphics2D fourni par swing, peut être obtenu en castant Graphics
     *  @param curveId le numéro de la courbe à dessiner
     */

    protected void paintCurve(Graphics2D g, int curveId) {
        CurveAdapterInterface curveModel = this.curves.get(curveId);
        CurveViewProxy curveProxy = this.curveViews.get(curveId);
        Path2D.Double curveView = new Path2D.Double();

        g.setColor(Color.RED);
        g.setStroke(curveProxy.getStroke());

        int maxPoints = (int) (this.getWidth()/this.step[0]);

        int nbPoints = curveModel.isFinite() ?
                Math.min(curveModel.getNbPoints(), maxPoints) : maxPoints;

        System.out.println("dessin courve à points " + nbPoints);


        for(int i=0; i<nbPoints-2; ++i) {
            double x = curveModel.getAbscisse(i);
            double y = curveModel.getValue(i);

            double xc = (x-this.scroll[0])*this.step[0];
            double yc =  axisOffset[1] - (y-this.scroll[1])*this.step[1];

            if (i!=0) curveView.lineTo(xc, yc);
            else curveView.moveTo(xc,yc);
        }
        g.draw(curveView);
    }

    public CurveViewProxy addCurve(CurveAdapterInterface curve) {
        CurveViewProxy proxy = new CurveViewProxy();
        synchronized (curves) {
            this.curves.add(curve);
            this.curveViews.add(proxy);
        }
        repaint();

        return proxy;
    }

    public void setStepPoints(double x, double y) {
        this.step[0] = x;
        this.step[1] = y;
        repaint();
    }

    public void scroll(double x, double y) {
        this.scroll[0] += x;
        this.scroll[1] += y;
        repaint();
    }

    public void setScroll(double x, double y) {
        this.scroll[0] = x;
        this.scroll[1] = y;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        int xmax = 0, ymax=0;
        for (CurveAdapterInterface curve : this.curves) {
            xmax = Math.max(xmax, curve.getNbPoints());
            ymax = Math.max(ymax, curve.getMaxAmplitude());
        }
        return new Dimension((int)(xmax*this.step[0]), (int)(ymax*this.step[1]));
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(100, 100);
    }

    @Override
    public Dimension getMaximumSize() {
        return super.getMaximumSize();
    }


    public int[] getGridStep() {
        return gridStep;
    }

    public void setGridStep(int x, int y) {
        this.gridStep[0] = x;
        this.gridStep[1] = y;
        repaint();
    }

}
