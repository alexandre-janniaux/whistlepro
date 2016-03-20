package fr.enst.pact34.whistlepro.api.common;



/*
 * Defines whether a given object is valid for use and how to create a well-formed object
 */
public interface OutputRequestInterface<F> {

    /*
     * Checks whether a given object is valid for use
     * @param object the object to check
     * @return true if object is valid
     */
    boolean isValid(F object);

    /*
     * Create a new well-formed object as requested
     * @return the new well-formed object
     */
    F makeObject();

}
