package fr.enst.pact34.whistlepro.app.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;

public class GenericPianoRollView extends View {

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

    class GenericNoteProperty {
        double start;
        double stop;
        int line;

        public GenericNoteProperty(double start, double stop, int line) {
            this.start = start;
            this.stop = stop;
            this.line = line;
        }
    }

    public float nameColWidth = 300.f;
    public float instrumentSpacing = 100.f;
    public double screenWidthInTime = 20.;
    public double cursorPosition = 0.;
    public float verticalShift=0.f;
    public float verticalSpacingBetweenLines = 20.f;

    private float lastX, lastY;

    private long lastNoteId=0;

    private int nbRow = 0;

    public double minShift=0., maxShift=0.;


    private HashMap<Long, GenericNoteProperty> noteSet = new HashMap<>();

    public GenericPianoRollView(Context context) {
        super(context);
    }

    public GenericPianoRollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GenericPianoRollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setNbRow(int nb) {
        nbRow = nb;
    }

    public void clear() {
        this.noteSet.clear();
        this.lastNoteId = 0;
    }

    protected void renderLeftPart(int index, Canvas canvas) {
        int chroma = index % 12;
        boolean isBlack = (chroma % 2 == 1) ? ((chroma<4) ? true : false) : (chroma>4? true : false);  // TODO: a vérifier
        float width = nameColWidth;
        float height = instrumentSpacing;
        float x = 0.f;
        float y = index * instrumentSpacing
                + verticalShift
                + index*verticalSpacingBetweenLines;

        Paint paint = new Paint();


        if (isBlack) {
            paint.setColor(Color.BLACK);
            canvas.drawRect(x, y, x+width, y+height, paint);
        } else {
            paint.setColor(Color.WHITE);
            canvas.drawRect(x, y, x+width, y+height, paint);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = new Paint();

        // choix de la couleur (à changer)
        paint.setARGB(255, 0, 177, 235);

        for (GenericNoteProperty note : this.noteSet.values()) {
            Log.d("whistlepro", "dessin d'une note whistle");
            double start = note.start;
            double stop = note.stop;

            //if (stop < this.cursorPosition || start > this.cursorPosition+this.screenWidthInTime) continue;

            float left = Math.max((float) ((start-cursorPosition)/screenWidthInTime*getWidth()), this.nameColWidth);
            float right = (float) ((stop-cursorPosition)/screenWidthInTime*getWidth());

            float top = note.line*instrumentSpacing
                    + verticalShift
                    + note.line*verticalSpacingBetweenLines;
            float bottom = top
                    + instrumentSpacing;
                    //+ note.line*verticalSpacingBetweenLines;

            canvas.drawRect(left, top, right, bottom, paint);

            //TODO: echelle des temps (équivalence temps/pixel)
        }

        paint.setARGB(255, 0, 116, 157);
        paint.setStrokeWidth(5.f);

        // ON DESSINE A INTERVALLE REGULIER LES SEPARATEURS D'INSTRUMENTS
        //paint.setARGB(125,0,255,0);
        int nbLines = nbRow;//(int) (getHeight() / this.instrumentSpacing);
        float[] lines = new float[(nbLines+1)*4];

        int startLine = 0;//(int) Math.max(0, verticalShift/(verticalSpacingBetweenLines+instrumentSpacing));

        // écriture des lignes horizontales // TODO: shift de la hauteur selon dragging (modulo)
        for(int i=startLine; i< Math.min(nbLines+1, nbRow+1); ++i) {
            lines[4*i]   = 0;                           // départ x
            lines[4*i+1] = this.instrumentSpacing*i
                    + verticalShift
                    + i*verticalSpacingBetweenLines
                    - verticalSpacingBetweenLines/2;    // départ y
            lines[4*i+2] = getWidth();                  // arrivé x
            lines[4*i+3] = lines[4*i+1];    // arrivé y
        }


        canvas.drawLines(lines, paint);

        for(int i=0; i < nbRow; ++i) {
            renderLeftPart(i, canvas);
        }

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

        float x = event.getX(),
                y = event.getY();

        switch(action) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - this.lastX;
                float dy = y - this.lastY;

                this.cursorPosition -= dx / getWidth() * screenWidthInTime;
                this.cursorPosition = Math.max(minShift,this.cursorPosition);
                this.cursorPosition = Math.min(maxShift,this.cursorPosition);
                //Log.d("whistlepro", "whistlepro cursor : " + this.cursorPosition);
                this.verticalShift += dy;
                this.invalidate();
                break;

        }

        this.lastX = x;
        this.lastY = y;

        return true;
    }


    public long addNote(double start, double stop, int line) {
        if (minShift>start) minShift = Math.max(0,start);
        if (maxShift<stop) maxShift = stop;

        this.noteSet.put(++this.lastNoteId, new GenericNoteProperty(start, stop, line));
        return this.lastNoteId;
    }



}
