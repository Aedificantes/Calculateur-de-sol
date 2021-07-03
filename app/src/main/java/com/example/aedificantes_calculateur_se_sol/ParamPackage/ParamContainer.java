package com.example.aedificantes_calculateur_se_sol.ParamPackage;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.ParamLayer;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.ParamLayerData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ScrewPile.ScrewPileParamManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.GroundWater.GroundWater;

import java.util.ArrayList;

/**
 * permet d'encapsuler (contenir) l'ensemble des classes de paramètres necéssaire aux calculs
 * les classes représentées sont celle permettant la gestion des éléments affichages
 */
public class ParamContainer {

    ArrayList<ParamLayer> sol_list;
    ScrewPileParamManager pieuManager;
    GroundWater groundWater;

    public ParamContainer(ArrayList<ParamLayer> sol_list, ScrewPileParamManager pieuManager, GroundWater groundWater) {
        this.sol_list = sol_list;
        this.pieuManager = pieuManager;
        this.groundWater = groundWater;
    }

    public ArrayList<ParamLayer> getSol_list() {
        return sol_list;
    }

    public void setSol_list(ArrayList<ParamLayer> sol_list) {
        this.sol_list = sol_list;
    }

    public ScrewPileParamManager getPieuManager() {
        return pieuManager;
    }

    public void setPieuManager(ScrewPileParamManager pieuManager) {
        this.pieuManager = pieuManager;
    }

    public ParamContainerData get_ParamContainerData(){
        return new ParamContainerData(listOfDataParamSol(), this.pieuManager.generate_pieuParamData(), this.groundWater.generate_eauxSouterrainesData());
    }

    public GroundWater getGroundWater() {
        return groundWater;
    }

    public void setGroundWater(GroundWater groundWater) {
        this.groundWater = groundWater;
    }

    private ArrayList<ParamLayerData> listOfDataParamSol(){
        ArrayList<ParamLayerData> listDataParamSol = new ArrayList<>();
        for(ParamLayer each : this.sol_list){
            listDataParamSol.add(each.getData());
        }
        return listDataParamSol;
    }
}
