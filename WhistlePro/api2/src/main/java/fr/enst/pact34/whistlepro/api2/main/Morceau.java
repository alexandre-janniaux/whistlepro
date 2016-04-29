package fr.enst.pact34.whistlepro.api2.main;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import fr.enst.pact34.whistlepro.api2.stream.StreamManager;


/**
 * Created by mms on 25/04/16.
 */
public class Morceau {

    private List<Piste> listePiste = new LinkedList<>();

    private String title = "";

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void addPiste(Piste newPiste)
    {
        if(listePiste.contains(newPiste)) return;
        newPiste.setId(listePiste.size());
        listePiste.add(newPiste);
    }

    public void removePiste(Piste piste)
    {
        listePiste.remove(piste);
    }

    public int nbPiste()
    {
        return listePiste.size();
    }

    public List<Piste> getListPiste()
    {
        return Collections.unmodifiableList(listePiste);
    }

    public Piste getPisteByIndex(int index)
    {
        return listePiste.get(index);
    }

    public Piste getPisteById(int id)
    {
        for (Piste p :
                listePiste) {
            if (p.getId() == id) return p;
        }
        
        return null;
    }
}
