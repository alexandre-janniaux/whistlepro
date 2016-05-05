package fr.enst.pact34.whistlepro.demo.models;


import java.util.ArrayList;

import fr.enst.pact34.whistlepro.demo.views.PianoRollView;

public class PianoRollModel {

    private ArrayList<PianoRollView> views;

    public void addViewNotifier(PianoRollView pianoRollView) {
        this.views.add(pianoRollView);
    }

    public class NoteProperty {
        public double getPitch() { return 0.; }
        public double getIntensity() { return 0.; }
        public double getStop() { return 0.; }
        public double getStart() {return 0.; }

    }

    private int noteTypeCount;

    public int getNoteTypeCount() {
        return this.noteTypeCount;
    }

    public int getNoteCount(int noteType) {
        return 0;
    }

    public NoteProperty getNote(int noteType, int noteNb) {
        return new NoteProperty();
    }

    public void update() {
        for(PianoRollView view : views) {

        }
    }




}
