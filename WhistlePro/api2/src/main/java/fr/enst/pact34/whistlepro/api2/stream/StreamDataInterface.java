package  fr.enst.pact34.whistlepro.api2.stream;

/**
 * Created by  Mohamed on 15/03/16.
 */
public interface StreamDataInterface<E> {
    void copyTo(E e); //should copy id and validity
    int getId();
    void setId(int id);
    E getNew();
    boolean isValid();
    void setValid(boolean v);
}
