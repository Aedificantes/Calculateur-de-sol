package com.example.aedificantes_calculateur_se_sol.ParamPackage.GroundWater;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.Error;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObservable;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObserver;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.EditTextManager;
import com.example.aedificantes_calculateur_se_sol.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Control every element in groundwater parameter
 */
public class GroundWater implements VerificateObserver {

    //private List<PieuParam> list_PieuParam = new ArrayList<>();
    private Switch enable_Param;
    private EditTextManager ETM_nes;
    private EditTextManager ETM_aquifere;
    private VerificateObservable verificator;

    public GroundWater(VerificateObservable verificator, View viewWhereElementArePLace) {
        this.verificator = verificator;
        this.verificator.addLikeObserver(this);
        enable_Param = viewWhereElementArePLace.findViewById(R.id.SWITCH_eaux_souterraines);

        ETM_nes = new EditTextManager((EditText)viewWhereElementArePLace.findViewById(R.id.ET_NES),"N.E.S",verificator );
        ETM_aquifere =  new EditTextManager((EditText)viewWhereElementArePLace.findViewById(R.id.ET_Aquifere),"Aquifère",verificator );

        if(enable_Param.isChecked()){
            ETM_nes.setEnabled(true);
            ETM_aquifere.setEnabled(true);
        }else{
            ETM_aquifere.setEnabled(false);
            ETM_nes.setEnabled(false);
        }

        enable_Param.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ETM_nes.setEnabled(true);
                    ETM_aquifere.setEnabled(true);
                }else{
                    ETM_aquifere.setEnabled(false);
                    ETM_nes.setEnabled(false);
                }
                verificator.notifyDataChange();
            }
        });

    }

    public void setValues(GroundWater_data groundWater_data) {
        this.enable_Param.setChecked(groundWater_data.isChecked());
        this.ETM_nes.setValue(groundWater_data.getNes());
        this.ETM_aquifere.setValue(groundWater_data.getAquifere());
    }

    @Override
    public boolean isFill() {
        if(enable_Param.isChecked()){
           return ETM_nes.isFill() && ETM_aquifere.isFill();
        }else{
            return true;
        }
    }


    public List<Error> generateError() {
        ArrayList<Error> list =  new ArrayList<>();
            if(!ETM_aquifere.isFill()){
                list.add(ETM_aquifere.generateError());
            }
            if(!ETM_nes.isFill()){
                list.add(ETM_nes.generateError());
            }
        return list;
    }

    public GroundWater_data generate_eauxSouterrainesData() {
        return new GroundWater_data(enable_Param.isChecked(), ETM_nes.value(), ETM_aquifere.value());
    }
}
