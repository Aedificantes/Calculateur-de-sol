package com.example.aedificantes_calculateur_se_sol.Error;

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

    public List<ErrorManager> generate_LineEmptyError(List<ParamLayer> listParam){
        List<ErrorManager> listTamp = new ArrayList<>();
        for(int i=0; i < listParam.size(); i++){
            if(!listParam.get(i).isAllFill()){
                listTamp.add(new ErrorParamSolManager(listParam.get(i).generateError(),ErrorOrder.PARAMSOL,i));
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
