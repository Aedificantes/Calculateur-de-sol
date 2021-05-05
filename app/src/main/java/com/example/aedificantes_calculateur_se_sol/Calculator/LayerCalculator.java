package com.example.aedificantes_calculateur_se_sol.Calculator;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuParamManager;

import java.util.List;

public class LayerCalculator {
    private List<ParamSol> paramSolList;
    private PieuParamManager pieuParamManager;

    public LayerCalculator(List<ParamSol> paramSolList, PieuParamManager pieuParamManager) {
        this.paramSolList = paramSolList;
        this.pieuParamManager = pieuParamManager;
    }

    public int index_couchePortante(){
        float sum = 0f;
        int index =1;
        for(ParamSol each_PS : paramSolList){
            sum += each_PS.h()*1000;
            if(pieuParamManager.H_val() < sum) {
                return index;
            }
            index++;
        }
        return paramSolList.size(); //Si aucune correspondance on renvoi le dernier
    }

    public ParamSol ParamSol_couchePortante(){
        return this.paramSolList.get(index_couchePortante()-1);
    }

    public float profondeurPieu(){
        return pieuParamManager.H_val()/1000 + pieuParamManager.Hk_val()/1000;
    }

    public float profondeur_couche_index(int indexCouche){//TODO faire apparaitre une exception en cas de valeur hors des couches de pose du pieu
        float size = 0f;
        System.out.println(" LayerCalculator -> profondeur_couche_index("+indexCouche+")");
        if(indexCouche == 0){
           size = (enfoncement_couche_index(indexCouche)/1000)/2 + pieuParamManager.Hk_val()/1000;
            System.out.println(" LayerCalculator -> profondeur_couche_index("+indexCouche+") detail: "+
                            enfoncement_couche_index(indexCouche)/1000+"  "+
                            pieuParamManager.Hk_val()/1000
                    );
        }else{
            size = (enfoncement_couche_index(indexCouche)/1000)/2 + accumulation_h_couche_index(indexCouche-1)/1000;
            System.out.println(" LayerCalculator -> profondeur_couche_index("+indexCouche+") detail: "+
                    enfoncement_couche_index(indexCouche)/1000+"  "+
                    accumulation_h_couche_index(indexCouche-1)/1000
            );
        }
        System.out.println(" LayerCalculator -> profondeur_couche_index("+indexCouche+") return: "+size);
        return  size;
    }

    public float enfoncement_couche_index(int indexCouche){//TODO faire apparaitre une exception en cas de valeur hors des couches de pose du pieu
        float sizePieuCalc;
        System.out.println(" LayerCalculator -> enfoncement_couche_index("+indexCouche+")");
        if(pieuParamManager.H_val() > accumulation_h_couche_index(indexCouche)){
            if(indexCouche == 0){
                sizePieuCalc = paramSolList.get(0).h()*1000 - pieuParamManager.Hk_val();
                System.out.println("1 -e ");
            }else {
                sizePieuCalc = paramSolList.get(indexCouche).h() * 1000;
                System.out.println("2 -e" );
            }
        }else{
            if(indexCouche == 0){
                sizePieuCalc = pieuParamManager.H_val() - pieuParamManager.Dhel_val();
                System.out.println("3 -e");
            }else {
                sizePieuCalc = pieuParamManager.H_val() - accumulation_enfoncement_couche_index(indexCouche - 1) - pieuParamManager.Dhel_val();
                System.out.println("4 -e" );
            }
        }

        System.out.println(" LayerCalculator -> enfoncement_couche_index("+indexCouche+") return: "+sizePieuCalc);

        return sizePieuCalc;


    }

    public float accumulation_h_couche_index(int index){
        System.out.println(" LayerCalculator -> accumulation_h_couche_index("+index+")");
        float size = 0f;
        if(index < paramSolList.size()-1){
            for (int i = 0; i <= index; i++) {
                size += paramSolList.get(i).h()*1000;
            }
        }else{
            size =  Float.POSITIVE_INFINITY;
        }
        System.out.println(" LayerCalculator -> accumulation_h_couche_index("+index+") return: "+size);
        return size;
    }

    public float accumulation_enfoncement_couche_index(int index){
        System.out.println(" LayerCalculator -> accumulation_enfoncement_couche_index("+index+")");
        float size =0f;
        for(int i =0; i <= index; i++){
            size += enfoncement_couche_index(i);
        }
        System.out.println(" LayerCalculator -> accumulation_enfoncement_couche_index("+index+") return: "+size);
        return size;
    }
}
