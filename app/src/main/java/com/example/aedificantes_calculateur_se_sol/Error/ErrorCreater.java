package com.example.aedificantes_calculateur_se_sol.Error;

import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.Error;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.ErrorManager;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.ErrorOrder;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.ErrorParamSolManager;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.ErrorPieuxParamManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuParamManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Souterrain.Eaux_souterraines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class ErrorCreater {

    public ErrorCreater() {
    }

    public List<ErrorManager> generate_LineEmptyError(List<ParamSol> listParam){
        List<ErrorManager> listTamp = new ArrayList<>();
        for(int i=0; i < listParam.size(); i++){
            if(!listParam.get(i).isAllFill()){
                listTamp.add(new ErrorParamSolManager(listParam.get(i).generateError(),ErrorOrder.PARAMSOL,i));
            }
        }
        return listTamp;
    }

    public List<ErrorManager> generate_PieuEmptyError(PieuParamManager manager){
        List<ErrorManager> listTamp = new ArrayList<>();
        if(!manager.isFill()){
            listTamp.add( new ErrorPieuxParamManager(manager.generateError(), ErrorOrder.PIEUX));
        }
        return listTamp;
    }

    public List<ErrorManager> generate_EauxSouterainesEmptyError(Eaux_souterraines eaux_souterraines){
        List<ErrorManager> listTamp = new ArrayList<>();
        if(!eaux_souterraines.isFill()){
            listTamp.add( new ErrorPieuxParamManager(eaux_souterraines.generateError(), ErrorOrder.EAUX_SOUTERRAINES));
        }
        return listTamp;
    }




}
