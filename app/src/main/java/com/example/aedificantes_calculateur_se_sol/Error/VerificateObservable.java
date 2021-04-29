package com.example.aedificantes_calculateur_se_sol.Error;

public interface VerificateObservable {

    public void notifyDataChange();
    public void addLikeObserver(VerificateObserver obs);
}
