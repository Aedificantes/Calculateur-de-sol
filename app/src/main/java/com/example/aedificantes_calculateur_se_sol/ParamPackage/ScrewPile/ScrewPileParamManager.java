package com.example.aedificantes_calculateur_se_sol.ParamPackage.ScrewPile;

import android.view.View;
import android.widget.EditText;

import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.Error;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObservable;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObserver;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.EditTextManager;
import com.example.aedificantes_calculateur_se_sol.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Manage every screw pile parameter in home activity
 */
public class ScrewPileParamManager implements VerificateObserver{
    private List<EditTextManager> list_PieuParam = new ArrayList<>();
    private VerificateObservable verificator;

    public ScrewPileParamManager(VerificateObservable verificator, View viewWhereElementArePLace) {
        this.verificator = verificator;
        this.verificator.addLikeObserver(this);
        list_PieuParam.add(new EditTextManager((EditText) viewWhereElementArePLace.findViewById(R.id.ET_Dfut), "Dfut", verificator));
        list_PieuParam.add(new EditTextManager((EditText) viewWhereElementArePLace.findViewById(R.id.ET_Dhel), "DHel",verificator));
        list_PieuParam.add(new EditTextManager((EditText) viewWhereElementArePLace.findViewById(R.id.ET_Ip), "Ip",verificator));
        list_PieuParam.add(new EditTextManager((EditText) viewWhereElementArePLace.findViewById(R.id.ET_Hk), "Hk",verificator));
        list_PieuParam.add(new EditTextManager((EditText) viewWhereElementArePLace.findViewById(R.id.ET_H), "H",verificator));
    }
    public void setValues(float[] tab) {
        int index =0;
        for(EditTextManager each: list_PieuParam){
            if(index <= tab.length) {
                each.setValue(tab[index]);
            }else{
                each.setValue(0);
            }
            index++;
        }
        verificator.notifyDataChange();
    }

    public void setValues(ScrewPileManagerData pieuData){
        list_PieuParam.get(0).setValue(pieuData.Dfut_val());
        list_PieuParam.get(1).setValue(pieuData.Dhel_val());
        list_PieuParam.get(2).setValue(pieuData.Ip_val());
        list_PieuParam.get(3).setValue(pieuData.Hk_val());
        list_PieuParam.get(4).setValue(pieuData.H_val());
        verificator.notifyDataChange();
    }

    public List<Error> generateError(){
        ArrayList<Error> list =  new ArrayList<>();
        for(EditTextManager each : list_PieuParam){
            if(!each.isFill()){
                list.add(each.generateError());
            }
        }
        return list;
    }

    public ScrewPileManagerData generate_pieuParamData(){
        return new ScrewPileManagerData(
                Dfut_val(),
                Dhel_val(),
                Ip_val(),
                Hk_val(),
                H_val()
        );
    }

    public float Dfut_val(){
        return this.list_PieuParam.get(0).value();
    }
    public float Dhel_val(){
        return this.list_PieuParam.get(1).value();
    }
    public float Hk_val(){
        return this.list_PieuParam.get(3).value();
    }
    public float Ip_val(){
        return this.list_PieuParam.get(2).value();
    }
    public float H_val(){
        return this.list_PieuParam.get(4).value();
    }


    @Override
    public boolean isFill() {
        for(EditTextManager each_PP : list_PieuParam){
            if(!each_PP.isFill())
                return false;
        }
        return true;
    }
}
