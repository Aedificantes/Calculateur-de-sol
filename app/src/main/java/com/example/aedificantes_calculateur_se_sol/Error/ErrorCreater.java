package com.example.aedificantes_calculateur_se_sol.Error;

import android.util.Log;

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

    private static final String LOG_TAG  = "CLA_3465";

    public ErrorCreater() {
    }

    public List<ErrorManager> generate_LineError(List<ParamLayer> listParam){
        List<ErrorManager> listTamp = new ArrayList<>();
        for(int i=0; i < listParam.size(); i++){
            List<Error> listError = new ArrayList<>();
            /*
            if(!listParam.get(i).isAllFill()){
                listError.addAll(listParam.get(i).generateEmptyError());
            }
            listError.addAll(listParam.get(i).generateMathError());
             */
            listError.addAll(listParam.get(i).generateError());
            Log.d(LOG_TAG,"finally line "+i+ " has "+listError.size()+" error ");
            if(!listError.isEmpty()) {
                listTamp.add(new ErrorParamSolManager(listError, ErrorOrder.PARAMSOL, i));
            }

        }
        Log.d(LOG_TAG,"All ground line line has "+listTamp.size()+" errors ");
        return listTamp;
    }

    public List<ErrorManager> generate_PieuEmptyError(ScrewPileParamManager manager){
        List<ErrorManager> listTamp = new ArrayList<>();
        /*
        if(!manager.isFill()){
            listTamp.add( new ErrorPieuxParamManager(manager.generateError(), ErrorOrder.PIEUX));
        }
         */
        List<Error> errorList = new ArrayList<>(manager.generateError());
        if(!errorList.isEmpty()){
            listTamp.add( new ErrorPieuxParamManager(errorList, ErrorOrder.PIEUX));
        }
        return listTamp;
    }

    public List<ErrorManager> generate_EauxSouterainesEmptyError(GroundWater groundWater){
        List<ErrorManager> listTamp = new ArrayList<>();
        /*if(!groundWater.isFill()){
            listTamp.add( new ErrorPieuxParamManager(groundWater.generateError(), ErrorOrder.EAUX_SOUTERRAINES));
        }

         */
        List<Error> errorList = new ArrayList<>(groundWater.generateError());
        if(!errorList.isEmpty()){
            listTamp.add( new ErrorPieuxParamManager(errorList, ErrorOrder.EAUX_SOUTERRAINES));
        }
        return listTamp;
    }




}
