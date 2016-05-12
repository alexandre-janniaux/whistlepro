package fr.enst.pact34.whistlepro.api2.main;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mms on 25/04/16.
 */
public abstract class  Piste {


    private boolean mute=false;
    private boolean solo=false;

    public abstract TypePiste getTypePiste();

    private String title;

    public final void setTitle(String title)
    {
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    private int id =-1;

    public final void setId(int id) {
        this.id = id;
    }

    public final int getId() {
        return id;
    }

    private double totalTime = 0 ;

    public double getTotalTime()
    {
        return totalTime;
    }

    public void setTotalTime(double totTime) {
        this.totalTime=totTime;
    }

    public String getSaveString()
    { 
        String saveStr =
                "<Piste "
                        +" type='"+getTypePiste().name()+"' "
                        +" length='"+getTotalTime()+"' "
                        +" mute='"+mute+"' "
                        +" solo='"+solo+"' "
                        +" title='"+title+"' "
                        +" >"
                        + getSaveStringInner()
                        +"</Piste>";

        return saveStr;
    }

    protected abstract String getSaveStringInner();


    public static List<String> splitStrAsPistesStrs(String strData) {
        LinkedList<String> values = new LinkedList<>();

        Pattern pattern = Pattern.compile("<Piste[ ]*type[ ]*=[ ]*'[^']*'[ ]*length[ ]*=[ ]*'[^']*'" +
                "[ ]*(mute[ ]*=[ ]*'[^']*')*[ ]*(solo[ ]*=[ ]*'[^']*')*[ ]*(title[ ]*=[ ]*'[^']*')*[ ]*>.*?</Piste[ ]*>", Pattern.DOTALL);
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
            Pattern pattern = Pattern.compile("<Piste[ ]*type[ ]*=[ ]*'[^']*'[ ]*length[ ]*=[ ]*'[^']*'" +
                    "[ ]*(mute[ ]*=[ ]*'[^']*')*[ ]*(solo[ ]*=[ ]*'[^']*')*[ ]*(title[ ]*=[ ]*'[^']*')*[ ]*>.*?</Piste[ ]*>", Pattern.DOTALL);
            Matcher matcher = pattern.matcher(string);


            if(matcher.find()) {
                String strData = matcher.group();

                Pattern patternType = Pattern.compile("type[ ]*=[ ]*'[^']*'");
                Matcher matcherType = patternType.matcher(strData);

                if(matcherType.find()) {

                    Piste pisteTmp;
                    String strType = matcherType.group();

                    strType=strType.substring(strType.indexOf("\'")+1);
                    strType=strType.substring(0,strType.indexOf("\'"));

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
                        String strLen = matcherTLengh.group();

                        strLen=strLen.substring(strLen.indexOf("\'")+1);
                        strLen=strLen.substring(0,strLen.indexOf("\'"));

                        pisteTmp.setTotalTime(Double.valueOf(strLen));


                        Pattern patternTitle = Pattern.compile("title[ ]*=[ ]*'[^']*'");
                        Matcher matcherTitle = patternTitle.matcher(strData);
                        if(matcherTitle.find()) {
                            String strTitle = matcherTitle.group();

                            strTitle=strTitle.substring(strTitle.indexOf("\'")+1);
                            strTitle=strTitle.substring(0,strTitle.indexOf("\'"));

                            pisteTmp.setTitle(strTitle);
                        }

                        Pattern patternMute = Pattern.compile("mute[ ]*=[ ]*'[^']*'");
                        Matcher matcherMute = patternMute.matcher(strData);
                        if(matcherMute.find()) {
                            String strMute = matcherMute.group();

                            strMute=strMute.substring(strMute.indexOf("\'")+1);
                            strMute=strMute.substring(0,strMute.indexOf("\'"));

                            pisteTmp.setMuted(Boolean.parseBoolean(strMute));
                        }

                        Pattern patternSolo = Pattern.compile("solo[ ]*=[ ]*'[^']*'");
                        Matcher matcherSolo = patternSolo.matcher(strData);
                        if(matcherSolo.find()) {
                            String strSolo = matcherSolo.group();

                            strSolo=strSolo.substring(strSolo.indexOf("\'")+1);
                            strSolo=strSolo.substring(0,strSolo.indexOf("\'"));

                            pisteTmp.setSolo(Boolean.parseBoolean(strSolo)); 
                        }


                        strData=strData.replaceAll("<Piste[ ]*type[ ]*=[ ]*'[^']*'[ ]*length[ ]*=[ ]*'[^']*'" +
                                "[ ]*(mute[ ]*=[ ]*'[^']*')*[ ]*(solo[ ]*=[ ]*'[^']*')*[ ]*(title[ ]*=[ ]*'[^']*')*[ ]*>|</Piste[ ]*>","");

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
    
    public void setMuted(boolean mute) {
        this.mute = mute;
    }

    public void setSolo(boolean solo) {
        this.solo = solo;
    }

    public boolean getMuted() {return this.mute;}

    public boolean getSolo() {return this.solo;}


    protected abstract void buildFromString(String strData);

    public enum TypePiste {
        Melodie,
        Percussions
    }
}
