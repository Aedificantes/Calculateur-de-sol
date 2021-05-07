package com.example.aedificantes_calculateur_se_sol.Calculator;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.TypeSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuParamManager;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class ResultManager {


    private List<ParamSol> paramSolList;
    private PieuParamManager pieuParamManager;

    private AlphaCalculator alphaCalculator = new AlphaCalculator();
    private LayerCalculator layerCalculator;
    private ResistanceSolCalculator resistanceSolCalculator = new ResistanceSolCalculator();


    public ResultManager(List<ParamSol> paramSolList, PieuParamManager pieuParamManager){
        this.paramSolList = paramSolList;
        this.pieuParamManager = pieuParamManager;
        layerCalculator = new LayerCalculator(paramSolList,pieuParamManager);
    }

    private float round(float value, int nbAfterComa){
        int tenPower = (int) Math.pow(10,nbAfterComa);
        float tamp = Math.round(value*tenPower);
        return tamp/tenPower;
    }

    public float getAlpha1() {
        return alphaCalculator.getAlpha1(layerCalculator.ParamSol_couchePortante().fi());
    }

    public float getAlpha2() {
        return alphaCalculator.getAlpha2(layerCalculator.ParamSol_couchePortante().fi());
    }

    public float fd0_comp(){ // return ( alpha1 · cT + alpha2 · yT · (H+Hk) ) · Acomp //A =
        ParamSol couchePortante = layerCalculator.ParamSol_couchePortante();
        float calc = (getAlpha1() * couchePortante.cT() + getAlpha2() * AVG_masse_volumique_sols_supérieurs() * layerCalculator.profondeurPieu() ) * fd0_Acomp();
        System.out.println("ResultManager -> fd0_couche() detail: ("+getAlpha1()+" * "+couchePortante.cT()+" + "+getAlpha2()+" * "+AVG_masse_volumique_sols_supérieurs()+" * "+layerCalculator.profondeurPieu()+" ) * "+fd0_Acomp()+" = "+calc);
        return round(calc,2);
    }

    public float fd0_Acomp(){ // return π · ( dhel /2 )2 =  xxx mm2 = 0.0xx m2
        float value =  (float) (Math.PI*(Math.pow((pieuParamManager.Dhel_val()/2),2)));
        value = Math.round(value/1000);
        value = value/1000;
        return value;
    }

    public float fd0_trac(){ // return ( alpha1 · cT + alpha2 · yT · (H+Hk) ) · Acomp //A =
        ParamSol couchePortante = layerCalculator.ParamSol_couchePortante();
        float calc = (getAlpha1() * couchePortante.cT() + getAlpha2() * AVG_masse_volumique_sols_supérieurs() * layerCalculator.profondeurPieu() ) * fd0_ATrac();
        System.out.println("ResultManager -> fd0_couche() detail: ("+getAlpha1()+" * "+couchePortante.cT()+" + "+getAlpha2()+" * "+AVG_masse_volumique_sols_supérieurs()+" * "+layerCalculator.profondeurPieu()+" ) * "+fd0_Acomp()+" = "+calc);

        return round(calc,2);
    }

    public float fd0_ATrac(){ // return π · ( dhel /2 )2 =  xxx mm2 = 0.0xx m2
        float value_dfut =  (float) (Math.PI*(Math.pow((pieuParamManager.Dfut_val()/2),2)));
        float value_dhel =  (float) (Math.PI*(Math.pow((pieuParamManager.Dhel_val()/2),2)));
        value_dfut = value_dhel - value_dfut;
        value_dfut = Math.round(value_dfut/1000);
        value_dfut = value_dfut/1000;
        return value_dfut;
    }

    public String enfoncement_couche_index_ToDisplay(int index){
        float tamp = layerCalculator.enfoncement_couche_index(index); // xxxx.x
        tamp = Math.round(tamp/10); //xxx.xx -> xxx
        tamp = tamp/100; // x.xx
        return String.valueOf(tamp);

    }

    public float resistanceSol_couche(int index){ //TODO pour les sols argileux la formule est différentes ! à produire
            ParamSol paramSolIndex = paramSolList.get(index);
            double tamp = resistanceSolCalculator.resistanceSol_AVG(round(layerCalculator.profondeur_couche_index(index),2),paramSolIndex); // xx.xx Kpa
            System.out.print(" ResultManager -> resistanceSol_couche("+index+") -> detail: "+ tamp+"Kpa"+ " = ");
            tamp = (tamp/9.80665); // x.xxxxxxx T/m²
            System.out.print(tamp+"T/m²"+ " =~ ");
            tamp = Math.round(tamp*100); //x.xxxxxxx -> xxx
            tamp = tamp/100; // x.xx
            System.out.println(tamp+"T/m²");
            return (float) tamp;
    }

    public String resistanceSol_couche_toDisplay(int index){
        if(paramSolList.get(index).getTypeSol() == TypeSol.REMBLAI){
            return "-";
        }else{
            return String.valueOf(resistanceSol_couche(index));
        }
    }



    public float perimetre_section_transfersale_fut(){
        return (float) ((Math.PI*pieuParamManager.Dfut_val())/1000);
    }
    public String perimetre_section_transfersale_fut_toDisplay(){
        float tamp = perimetre_section_transfersale_fut();
        tamp = round(tamp, 2);
        return String.valueOf(tamp);
    }

    public float fdf(){
        float fdf = round(perimetre_section_transfersale_fut(),2) * resistance_AVG_long_du_fut() * (pieuParamManager.Ip_val()/1000 - pieuParamManager.Dhel_val()/1000);
        System.out.println(" ResultManager -> fdf() -> detail: "+round(perimetre_section_transfersale_fut(),2)+" * "+resistance_AVG_long_du_fut()+ " * ("+pieuParamManager.Ip_val()/1000 + " - "+pieuParamManager.Dhel_val()/1000+")");
        return fdf;
    }

    public String fdf_toDisplay(){
        float fdf = fdf();
        fdf = round(fdf*100, 0);
        return String.valueOf(fdf/100);
    }

    public float resistance_AVG_long_du_fut(){
        float tamp =0f;
        float sum_enfoncement =0f;
        float[][] logArray = new float[layerCalculator.index_couchePortante()][2];
        if(layerCalculator.index_couchePortante() == 1){
            tamp = resistanceSol_couche(0);
            System.out.println(" ResultManager -> resistance_AVG_long_du_fut() -> detail :("+ resistanceSol_couche(0)+")");
            return tamp;
        }else {
            for (int i = 0; i < layerCalculator.index_couchePortante(); i++) {
                if (i == paramSolList.size() - 1) {
                    logArray[i][0] = (layerCalculator.enfoncement_couche_index(i) - pieuParamManager.Dhel_val()) / 1000;
                } else {
                    logArray[i][0] = layerCalculator.enfoncement_couche_index(i) / 1000;
                }
                logArray[i][1] = resistanceSol_couche(i);
                tamp += logArray[i][0] * logArray[i][1];
                sum_enfoncement += layerCalculator.enfoncement_couche_index(i) / 1000;
            }
            System.out.print(" ResultManager -> resistance_AVG_long_du_fut() -> detail :(");
            for(int i =0; i < logArray.length; i++){
                System.out.print( logArray[i][0]+" * "+logArray[i][1] + " + ");
            }
            System.out.println(") / "+sum_enfoncement +" = "+ tamp+" / "+sum_enfoncement +" = "+(tamp / sum_enfoncement));
            return tamp / sum_enfoncement;
        }
    }

    public float AVG_masse_volumique_sols_supérieurs(){
        float tamp =0f;
        float sum_enfoncement =0f;
        float[][] logArray = new float[layerCalculator.index_couchePortante()][2];
        if(layerCalculator.index_couchePortante() == 1){
            ParamSol couchePortante = layerCalculator.ParamSol_couchePortante();
            tamp = couchePortante.yT() ;
            System.out.println(" ResultManager -> AVG_masse_volumique_sols_supérieurs() -> detail :("+ couchePortante.yT()+")");
            return tamp;
        }else {
            for (int i = 0; i < layerCalculator.index_couchePortante(); i++) {
                logArray[i][0] = paramSolList.get(i).yT();
                logArray[i][1] = layerCalculator.enfoncement_couche_index(i)/1000;
                tamp += logArray[i][0] * logArray[i][1];
                sum_enfoncement += layerCalculator.enfoncement_couche_index(i) / 1000;
            }
            System.out.print(" ResultManager -> AVG_masse_volumique_sols_supérieurs() -> detail :(");
            for(int i =0; i < logArray.length; i++){
                System.out.print( logArray[i][0]+" * "+logArray[i][1] + " + ");
            }
            System.out.println(") / "+sum_enfoncement +" = "+ tamp+" / "+sum_enfoncement +" = "+round(tamp / sum_enfoncement,2));
            return round(tamp / sum_enfoncement,2);
        }

    }







    public AlphaCalculator getAlphaCalculator() {
        return alphaCalculator;
    }

    public LayerCalculator getLayerCalculator() {
        return layerCalculator;
    }
}
