package  fr.enst.pact34.whistlepro.api2.dataTypes;

import java.util.ArrayList;

import  fr.enst.pact34.whistlepro.api2.stream.StreamDataInterface;

/**
 * Created by mms on 15/03/16.
 */
public class ClassifResults implements StreamDataInterface<ClassifResults> {
    @Override
    public void copyTo(ClassifResults classifResults) {

        classifResults.setNbRes(this.res.length);
        System.arraycopy(this.res, 0, classifResults.res, 0, this.res.length);
        System.arraycopy(this.classes,0,classifResults.classes,0,this.classes.length);
        classifResults.id=this.id;
        classifResults.valid=this.valid;
    }

    @Override
    public ClassifResults getNew() {
        ClassifResults c = new ClassifResults();
        return c;
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


    double res[] =null;
    String classes[] = null;
    String reco = null;

    public void setNbRes(int nb)
    {
        if(classes == null || res == null || res.length<nb || classes.length < nb) {
            res = new double[nb];
            classes = new String[nb];
        }
    }

    public void fillResFromArray(double[] res_in)
    {
        if(res_in.length < res.length) throw new RuntimeException("Classif bad length res");
        for (int i = 0; i < res.length; i++) {
            res[i] = res_in[i];
        }
    }

    public void fillClassesFromArray(String[] classes_in)
    {
        if(classes_in.length < classes.length) throw new RuntimeException("Classif bad length classes");
        for (int i = 0; i < classes.length; i++) {
            classes[i] = classes_in[i];
        }
    }

    public void fillClassesFromArray(ArrayList<String> classes_in)
    {
        if(classes_in.size() < classes.length) throw new RuntimeException("Classif bad length classes");
        for (int i = 0; i < classes.length; i++) {
            classes[i] = classes_in.get(i);
        }
    }

    public String getRecoClass() {
        if(reco == null)
        {
            double max = -Double.MAX_VALUE;
            for (int i = 0; i < res.length; i++) {
                if (res[i] > max) {
                    max = res[i];
                    reco = classes[i];
                }
            }

        }
        return reco;
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
}
