package com.example.aedificantes_calculateur_se_sol.ParamPackage;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuManagerData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Souterrain.EauxSouterraines_data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * permet d'encapsuler (contenir) l'ensemble des classes de paramètres necéssaire aux calculs
 * les classes représentées sont celle permettant la gestion des données des éléments
 */
public class ParamContainerData implements Serializable {

    ArrayList<ParamSolData> sol_data_list;
    PieuManagerData pieuManagerData;
    EauxSouterraines_data eauxSouterraines_data;

    public ParamContainerData(ArrayList<ParamSolData> sol_data_list, PieuManagerData pieuManagerData, EauxSouterraines_data eauxSouterraines_data) {
        this.sol_data_list = sol_data_list;
        this.pieuManagerData = pieuManagerData;
        this.eauxSouterraines_data = eauxSouterraines_data;
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

    public EauxSouterraines_data getEauxSouterraines_data() {
        return eauxSouterraines_data;
    }

    public void setEauxSouterraines_data(EauxSouterraines_data eauxSouterraines_data) {
        this.eauxSouterraines_data = eauxSouterraines_data;
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
