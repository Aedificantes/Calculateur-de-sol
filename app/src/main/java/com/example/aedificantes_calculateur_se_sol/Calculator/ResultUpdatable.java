package com.example.aedificantes_calculateur_se_sol.Calculator;

public interface ResultUpdatable {

    /**
     * tell that parameter ar not set correctly to generate error then
     */
    public void allValuesAreSet();

    /**
     * tell that parameter are set correctly to allow use to load result page
     */
    public void allValuesAreNotSet();

    /**
     * when value change calculator data need to be upload
     */
    public void updateCalculator();



}
