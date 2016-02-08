package common;

// TODO: documentation
public interface JobProviderInterface
{

    //////////////////////////////
    /// @brief Warn the application whether the provider has work or not
    //////////////////////////////
    boolean hasWork();    

    
    //////////////////////////////
    /// @brief Let the work be done
    //
    // Let the work be done in the current thread -> don't forget to solve thread issue.
    //////////////////////////////
    void doWork();

}

