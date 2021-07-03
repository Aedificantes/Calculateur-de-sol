package com.example.aedificantes_calculateur_se_sol.ParamPackage;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuManagerData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuParamManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Souterrain.Eaux_souterraines;

import java.util.ArrayList;

/**
 * permet d'encapsuler (contenir) l'ensemble des classes de paramètres necéssaire aux calculs
 * les classes représentées sont celle permettant la gestion des éléments affichages
 */
public class ParamContainer {

    ArrayList<ParamSol> sol_list;
    PieuParamManager pieuManager;
    Eaux_souterraines eaux_souterraines;

    public ParamContainer(ArrayList<ParamSol> sol_list, PieuParamManager pieuManager, Eaux_souterraines eaux_souterraines) {
        this.sol_list = sol_list;
        this.pieuManager = pieuManager;
        this.eaux_souterraines = eaux_souterraines;
    }

    public ArrayList<ParamSol> getSol_list() {
        return sol_list;
    }

    public void setSol_list(ArrayList<ParamSol> sol_list) {
        this.sol_list = sol_list;
    }

    public PieuParamManager getPieuManager() {
        return pieuManager;
    }

    public void setPieuManager(PieuParamManager pieuManager) {
        this.pieuManager = pieuManager;
    }

    public ParamContainerData get_ParamContainerData(){
        return new ParamContainerData(listOfDataParamSol(), this.pieuManager.generate_pieuParamData(), this.eaux_souterraines.generate_eauxSouterrainesData());
    }

    public Eaux_souterraines getEaux_souterraines() {
        return eaux_souterraines;
    }

    public void setEaux_souterraines(Eaux_souterraines eaux_souterraines) {
        this.eaux_souterraines = eaux_souterraines;
    }

    private ArrayList<ParamSolData> listOfDataParamSol(){
        ArrayList<ParamSolData> listDataParamSol = new ArrayList<>();
        for(ParamSol each : this.sol_list){
            listDataParamSol.add(each.getData());
        }
        return listDataParamSol;
    }
}
