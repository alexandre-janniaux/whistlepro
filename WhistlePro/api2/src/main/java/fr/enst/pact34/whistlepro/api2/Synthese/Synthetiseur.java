package fr.enst.pact34.whistlepro.api2.Synthese;

import java.util.List;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.main.Morceau;
import fr.enst.pact34.whistlepro.api2.main.Piste;
import fr.enst.pact34.whistlepro.api2.main.PisteMelodie;
import fr.enst.pact34.whistlepro.api2.main.PistePercu;

/**
 * Created by mms on 28/04/16.
 */
public class Synthetiseur {

    private final double Fs;
    private PercuGenerator percuGen = null;
    private InstruGenerator instruGen = null;

    public Synthetiseur(PercuGenerator percuGen, InstruGenerator instruGen,double smplingFreq)
    {
        this.percuGen = percuGen;
        this.instruGen = instruGen;
        this.Fs = smplingFreq;
    }

    public Signal synthetise (Morceau morceau)
    {
        Signal musique = new Signal();
        musique.setSamplingFrequency(Fs);
        musique.setLength((int) (morceau.getTotalTime() * Fs+1));
        for (Piste piste :
                morceau.getListPiste()) {
            Signal tmp =null;
            switch (piste.getTypePiste())
            {
                case Melodie:
                    tmp= synthetiseMelodie((PisteMelodie) piste);
                    break;
                case Percussions:
                    tmp= synthetisePercu((PistePercu) piste);
                    break;

            }
            if(tmp != null)
            {
                for (int i = 0; i < tmp.length(); i++) {
                    musique.setValue(i,musique.getValue(i)+tmp.getValue(i));
                }
            }
        }
        musique.maxToM(0.9);
        return  musique;
    }

    public Signal synthetise (Piste piste)
    {

        switch (piste.getTypePiste())
        {
            case Melodie:
                return synthetiseMelodie((PisteMelodie) piste);

            case Percussions:
                return synthetisePercu((PistePercu) piste);

        }

        return  null;
    }

    private Signal synthetisePercu(PistePercu pistePercu)
    {
        List<Percu> percussions = pistePercu.getPercuList();

        double totalTime = pistePercu.getTotalTime();
        double Fs = percuGen.getSamplingFreq();
        Signal sound = new Signal();
        sound.setLength((int) (Fs * totalTime));

        for (int i = 0; i < percussions.size(); i++) {
            Percu p = percussions.get(i);
            Percu.Type type = p.getType();
            if(type == null) continue;
            Signal sigGen = percuGen.getPercu(type);
            int is = (int)(Fs*p.getStartTime());
            if(sigGen.length() > sound.length() - is) continue;
            sigGen.maxToM(1);
            sound.fromSignal(sigGen,0,is,sigGen.length());
        }

        sound.setSamplingFrequency(Fs);
        return sound;
    }

    private Signal synthetiseMelodie(PisteMelodie pisteMelodie)
    {
        List<Instru> instruments = pisteMelodie.getInstruList();

        double totalTime = pisteMelodie.getTotalTime();
        double Fs = instruGen.getSamplingFreq();
        Signal sound = new Signal();
        sound.setLength((int) (Fs * totalTime));
        PisteMelodie.Instrument instrument = pisteMelodie.getInstrument();

        if(instrument != null)
        {
        for (int i = 0; i < instruments.size(); i++) {
            Instru p = instruments.get(i);
            if(p.getFreq() <1) continue;
            double time = p.getEndTime() - p.getStartTime();
            Signal sigGen = instruGen.generate(instrument,time,p.getFreq());
            sigGen.maxToM(0.5);

            sound.fromSignal(sigGen,0,(int)(Fs*p.getStartTime()),sigGen.length());
        }
        }

        sound.setSamplingFrequency(Fs);
        return sound;
    }
}
