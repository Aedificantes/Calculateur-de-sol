package com.example.aedificantes_calculateur_se_sol.ParamPackage;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.ParamLayer;
import com.example.aedificantes_calculateur_se_sol.ui.home.HomeFragment;

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
        if(containerData.getScrewPileManagerData().isFill()){
            for(ParamLayer each: paramContainer.getSol_list()){
                each.setLoadLayer(false);
            }
            HomeFragment.resultManager.getLayerCalculator().ParamSol_couchePortante().setLoadLayer(true);

            //updateAll_ET_Element(); dans lineProfilSol le param disait de mettre à jour les ParamEnabler, vérifier si c'est toujours utile de l'appeler explicitement
        }

    }
}
