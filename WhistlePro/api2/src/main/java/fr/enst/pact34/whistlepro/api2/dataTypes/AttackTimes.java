package  fr.enst.pact34.whistlepro.api2.dataTypes;

import java.util.LinkedList;
import java.util.List;

import  fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 15/03/16.
 */
public class AttackTimes implements StreamDataInterface<AttackTimes> {

    @Override
    public void copyTo(AttackTimes attaqueTimes) {

        attaqueTimes.id=this.id;
        attaqueTimes.valid=this.valid;
        //TODO remove NEW HERE
        attaqueTimes.times.clear();
        for (int i = 0; i < this.times.size(); i++) {
            Attack a = times.get(i);
            Attack b = new Attack(a.getTime(),a.getType());
            attaqueTimes.times.add(b);
        }
    }

    @Override
    public AttackTimes getNew() {
        //TODO
        return new AttackTimes();
    }


    int id = -1;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }


    boolean valid = true;

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public void setValid(boolean v) {
        valid =v;
    }


    List<Attack> times = new LinkedList<>();

    public void addUp(double v) {
        times.add(new Attack(v, Attack.Type.Up));
    }

    public void addDown(double v) {
        times.add(new Attack(v, Attack.Type.Down));
    }

    public List<Attack> getAttackTimes() {
        return times;
    }

    public void clear() {
        times.clear();
    }

    public static class Attack
    {
        public enum Type { Up, Down};
        private double time ;
        private Type type;

        public Attack(double time, Type type) {
            this.time = time;
            this.type = type;
        }

        public double getTime() {
            return time;
        }

        public Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        public void setTime(double time) {
            this.time = time;
        }

    }
}
