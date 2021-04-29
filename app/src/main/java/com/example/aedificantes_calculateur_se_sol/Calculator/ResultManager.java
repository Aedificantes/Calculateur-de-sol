package com.example.aedificantes_calculateur_se_sol.Calculator;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSol;

public class ResultManager {

    AlphaCalculator alphaCalculator = new AlphaCalculator();

    public ResultManager(){

    }

    public float getAlpha1(ParamSol paramSol) {
        return alphaCalculator.getAlpha1(paramSol.getParams().get(4));
    }

    public float getAlpha2(ParamSol paramSol) {
        return alphaCalculator.getAlpha2(paramSol.getParams().get(4));
    }
}
