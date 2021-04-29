package com.example.aedificantes_calculateur_se_sol.Error;

import com.example.aedificantes_calculateur_se_sol.ParamSolPackage.ParamSol;

import java.util.HashMap;
import java.util.List;

public class ErrorCreater {

    public ErrorCreater() {
    }

    public HashMap<Integer, List<Error>> generate(List<ParamSol> listParam){
        HashMap<Integer, List<Error>> hashTamp = new HashMap<>();
        for(int i=0; i < listParam.size(); i++){
            if(!listParam.get(i).isAllFill()){
                hashTamp.put(i,listParam.get(i).generateError());
            }
        }
        return hashTamp;
    }
}
