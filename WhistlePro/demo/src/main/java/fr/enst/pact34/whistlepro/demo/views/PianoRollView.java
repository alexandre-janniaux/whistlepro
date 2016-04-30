package fr.enst.pact34.whistlepro.demo.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;

/******************************
 * Dessin d'un piano roll:
 * ____________________________________________________
 * |Nom de l'instrument |        [====|===] [===] |
 * |----------------------------------------------------
 * |Nom de l'instrument |        [====|===] [===] |
 * |----------------------------------------------------
 * |......................idem..........................
 * _____________________________________________________
 *
 *
 * - | est une barre de séparation de mesure (placées à intervalles régulière, d'un trait
 *      léger
 * - [====] représente la note de l'instrument jouée pendant la durée correspondante
 * -       est du vide indiquant qu'il n'y a aucune note
 * - ----- ou _____ est une ligne horizontale à trait plus lourd que pour la barre de mesure
 *
 * EVENEMENTS:
 * - dragging: action d'appuyer sur l'écran, puis de bouger le doigt, ici
 *              utilisé afin de se déplacer sur le piano roll selon les axes vertical et horizontal
 *
 * - touching: action de toucher l'écran du doigt d'un coup rapide, ici utilisé selon plusieurs
 *              modes activables par l'utilisateur. Précision: ce n'est pas le piano-roll qui
 *              s'occupe de l'action en elle-même
 *
 ******************************/


public class PianoRollView extends View {

    private Rect rectView = new Rect();
    private float instrumentSpacing = 25.f;

    public PianoRollView(Context context) {
        super(context);
    }

    public PianoRollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PianoRollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PianoRollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();

        // ON DESSINE A INTERVALLE REGULIER LES SEPARATEURS D'INSTRUMENTS
        paint.setARGB(255,0,0,0);
        int nbLines = (int) (this.rectView.height() / this.instrumentSpacing);
        float[] lines = new float[nbLines*4];

        // écriture des lignes horizontales // TODO: shift de la hauteur selon dragging (modulo)
        for(int i=0; i<nbLines; ++i) {
            lines[i]   = 0;
            lines[i+1] = this.instrumentSpacing*i;
            lines[i+2] = this.rectView.width();
            lines[i+3] = this.instrumentSpacing*i;
        }

        canvas.drawLines(lines, paint);

        // TODO: dessin des notes pouvant apparaitre à l'écran

        // TODO: model-view

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onDragEvent(DragEvent event) {
        return super.onDragEvent(event);

    }
}
