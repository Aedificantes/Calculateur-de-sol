package com.example.aedificantes_calculateur_se_sol.Error;

import java.util.List;

/**
 * Desing Pattern Observer
 * Observed object that can be call back by Observable to verify data set
 */
public interface VerificateObserver {

    /**
     * verify is all value are set or not (0.0 is a none fill value)
     * @return false if isn't fill true else
     */
    public boolean isFill();
}
