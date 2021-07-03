package com.example.aedificantes_calculateur_se_sol.ParamPackage;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.ParamLayerData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ScrewPile.ScrewPileManagerData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.GroundWater.GroundWater_data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * permet d'encapsuler (contenir) l'ensemble des classes de paramètres necéssaire aux calculs
 * les classes représentées sont celle permettant la gestion des données des éléments
 */
public class ParamContainerData implements Serializable {

    ArrayList<ParamLayerData> sol_data_list;
    ScrewPileManagerData screwPileManagerData;
    GroundWater_data groundWater_data;

    public ParamContainerData(ArrayList<ParamLayerData> sol_data_list, ScrewPileManagerData screwPileManagerData, GroundWater_data groundWater_data) {
        this.sol_data_list = sol_data_list;
        this.screwPileManagerData = screwPileManagerData;
        this.groundWater_data = groundWater_data;
    }

    public ArrayList<ParamLayerData> getSol_data_list() {
        return sol_data_list;
    }

    public void setSol_data_list(ArrayList<ParamLayerData> sol_data_list) {
        this.sol_data_list = sol_data_list;
    }

    public ScrewPileManagerData getScrewPileManagerData() {
        return screwPileManagerData;
    }

    public void setScrewPileManagerData(ScrewPileManagerData screwPileManagerData) {
        this.screwPileManagerData = screwPileManagerData;
    }

    public GroundWater_data getGroundWater_data() {
        return groundWater_data;
    }

    public void setGroundWater_data(GroundWater_data groundWater_data) {
        this.groundWater_data = groundWater_data;
    }

    @Override
    public String toString() {
        String dataSol_toString="";
        for(ParamLayerData each : sol_data_list){
            dataSol_toString+="\n"+each.toString();
        }
        return "ParamContainer{" +
                "sol_data_list=" + dataSol_toString +
                ",\n pieuManagerData=" + screwPileManagerData.toString() +
                '}';
    }
}
