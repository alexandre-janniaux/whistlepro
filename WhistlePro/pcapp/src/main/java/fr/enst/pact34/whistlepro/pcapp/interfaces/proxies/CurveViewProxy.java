package fr.enst.pact34.whistlepro.pcapp.interfaces.proxies;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

public class CurveViewProxy {

    private boolean computed=false;
    private float thickness;
    private Color color=Color.RED;
    private Stroke stroke= new BasicStroke();

    void setColor(Color color) {
        this.color = color;
        invalidate();
    }

    void setThickness(float thickness) {
        this.thickness = thickness;
        invalidate();
    }

    void invalidate() {
        this.computed = false;
    }

    public Color getColor() {
        return color;
    }

    public Stroke getStroke() {
        return stroke;
    }
}
