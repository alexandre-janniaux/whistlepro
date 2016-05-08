package fr.enst.pact34.whistlepro.api2.main;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.enst.pact34.whistlepro.api2.Synthese.Instru;

/**
 * Created by mms on 25/04/16.
 */
public class PisteMelodie extends Piste {

    private List<Instru> instruList = new LinkedList<>();

    @Override
    public TypePiste getTypePiste() {
        return TypePiste.Melodie;
    }

    @Override
    public String getSaveStringInner() {
        String str = "<instrument>"+getInstrument().name()+"</instrument>";
        for (Instru instru :
                instruList) {
            str+= "<Instru>" + instru.getStartTime()+ ";"+instru.getEndTime()+";"+instru.getFreq()+"</Instru>";
        }
        return str;
    }

    @Override
    protected void buildFromString(String strData) {
        Pattern patternInstr = Pattern.compile("<instrument>.*?</instrument>", Pattern.DOTALL);
        Matcher matcherInstr = patternInstr.matcher(strData);
        if(matcherInstr.find())
        {
            String tmp = matcherInstr.group();
            tmp=tmp.replace("<instrument>","");
            tmp=tmp.replace("</instrument>","");
            for (Instrument i :
                    Instrument.values()) {
                if(i.name().equals(tmp))
                {
                    setInstrument(i);
                    break;
                }
            }
        }

        Pattern pattern = Pattern.compile("<Instru>.*?</Instru>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(strData);

        while(matcher.find()) {
            String tmp = matcher.group();
            tmp = tmp.replaceAll("<Instru>|</Instru>","");
            String[] values = tmp.split(";");
            if(values.length==3)
            {
                Instru tmpInstru = new Instru();
                tmpInstru.setStartTime(Double.valueOf(values[0]));
                tmpInstru.setEndTime(Double.valueOf(values[1]));
                tmpInstru.setFreq(Double.valueOf(values[2]));
                addInstru(tmpInstru);
            }
        }
    }

    public List<Instru> getInstruList() {
        return instruList;
    }

    public void addInstru(Instru instru) {
        instruList.add(instru);
    }

    public enum Instrument {
        Cuivre, Boise, Piano
    }

    private Instrument instrument = Instrument.Piano;

    public void setInstrument(Instrument instrument)
    {
        this.instrument = instrument;
    }

    public Instrument getInstrument() {
        return instrument;
    }
}
