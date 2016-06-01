package fr.enst.pact34.whistlepro.app.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import fr.enst.pact34.whistlepro.app.R;
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


public class RhythmRollView extends GenericPianoRollView {;

    public RhythmRollView(Context context) {
        super(context);
    }

    public RhythmRollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RhythmRollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private PianoRollModel model = null;

    public void setModel(PianoRollModel model) {
        // TODO: delete view notifier : if (this.model != null)
        this.model = model;
    }

    @Override
    protected void renderLeftPart(int index, Canvas canvas) {
        int chroma = index % 12;
        float width = nameColWidth;
        float height = instrumentSpacing;
        float x = 0.f;
        float y = index * instrumentSpacing
                + verticalShift
                + index*verticalSpacingBetweenLines;

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.wp_title, getContext().getTheme()));
        canvas.drawText("Instrument", x, y, paint);
    }

    public void notifyView() {
        setNbRow(model.getNoteTypeCount());
        clear();
        for(int i=0; i<model.getNoteTypeCount(); ++i) {
            for (int j = 0; j < model.getNoteCount(i); ++j) {
                PianoRollModel.NoteProperty note = model.getNote(i, j);
                addNote(note.getStart(), note.getStop(), note.getPitch());
            }
        }
    }

    public void notifyNoteView(int piste, int note) {
    }

}
