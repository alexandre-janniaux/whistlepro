package fr.enst.pact34.whistlepro.demo.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import fr.enst.pact34.whistlepro.demo.models.PianoRollModel;

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
    private float instrumentSpacing = 75.f;
    private PianoRollModel model;
    private double screenWidthInTime = 5.;
    private double cursorPosition = 0.;

    public PianoRollView(Context context) {
        super(context);
    }

    public PianoRollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PianoRollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    public PianoRollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    public void setModel(PianoRollModel model) {
        model.addViewNotifier(this);
        this.model = model;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();

        // choix de la couleur (à changer)
        paint.setColor(Color.RED);

        Log.d("whistlepro","[whistlepro] dessin du piano");

        int nbRow = this.model.getNoteTypeCount();
        if (this.model != null) {
            for (int i = 0; i < nbRow; ++i) {
                for (int j = 0; j < this.model.getNoteCount(i); ++j) {
                    PianoRollModel.NoteProperty note = this.model.getNote(i,j);
                    double start = note.getStart();
                    double stop = note.getStop();

                    float left = (float) ((start-cursorPosition)/screenWidthInTime*getWidth());
                    float right = (float) ((stop-cursorPosition)/screenWidthInTime*getWidth())
                    float top = i*instrumentSpacing;
                    float bottom = top + instrumentSpacing;
                    canvas.drawRect(left, top, right, bottom, paint);

                    //TODO: echelle des temps (équivalence temps/pixel)
                }
            }


        }


        // ON DESSINE A INTERVALLE REGULIER LES SEPARATEURS D'INSTRUMENTS
        //paint.setARGB(125,0,255,0);
        int nbLines = (int) (getHeight() / this.instrumentSpacing);
        float[] lines = new float[(nbLines+1)*4];

        // écriture des lignes horizontales // TODO: shift de la hauteur selon dragging (modulo)
        for(int i=0; i<Math.min(nbLines+1, nbRow+1); ++i) {
            lines[4*i]   = 0;                           // départ x
            lines[4*i+1] = this.instrumentSpacing*i;    // départ y
            lines[4*i+2] = getWidth();                  // arrivé x
            lines[4*i+3] = this.instrumentSpacing*i;    // arrivé y
        }

        canvas.drawLines(lines, paint);

        // TODO: dessin des notes pouvant apparaitre à l'écran

        // TODO: model-view


        super.onDraw(canvas);


        //canvas.drawLine(0,0,getWidth(),getHeight(),paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        final int action = event.getAction();

        switch(action) {
            case MotionEvent.ACTION_MOVE:
                break;

        }

        return true;
    }

    public void notifyView() {

    }

    public void notifyNoteView(int piste, int note) {

    }

}
