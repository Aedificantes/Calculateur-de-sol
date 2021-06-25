package com.example.aedificantes_calculateur_se_sol.ParamPackage;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuManagerData;

import java.io.Serializable;
import java.util.ArrayList;

public class ParamContainer implements Serializable {

    ArrayList<ParamSolData> sol_data_list;
    PieuManagerData pieuManagerData;

    public ParamContainer(ArrayList<ParamSolData> sol_data_list, PieuManagerData pieuManagerData) {
        this.sol_data_list = sol_data_list;
        this.pieuManagerData = pieuManagerData;
    }

    public ArrayList<ParamSolData> getSol_data_list() {
        return sol_data_list;
    }

    public void setSol_data_list(ArrayList<ParamSolData> sol_data_list) {
        this.sol_data_list = sol_data_list;
    }

    public PieuManagerData getPieuManagerData() {
        return pieuManagerData;
    }

    public void setPieuManagerData(PieuManagerData pieuManagerData) {
        this.pieuManagerData = pieuManagerData;
    }

    @Override
    public String toString() {
        String dataSol_toString="";
        for(ParamSolData each : sol_data_list){
            dataSol_toString+="\n"+each.toString();
        }
        return "ParamContainer{" +
                "sol_data_list=" + dataSol_toString +
                ",\n pieuManagerData=" + pieuManagerData.toString() +
                '}';
    }
}
