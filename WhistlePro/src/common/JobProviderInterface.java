package common;

// TODO: documentation
public interface JobProviderInterface
{

    //////////////////////////////
    /// @brief Warns the application whether the provider has work or not
    /// @return true if work is available, false otherwise
    //////////////////////////////
    boolean isWorkAvailable();    

    
    //////////////////////////////
    /// @brief Do the work, perhaps in another thread
    //////////////////////////////
    void doWork();

}

