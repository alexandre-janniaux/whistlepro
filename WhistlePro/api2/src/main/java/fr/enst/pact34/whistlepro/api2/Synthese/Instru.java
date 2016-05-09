package fr.enst.pact34.whistlepro.api2.Synthese;

import fr.enst.pact34.whistlepro.api2.common.NoteCorrector;

/**
 * Created by mms on 28/04/16.
 */
public class Instru extends SynthetisableElmt {

    private double freq = 0.0;
    private int note ;

    public double getFreq() {
        return  freq;
    }
    public int getNote() {
        return note;
    }

    public void setFreq(double freq) {
        this.freq=freq;
        this.note = NoteCorrector.getNoteFromFreq(freq);
    }




}
