package com.example.aedificantes_calculateur_se_sol.ParamPackage;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.ParamLayer;

public class GlobalParamActivator implements ParamVerificator{

    private ParamContainer paramContainer;

    public GlobalParamActivator(ParamContainer paramContainer) {
        this.paramContainer = paramContainer;
    }

    @Override
    public void manageParamActivator() {
        ParamContainerData containerData = this.paramContainer.get_ParamContainerData();
        for(ParamLayer each : paramContainer.getSol_list()){
            each.getParamEnabler().remove_forceParam();
        }
        if(containerData.getGroundWater_data().isChecked()){
            for(ParamLayer each : paramContainer.getSol_list()){
                each.getParamEnabler().forceParam_e(true);
            }
        }

    }
}
