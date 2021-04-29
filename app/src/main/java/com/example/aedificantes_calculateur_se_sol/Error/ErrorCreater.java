package com.example.aedificantes_calculateur_se_sol.Error;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuParamManager;

import java.util.HashMap;
import java.util.List;

public class ErrorCreater {

    public ErrorCreater() {
    }

    public HashMap<String, List<Error>> generate_LineEmptyError(List<ParamSol> listParam){
        HashMap<String, List<Error>> hashTamp = new HashMap<>();
        for(int i=0; i < listParam.size(); i++){
            if(!listParam.get(i).isAllFill()){
                hashTamp.put("Ligne n°"+i,listParam.get(i).generateError());
            }
        }
        return hashTamp;
    }

    public HashMap<String, List<Error>> generate_PieuEmptyError(PieuParamManager manager){
        HashMap<String, List<Error>> hashTamp = new HashMap<>();
        if(!manager.isFill()){
            hashTamp.put("Pieu", manager.generateError());
        }
        return hashTamp;
    }


}
