package fr.enst.pact34.whistlepro.app.models;


import java.util.ArrayList;
import java.util.HashMap;

import fr.enst.pact34.whistlepro.app.views.PianoRollView;

public class PianoRollModel {

    private ArrayList<PianoRollView> views = new ArrayList<>();

    private ArrayList<ArrayList<NoteProperty>> notes = new ArrayList<>();

    public void addViewNotifier(PianoRollView pianoRollView) {
        this.views.add(pianoRollView);
    }

    public static class NoteProperty {

        private int pitch;
        private double intensity;
        private double start;
        private double stop;
        private int type;

        public NoteProperty(int type, int pitch, double intensity, double start, double stop) {
            this.type = type;
            this.pitch = pitch;
            this.intensity = intensity;
            this.start = start;
            this.stop = stop;
        }

        public int getPitch() { return pitch; }
        public double getIntensity() { return intensity; }
        public double getStop() { return stop; }
        public double getStart() { return start; }
        public int getType() { return type; }
    }

    private int lastId=0;
    private int noteTypeCount=6*12;

    public PianoRollModel() {
        for(int i=0; i<getNoteTypeCount(); ++i) {
            this.notes.add(new ArrayList<NoteProperty>());
        }
    }

    public int addNote(NoteProperty note) {
        notes.get(note.getPitch()).add(note); return lastId++;
    }


    public int getNoteTypeCount() {
        return this.noteTypeCount;
    }

    public int getNoteCount(int noteType) {
        return this.notes.get(noteType).size();
    }

    public NoteProperty getNote(int noteType, int noteNb) {
        return this.notes.get(noteType).get(noteNb);
    }

    public void update() {
        for(PianoRollView view : views) {
            view.notifyView();
        }
    }




}
