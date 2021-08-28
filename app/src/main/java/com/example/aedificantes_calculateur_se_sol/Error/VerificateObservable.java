package com.example.aedificantes_calculateur_se_sol.Error;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.GlobalParamActivator;

/**
 * Desing Pattern Observer
 * Observable object that can be notify by subscribe other object
 */
public interface VerificateObservable {


    public void notifyDataChange();

    /**
     * subscribe to Obervable
     * @param obs Observer that want to be be subscribe
     */
    public void addLikeObserver(VerificateObserver obs);

    public void removeObserver(VerificateObserver obs);


    public void setGlobalParamActivator(GlobalParamActivator globalParamActivator);
}
