package fr.enst.pact34.whistlepro.api2.main;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mms on 25/04/16.
 */
public abstract class  Piste {
    

    public abstract TypePiste getTypePiste();
/*
    private String title;

    public final void setTitle(String title)
    {
        this.title=title;
    }

    public String getTitle() {
        return title;
    }
    */
    /*
    private int id =-1;

    public final void setId(int id) {
        this.id = id;
    }

    protected final int getId() {
        return id;
    }
    */
    private double totalTime = 0 ;

    public double getTotalTime()
    {
        return totalTime;
    }

    public void setTotalTime(double totTime) {
        this.totalTime=totTime;
    }

    public abstract String getSaveString();

    protected String buildSaveString(String innerData)
    {
        String saveStr =
                "<Piste "
                +" type='"+getTypePiste().name()+"' "
                +" length='"+getTotalTime()+"' "
                +" >"
                +innerData
                +"</Piste>";

        return saveStr;
    }

    public static List<String> splitStrAsPistesStrs(String strData) {
        LinkedList<String> values = new LinkedList<>();

        Pattern pattern = Pattern.compile("<Piste[ ]*type[ ]*=[ ]*'[^']*'[ ]*length[ ]*=[ ]*'[^']*'[ ]*>.*?</Piste[ ]*>", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(strData);

        while(matcher.find())
        {
            values.add(matcher.group());
        }
        return values;
    }

    public static class Builder
    {
        Piste piste = null;
        boolean valid = false;
        public void fromString(String string) {
            valid = false;
            piste = null;
            Pattern pattern = Pattern.compile("<Piste[ ]*type[ ]*=[ ]*'[^']*'[ ]*length[ ]*=[ ]*'[^']*'[ ]*>.*?</Piste[ ]*>", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(string);


            if(matcher.find()) {
                String strData = matcher.group();

                Pattern patternType = Pattern.compile("type[ ]*=[ ]*'[^']*'");
                Matcher matcherType = patternType.matcher(strData);

                if(matcherType.find()) {

                    Piste pisteTmp;
                    String strType = matcherType.group();

                    if(TypePiste.Melodie.name().equals(strType) )
                    {
                        pisteTmp = new PisteMelodie();
                    }
                    else if(TypePiste.Percussions.name().equals(strType) )
                    {
                        pisteTmp = new PistePercu();
                    }
                    else
                    {
                        return;
                    }


                    Pattern patternLength = Pattern.compile("length[ ]*=[ ]*'[^']*'");
                    Matcher matcherTLengh = patternLength.matcher(strData);
                    if(matcherTLengh.find()) {
                        pisteTmp.setTotalTime(Double.valueOf(matcher.group()));

                        strData=strData.replaceAll("<Piste[ ]*type[ ]*=[ ]*'[^']*'[ ]*length[ ]*=[ ]*'[^']*'[ ]*>|</Piste[ ]*>","");

                        pisteTmp.buildFromString(strData);

                        piste = pisteTmp;
                        
                        valid = true;
                    }

                }
            }
        }

        public boolean isValid() {
            return valid;
        }

        public Piste build() {

            return piste;
        }
    }

    protected abstract void buildFromString(String strData);

    public enum TypePiste {
        Melodie,
        Percussions
    }
}
