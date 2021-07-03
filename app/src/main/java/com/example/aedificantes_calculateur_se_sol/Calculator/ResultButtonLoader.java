package com.example.aedificantes_calculateur_se_sol.Calculator;

/**
 * With this design patter we don't care about wich object load Result because we just need to have a way to notify it to display result page
 */
public interface ResultButtonLoader {

    /**
     * on call display result page
     */
    public void launchResultsView();
}

