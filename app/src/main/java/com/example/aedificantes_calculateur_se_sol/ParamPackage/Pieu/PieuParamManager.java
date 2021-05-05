package com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu;

import android.view.View;
import android.widget.EditText;

import com.example.aedificantes_calculateur_se_sol.Error.Error;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObservable;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObserver;
import com.example.aedificantes_calculateur_se_sol.R;

import java.util.ArrayList;
import java.util.List;

public class PieuParamManager  implements VerificateObserver{
    private List<PieuParam> list_PieuParam = new ArrayList<>();
    private VerificateObservable verificator;

    public PieuParamManager(VerificateObservable verificator, View viewWhereElementArePLace) {
        this.verificator = verificator;
        this.verificator.addLikeObserver(this);
        //TODO get editText from layout and add to listof PieuParam
        list_PieuParam.add(new PieuParam((EditText) viewWhereElementArePLace.findViewById(R.id.ET_Dfut), "Dfut", verificator));
        list_PieuParam.add(new PieuParam((EditText) viewWhereElementArePLace.findViewById(R.id.ET_Dhel), "DHel",verificator));
        list_PieuParam.add(new PieuParam((EditText) viewWhereElementArePLace.findViewById(R.id.ET_Hk), "Hk",verificator));
        list_PieuParam.add(new PieuParam((EditText) viewWhereElementArePLace.findViewById(R.id.ET_Ip), "Ip",verificator));
        list_PieuParam.add(new PieuParam((EditText) viewWhereElementArePLace.findViewById(R.id.ET_H), "H",verificator));
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

    public float Dfut_val(){
        return this.list_PieuParam.get(0).value();
    }
    public float Dhel_val(){
        return this.list_PieuParam.get(1).value();
    }
    public float Hk_val(){
        return this.list_PieuParam.get(2).value();
    }
    public float Ip_val(){
        return this.list_PieuParam.get(3).value();
    }
    public float H_val(){
        return this.list_PieuParam.get(4).value();
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
