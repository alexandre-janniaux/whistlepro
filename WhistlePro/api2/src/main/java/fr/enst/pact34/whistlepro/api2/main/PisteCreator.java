package fr.enst.pact34.whistlepro.api2.main;

import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import fr.enst.pact34.whistlepro.api2.Synthese.Instru;
import fr.enst.pact34.whistlepro.api2.Synthese.Percu;
import fr.enst.pact34.whistlepro.api2.dataTypes.AttackTimes;
import fr.enst.pact34.whistlepro.api2.dataTypes.ClassifResults;
import fr.enst.pact34.whistlepro.api2.dataTypes.Frequency;

/**
 * Created by mms on 28/04/16.
 */
public class PisteCreator {

    private final double SAMPLE_TIME = 0.010;

    private List<ClassifResults> classifResultses = new LinkedList<>();
    private List<AttackTimes> attackTimes = new LinkedList<>();
    private List<Frequency> frequencies = new LinkedList<>();

    public void addPercu(ClassifResults onomatopee) {
        this.classifResultses.add(onomatopee);
    }

    public void addAttackTime(AttackTimes attackTime) {
        this.attackTimes.add(attackTime);
    }

    public void addFrequency(Frequency frequency) {
        this.frequencies.add(frequency);
    }

    public void addPercus(List<ClassifResults> onomatopees) {
        this.classifResultses.addAll(onomatopees);
    }

    public void addAttackTimes(List<AttackTimes> attackTimes) {
        this.attackTimes.addAll(attackTimes);
    }

    public void addFrequencies(List<Frequency> frequencies) {
        this.frequencies.addAll(frequencies);
    }

    public Piste buildPiste(Piste.TypePiste typePiste)
    {

        //on cree une liste avec tout les elements
        List<AttackTimes.Attack> allAttacksList = new LinkedList<>();
        for (int i = 0; i < attackTimes.size(); i++) {
            AttackTimes attacks = attackTimes.get(i);
            for (AttackTimes.Attack attack:
                    attacks.getAttackTimes()){
                double tmp ;
                tmp=attack.getTime();
                tmp= tmp+ attacks.getId()*SAMPLE_TIME;
                attack.setTime(tmp);
                allAttacksList.add(attack);
            }
        }

        //on trie
        Collections.sort(allAttacksList, new Comparator<AttackTimes.Attack>() {
            @Override
            public int compare(AttackTimes.Attack t0, AttackTimes.Attack t1) {
                if(t0.getTime() - t1.getTime() < 0)
                    return -1;
                if(t0.getTime() - t1.getTime() > 0)
                    return 1;
                return 0;
            }
        });

        //suppression des doublons
        if(allAttacksList.size()>1) {
            for (int i = 1; i < allAttacksList.size(); ) {
                if (Math.abs(allAttacksList.get(i-1).getTime() - allAttacksList.get(i).getTime()) < 1e-6) {
                    allAttacksList.remove(i);
                }
                else
                {
                    i++;
                }
            }
        }

        //Creation de la liste des percussions ou des instruments
        Piste piste = null;
        if(typePiste==null) throw new RuntimeException("Piste has no type");

        switch (typePiste)
        {
            case Melodie:
                piste = buildPisteMelodie(allAttacksList);
                break;

            case Percussions:
                piste = buildPistePercu(allAttacksList);
                break;
        }
        
        return piste;
    }

