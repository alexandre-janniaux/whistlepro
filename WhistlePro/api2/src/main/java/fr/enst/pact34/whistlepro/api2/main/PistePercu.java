package fr.enst.pact34.whistlepro.api2.main;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.enst.pact34.whistlepro.api2.Synthese.Instru;
import fr.enst.pact34.whistlepro.api2.Synthese.Percu;

/**
 * Created by mms on 25/04/16.
 */
public class PistePercu extends Piste {

    private List<Percu> percuList = new LinkedList<>();

    @Override
    public TypePiste getTypePiste() {
        return TypePiste.Percussions;
    }

    public List<Percu> getPercuList() {
        return percuList;
    }

    public void addPercu(Percu percu) {
        percuList.add(percu);
    }


    @Override
    public String getSaveStringInner() {
        String str = "";
        for (Percu percu :
                percuList) {
            str+= "<Percu>" + percu.getStartTime()+ ";"+percu.getEndTime()+";"+percu.getType().name()+"</Percu>";
        }
        return str;
    }

    @Override
    protected void buildFromString(String strData) {
        Pattern pattern = Pattern.compile("<Percu>.*?</Percu>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(strData);

        while(matcher.find()) {
            String tmp = matcher.group();
            tmp = tmp.replaceAll("<Percu>|</Percu>","");
            String[] values = tmp.split(";");
            if(values.length==3)
            {
                Percu tmpPercu = new Percu();
                tmpPercu.setStartTime(Double.valueOf(values[0]));
                tmpPercu.setEndTime(Double.valueOf(values[1]));
                Percu.Type typeF = null;
                for (Percu.Type type: Percu.Type.values()
                     ) {
                    if(type.name().equals(values[2]))
                    {
                        typeF= type;
                        break;
                    }
                }
                if(typeF==null) continue;
                tmpPercu.setType(typeF);
                addPercu(tmpPercu);
            }
        }
    }
}
