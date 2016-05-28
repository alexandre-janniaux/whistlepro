package fr.enst.pact34.whistlepro.pcapp.interfaces.views;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JComponent;

import fr.enst.pact34.whistlepro.pcapp.interfaces.adapters.SpectrogramAdapterInterface;
import fr.enst.pact34.whistlepro.pcapp.interfaces.proxies.SpectrogramViewProxy;

public class SpectrogramView extends JComponent {

    private SpectrogramAdapterInterface spectrogramAdapter;
    private ArrayList<SpectrogramViewProxy> spectrogramViews = new ArrayList<>();
    private double[] step = new double[2];
    private double[] scroll = new double[2];
    private int[] axisOffset = new int[2];

    /**
     * TODO:
     * + synchronisation grille / taille des fenêtres glissantes
     * + nuances dans le proxy
     * + rendu des blocs
     */


    public SpectrogramView() {
        step[0] = 5;
        step[1] = 5;
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
        this.axisOffset[1] = 10;

        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());


        paintSpectrogram(g2d);
        paintGrid(g2d);
    }


    /*
     *  Fonction auxiliaire qui effectue le dessin du spectrogramme
     *  @param g l'outil Graphics2D fourni par swing, peut être obtenu en castant Graphics
     */

    protected void paintSpectrogram(Graphics2D g) {

        g.setColor(Color.RED);
        //g.setStroke(curveProxy.getStroke());

        int maxPoints = (int) (this.getWidth()/this.step[0]);

        int nbPoints = spectrogramAdapter.isFinite() ?
                Math.min(spectrogramAdapter.getNbWindows(), maxPoints) : maxPoints;

        System.out.println("Nb points "+nbPoints);


        for(int i=0; i<nbPoints-2; ++i) {
            for(int j=0; j<spectrogramAdapter.getNbFrequencies(); ++j) {
                double freq = spectrogramAdapter.getFrequency(i);
                double value = spectrogramAdapter.getValue(i, j);

                double xc = (i - this.scroll[0]) * this.step[0];
                double yc = getHeight() - (j - this.scroll[1]) * this.step[1];


                int red = Math.min(255, (int) (Math.log(1+value)*255*255*55));
                System.out.println("Value : " + red);

                Color color = new Color(red, 12, 12);
                g.setColor(color);

                AffineTransform transform = new AffineTransform();
                transform.translate(xc, yc);
                transform.scale(this.step[0], this.step[1]);
                g.setTransform(transform);
                g.fillRect(0, 0, 1, 1);
                g.setTransform(new AffineTransform());
                //g.fillRect((int) xc, (int) yc, 15, 15);
            }
        }
    }

    public void paintGrid(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);

        int nbLineVert = (int) (getWidth()/this.step[0]+2);
        int nbLineHoriz = (int) (getHeight()/this.step[1]+2);

        double shiftX = (int)(this.scroll[0] - (Math.floor(this.scroll[0]/this.step[0]))*this.step[0]);
        double shiftY = (int)(this.scroll[1] - (Math.floor(this.scroll[1]/this.step[1]))*this.step[1]);

        AffineTransform transform = new AffineTransform();
        transform.translate(0, this.scroll[1] - shiftY);
        g.setTransform(transform);

        for (int i=0; i<nbLineHoriz; ++i) {
            g.drawLine(0,0,getWidth(),0);
            transform.translate(0,this.step[1]);
            g.setTransform(transform);
        }

        transform = new AffineTransform();
        transform.translate(this.scroll[0] - shiftX, 0);
        g.setTransform(transform);

        for (int i=0; i<nbLineVert; ++i) {
            g.drawLine(0,0,0,getHeight());
            transform.translate(this.step[0],0);
            g.setTransform(transform);
        }
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
        xmax = Math.max(xmax, spectrogramAdapter.getNbWindows());
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


    public SpectrogramViewProxy setSpectrogram(SpectrogramAdapterInterface specData) {
        this.spectrogramAdapter = specData;
        repaint();
        SpectrogramViewProxy proxy = new SpectrogramViewProxy(); // TODO
        return proxy;
    }
}
