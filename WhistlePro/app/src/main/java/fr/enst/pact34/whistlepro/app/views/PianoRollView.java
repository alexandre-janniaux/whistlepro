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

import fr.enst.pact34.whistlepro.app.models.PianoRollModel;

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


public class PianoRollView extends GenericPianoRollView {;

    public PianoRollView(Context context) {
        super(context);
    }

    public PianoRollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PianoRollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private PianoRollModel model = null;

    public void setModel(PianoRollModel model) {
        // TODO: delete view notifier : if (this.model != null)
        model.addViewNotifier(this);
        this.model = model;
    }

    @Override
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

    public void notifyView() {
        setNbRow(model.getNoteTypeCount());
        clear();
        for(int i=0; i<model.getNoteTypeCount(); ++i) {
            for (int j = 0; j < model.getNoteCount(i); ++j) {
                PianoRollModel.NoteProperty note = model.getNote(i, j);
                addNote(note.getStart(), note.getStop(), 0);// note.getPitch()) TODO
            }
        }
    }

    public void notifyNoteView(int piste, int note) {
    }

}