    private Piste buildPisteMelodie(List<AttackTimes.Attack> allAttacksList) {
        PisteMelodie piste = new PisteMelodie();
        Instru tmp =null;

        boolean lastEnded =true;
        for (int i = 0; i < allAttacksList.size(); i++) {

            AttackTimes.Attack attack = allAttacksList.get(i);

            if(tmp == null) //on attend le premier debut
            {
                if(attack.getType() == AttackTimes.Attack.Type.Up)
                {
                    tmp = new Instru();
                    tmp.setStartTime(attack.getTime());
                    lastEnded = false;
                }
            } else //on attend la derniere fn ou le prochain debut
            {
                switch (attack.getType())
                {
                    case Up:
                        if(lastEnded == true) {
                            piste.addInstru(tmp);
                            tmp = new Instru();
                            tmp.setStartTime(attack.getTime());
                            lastEnded = false;
                        }
                        break;

                    case Down:
                        // on cherche la derniere fin
                        // (normalement il n'y en a qu'un mais au cas ou)
                        tmp.setEndTime(attack.getTime());
                        lastEnded = true;
                        break;
                }
            }

        }

        if(tmp == null) return  null;

        if(lastEnded==false)
        {
            tmp.setEndTime(attackTimes.size()*SAMPLE_TIME);
        }
        piste.addInstru(tmp);

        //ajout onomatopées

        //on trie les donnees de classifs
        Collections.sort(classifResultses, new Comparator<ClassifResults>() {
            @Override
            public int compare(ClassifResults t0, ClassifResults t1) {
                return (t0.getId() - t1.getId());
            }
        });

        Hashtable<Double, Integer> countingMax = new Hashtable<>();
        for (Instru instru : piste.getInstruList()
                ) {
            int id_start = (int)(instru.getStartTime()/SAMPLE_TIME);
            int id_end = (int)(instru.getEndTime()/SAMPLE_TIME);
            countingMax.clear();
            for (int i = id_start; i <= id_end && i < frequencies.size(); i++) {
                if(frequencies.get(i).isValid()==false) continue;
                Double tmpDbl = frequencies.get(i).getFrequency();
                if(countingMax.containsKey(tmpDbl))
                {
                    countingMax.put(tmpDbl,countingMax.get(tmpDbl)+1);
                }
                else
                {
                    countingMax.put(tmpDbl, 1);
                }
            }

            int max = -1;
            Double maxKey = null;
            for (Double key :
                    countingMax.keySet()) {
                if(countingMax.get(key)> max)
                {
                    max = countingMax.get(key);
                    maxKey = key;
                }
            }

            if(maxKey != null) {
                instru.setFreq(maxKey);
            }
            else
                instru.setFreq(0);
        }
        piste.setTotalTime(attackTimes.size()*SAMPLE_TIME);
        return piste;
    }

    private Piste buildPistePercu(List<AttackTimes.Attack> allAttacksList) {
        PistePercu piste = new PistePercu();
        Percu tmp =null;

        boolean lastEnded =true;
        for (int i = 0; i < allAttacksList.size(); i++) {

            AttackTimes.Attack attack = allAttacksList.get(i);

            if(tmp == null) //on attend le premier debut
            {
                if(attack.getType() == AttackTimes.Attack.Type.Up)
                {
                    tmp = new Percu();
                    tmp.setStartTime(attack.getTime());
                    lastEnded = false;
                }
            } else //on attend la derniere fn ou le prochain debut
            {
                switch (attack.getType())
                {
                    case Up:
                        if(lastEnded == true) {
                            piste.addPercu(tmp);
                            tmp = new Percu();
                            tmp.setStartTime(attack.getTime());
                            lastEnded = false;
                        }
                        break;

                    case Down:
                        // on cherche la derniere fin
                        // (normalement il n'y en a qu'un mais au cas ou)
                        tmp.setEndTime(attack.getTime());
                        lastEnded = true;
                        break;
                }
            }

        }

        if(tmp == null) return  null;

        if(lastEnded==false)
        {
            tmp.setEndTime(attackTimes.size()*SAMPLE_TIME);
        }
        piste.addPercu(tmp);

        //ajout onomatopées

        //on trie les donnees de classifs
        Collections.sort(classifResultses, new Comparator<ClassifResults>() {
            @Override
            public int compare(ClassifResults t0, ClassifResults t1) {
                return (t0.getId() - t1.getId());
            }
        });

        Hashtable<String, Integer> countingMax = new Hashtable<>();
        for (Percu percu : piste.getPercuList()
                ) {
            int id_start = (int)(percu.getStartTime()/SAMPLE_TIME);
            int id_end = (int)(percu.getEndTime()/SAMPLE_TIME);
            countingMax.clear();
            for (int i = id_start; i <= id_end && i < classifResultses.size(); i++) {
                if(classifResultses.get(i).isValid()==false) continue;
                String tmpStr = classifResultses.get(i).getRecoClass();
                if(countingMax.containsKey(tmpStr))
                {
                    countingMax.put(tmpStr,countingMax.get(tmpStr)+1);
                }
                else
                {
                    countingMax.put(tmpStr, 1);
                }
            }

            int max = -1;
            String maxKey = null;
            for (String key :
                    countingMax.keySet()) {
                if(countingMax.get(key)> max)
                {
                    max = countingMax.get(key);
                    maxKey = key;
                }
            }

            if(maxKey != null)
                percu.setType(tableCorrespondancePercu.get(maxKey));
            else
                percu.setType(null);
        }
        piste.setTotalTime(attackTimes.size()*SAMPLE_TIME);
        return piste;
    }

    // associe une chaine correspondante à l'element reconnu aux type de percussion
    Hashtable<String,Percu.Type> tableCorrespondancePercu = new Hashtable<>();

    public void setPercuCorrespondance(String recoStr, Percu.Type typeAssocie)
    {
        tableCorrespondancePercu.put(recoStr, typeAssocie);
    }

    public void clearOldData() {
        classifResultses.clear();
        frequencies.clear();
        attackTimes.clear();
    }
}
