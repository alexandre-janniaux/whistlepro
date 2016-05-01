package fr.enst.pact34.whistlepro.api2.main;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.enst.pact34.whistlepro.api2.stream.StreamManager;


/**
 * Created by mms on 25/04/16.
 */
public class Morceau {

    private List<Piste> listePiste = new LinkedList<>();

    private String title = "";

    public void setTitle(String title) {
        title = title.replace("'","");
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void addPiste(Piste newPiste)
    {
        if(listePiste.contains(newPiste)) return;
        //newPiste.setId(listePiste.size());
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

    public String getSaveString() {
        String saveStr = "";
        saveStr += "<Morceau titre='"+title+"' >";
        for (Piste p: listePiste
             ) {
            saveStr += p.getSaveString();
        }
        saveStr += "</Morceau>";
        return saveStr;
    }

    public static class Builder
    {
        Morceau m = null;
        private boolean valid = false;
        public void fromString(String dataFromFile) {

            // TODO
            Pattern pattern = Pattern.compile("<Morceau[ ]*titre[ ]*=[ ]*'[^']*'[ ]*>.*?</Morceau[ ]*>", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(dataFromFile);


            if(matcher.find())
            {
                String strData = matcher.group();

                Pattern patternTitre = Pattern.compile("titre[ ]*=[ ]*'[^']*'");
                Matcher matcherTitre= patternTitre.matcher(strData);

                if(matcherTitre.find())
                {
                    m= new Morceau();

                    String titre = matcherTitre.group();
                    titre = titre.substring(titre.indexOf("'")+1);
                    titre = titre.substring(0,titre.indexOf("'"));
                    m.setTitle(titre);

                    strData = strData.replaceAll("<Morceau[ ]*titre[ ]*=[ ]*'[^']*'[ ]*>|</Morceau[ ]*>", "");

                    List<String> pistesStrData = Piste.splitStrAsPistesStrs(strData);

                    for (String strPiste :
                            pistesStrData) {
                        Piste.Builder pisteBuilder = new Piste.Builder();
                        pisteBuilder.fromString(strPiste);
                        if(pisteBuilder.isValid())
                        {
                            m.addPiste(pisteBuilder.build());
                        }
                    }

                    valid = true;
                }
            }
        }

        public boolean dataValid() {
            return valid;
        }

        public Morceau build() {
            return m;
        }
    }
}
