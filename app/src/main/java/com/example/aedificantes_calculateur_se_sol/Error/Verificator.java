package com.example.aedificantes_calculateur_se_sol.Error;

import com.example.aedificantes_calculateur_se_sol.Calculator.ResultUpdatable;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.GlobalParamActivator;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamVerificator;

import java.util.ArrayList;
import java.util.List;

/**
 * Observable Object that verify is all parameter are fill
 */
public class Verificator implements  VerificateObservable{

    private ResultUpdatable updater;
    private ParamVerificator paramVerificator;
    private List<VerificateObserver> list_observer = new ArrayList<>();

    public Verificator(ResultUpdatable updater) {
        this.updater = updater;
    }

    @Override
    public void notifyDataChange() {
        updater.updateCalculator();
        paramVerificator.manageParamActivator();
        for(VerificateObserver each_obs : list_observer){
            if(!each_obs.isFill()) {
                updater.allValuesAreNotSet();
                return;
            }
        }
        updater.allValuesAreSet();
    }

    @Override
    public void addLikeObserver(VerificateObserver obs) {
        this.list_observer.add(obs);
    }

    @Override
    public void setGlobalParamActivator(GlobalParamActivator globalParamActivator) {
        this.paramVerificator = globalParamActivator;
    }


}
