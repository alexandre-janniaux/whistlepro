package fr.enst.pact34.whistlepro.demo.models;


import java.util.ArrayList;

import fr.enst.pact34.whistlepro.demo.views.PianoRollView;

public class PianoRollModel {

    private ArrayList<PianoRollView> views;

    private ArrayList<NoteProperty> notes = new ArrayList<>();

    public void addViewNotifier(PianoRollView pianoRollView) {
        this.views.add(pianoRollView);
    }

    public class NoteProperty {

        private double pitch;
        private double intensity;
        private double start;
        private double stop;
        private int type;

        public NoteProperty(int type, double pitch, double intensity, double start, double stop) {
            this.type = type;
            this.pitch = pitch;
            this.intensity = intensity;
            this.start = start;
            this.stop = stop;
        }

        public double getPitch() { return pitch; }
        public double getIntensity() { return intensity; }
        public double getStop() { return stop; }
        public double getStart() { return start; }
        public int getType() { return type; }
    }

    public int addNote(NoteProperty note) {
        return 0;
    }

    private int noteTypeCount;

    public int getNoteTypeCount() {
        return this.noteTypeCount;
    }

    public int getNoteCount(int noteType) {
        return 0;
    }

    public NoteProperty getNote(int noteType, int noteNb) {
        return new NoteProperty(0,0,0,0,0);
    }

    public void update() {
        for(PianoRollView view : views) {

        }
    }




}
