package fr.enst.pact34.whistlepro.pcapp.adapters;

import fr.enst.pact34.whistlepro.api.attaque.Enveloppe;
import fr.enst.pact34.whistlepro.api.attaque.Pics;
import fr.enst.pact34.whistlepro.api.common.Convolution1D;
import fr.enst.pact34.whistlepro.pcapp.interfaces.adapters.CurveAdapterInterface;

public class AttackAdapter implements CurveAdapterInterface {

    private double[] attackSignal;

    public static AttackAdapter createFromSignal(double[] signal) {
        AttackAdapter attack = new AttackAdapter();
        double[] e = Enveloppe.enveloppe(0.99, signal);
        double[] kernel = new double[10];
        for(int i=0; i<kernel.length; ++i) kernel[i] = 1/kernel.length;
        Convolution1D conv = new Convolution1D(0,kernel);
        double[] e2 = Enveloppe.sousEchantillonne(200, e);
        double[] derive = Enveloppe.derive(10, e2);
        Pics pics = new Pics();
        attack.attackSignal = derive;
        System.out.println("Attaque points " + attack.attackSignal.length);
        return attack;
    }

    @Override
    public boolean isFinite() {
        return true;
    }

    @Override
    public double getValue(int i) {
        return attackSignal[i];
    }

    @Override
    public double getAbscisse(int i) {
        return i;
    }

    @Override
    public int getNbPoints() {
        return attackSignal.length;
    }

    @Override
    public int getMaxAmplitude() {
        return 0;
    }
}
