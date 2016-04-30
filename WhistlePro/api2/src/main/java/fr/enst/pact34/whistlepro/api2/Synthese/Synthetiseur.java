package fr.enst.pact34.whistlepro.api2.Synthese;

import java.util.List;

import fr.enst.pact34.whistlepro.api2.dataTypes.Signal;
import fr.enst.pact34.whistlepro.api2.main.Piste;
import fr.enst.pact34.whistlepro.api2.main.PisteMelodie;
import fr.enst.pact34.whistlepro.api2.main.PistePercu;

/**
 * Created by mms on 28/04/16.
 */
public class Synthetiseur {

    private PercuGenerator percuGen = null;
    private InstruGenerator instruGen = null;

    public Synthetiseur(PercuGenerator percuGen, InstruGenerator instruGen)
    {
        this.percuGen = percuGen;
        this.instruGen = instruGen;
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
            double time = p.getEndTime() - p.getStartTime();
            Signal sigGen = percuGen.generate(type,time);

            sound.fromSignal(sigGen,0,(int)(Fs*p.getStartTime()),sigGen.length());
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

        for (int i = 0; i < instruments.size(); i++) {
            Instru p = instruments.get(i);
            Instru.Type type = p.getType();
            if(type == null || p.getFreq() <1) continue;
            double time = p.getEndTime() - p.getStartTime();
            Signal sigGen = instruGen.generate(type,time,p.getFreq());

            sound.fromSignal(sigGen,0,(int)(Fs*p.getStartTime()),sigGen.length());
        }

        sound.setSamplingFrequency(Fs);
        return sound;
    }
}
