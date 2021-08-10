package com.example.aedificantes_calculateur_se_sol.Error;

import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.Error;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.ErrorManager;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.ErrorOrder;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.ErrorParamSolManager;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.ErrorPieuxParamManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.ParamLayer;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ScrewPile.ScrewPileParamManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.GroundWater.GroundWater;

import java.util.ArrayList;
import java.util.List;

public class ErrorCreater {

    public ErrorCreater() {
    }

    public List<ErrorManager> generate_LineError(List<ParamLayer> listParam){
        List<ErrorManager> listTamp = new ArrayList<>();
        for(int i=0; i < listParam.size(); i++){
            List<Error> listError = new ArrayList<>();
            if(!listParam.get(i).isAllFill()){
                listError.addAll(listParam.get(i).generateEmptyError());
            }
            listError.addAll(listParam.get(i).generateMathError());
            if(!listError.isEmpty()) {
                listTamp.add(new ErrorParamSolManager(listError, ErrorOrder.PARAMSOL, i));
            }

        }
        return listTamp;
    }

    public List<ErrorManager> generate_PieuEmptyError(ScrewPileParamManager manager){
        List<ErrorManager> listTamp = new ArrayList<>();
        if(!manager.isFill()){
            listTamp.add( new ErrorPieuxParamManager(manager.generateError(), ErrorOrder.PIEUX));
        }
        return listTamp;
    }

    public List<ErrorManager> generate_EauxSouterainesEmptyError(GroundWater groundWater){
        List<ErrorManager> listTamp = new ArrayList<>();
        if(!groundWater.isFill()){
            listTamp.add( new ErrorPieuxParamManager(groundWater.generateError(), ErrorOrder.EAUX_SOUTERRAINES));
        }
        return listTamp;
    }




}
