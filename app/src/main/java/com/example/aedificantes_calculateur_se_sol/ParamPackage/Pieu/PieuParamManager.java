package com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu;

import com.example.aedificantes_calculateur_se_sol.Error.Error;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObservable;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObserver;

import java.util.ArrayList;
import java.util.List;

public class PieuParamManager  implements VerificateObserver{
    private List<PieuParam> list_PieuParam = new ArrayList<>();
    private VerificateObservable verificator;

    public PieuParamManager(VerificateObservable verificator) {
        this.verificator = verificator;
        this.verificator.addLikeObserver(this);
        //TODO get editText from layout and add to listof PieuParam

    }

    public List<Error> generateError(){
        ArrayList<Error> list =  new ArrayList<>();
        for(PieuParam each : list_PieuParam){
            if(!each.isFill()){
                list.add(each.generateError());
            }
        }
        return list;
    }

    @Override
    public boolean isFill() {
        for(PieuParam each_PP : list_PieuParam){
            if(!each_PP.isFill())
                return false;
        }
        return true;
    }
}
