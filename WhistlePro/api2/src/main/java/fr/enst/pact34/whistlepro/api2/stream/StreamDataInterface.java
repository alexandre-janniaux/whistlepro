package  fr.enst.pact34.whistlepro.api2.stream;

/**
 * Created by  Mohamed on 15/03/16.
 */
public interface StreamDataInterface<E> {
    void copyTo(E e);
    E getNew();
}
