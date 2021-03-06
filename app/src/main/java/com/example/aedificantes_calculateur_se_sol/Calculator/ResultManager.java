package com.example.aedificantes_calculateur_se_sol.Calculator;

import android.provider.Settings;
import android.util.Log;

import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.TabBlockManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.GroundWater.GroundWater;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.GroundWater.GroundWater_data;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainerData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.ParamLayerData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.TypeSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ScrewPile.ScrewPileManagerData;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultManager {

    private static final String LOG_TAG = "CLA_112354";

    private ParamContainerData paramContainerData;

    private AlphaCalculator alphaCalculator = new AlphaCalculator();
    private LayerCalculator layerCalculator;
    private SoilResistanceCalculator resistanceSolCalculator = new SoilResistanceCalculator();
    private CoefLayer coefLayer = new CoefLayer();
    private GroundWater_calculator groundWater_calculator;


    public ResultManager(ParamContainerData data){
        this.paramContainerData = data ;
        layerCalculator = new LayerCalculator(data);
        groundWater_calculator = new GroundWater_calculator(data);
    }

    public float round(float value, int nbAfterComa){
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
    public TabBlockManager alpha_detail_tab() {
        return alphaCalculator.constructTab(layerCalculator.ParamSol_couchePortante().fi());
    }


    public float fd0_comp(){ // return ( alpha1 ?? cT + alpha2 ?? yT ?? (H+Hk) ) ?? Acomp //A =
        ParamLayerData couchePortante = layerCalculator.ParamSol_couchePortante();
        float calc = (getAlpha1() * couchePortante.cT() + getAlpha2() * AVG_masse_volumique_sols_sup??rieurs() * layerCalculator.profondeurPieu() ) * fd0_Acomp();
        System.out.println("ResultManager -> fd0_couche() detail: ("+getAlpha1()+" * "+couchePortante.cT()+" + "+getAlpha2()+" * "+AVG_masse_volumique_sols_sup??rieurs()+" * "+layerCalculator.profondeurPieu()+" ) * "+fd0_Acomp()+" = "+calc);
        return round(calc,2);
    }

    public float[] fd0_comp_toLayer(int index){//TODO on dev
        float[] resultArray = new float[2];
        ParamLayerData couche = paramContainerData.getSol_data_list().get(index);

        resultArray[0] = (getAlpha1() * couche.cT() + getAlpha2() * AVG_masse_volumique_sols_sup??rieurs() * layerCalculator.profondeurPieu() ) * fd0_Acomp();
        resultArray[1] = (getAlpha1() * couche.cT() + getAlpha2() * AVG_masse_volumique_sols_sup??rieurs() * layerCalculator.profondeurPieu() ) * fd0_Acomp();
        resultArray[0] = round(resultArray[0],2);
        resultArray[1] = round(resultArray[1],2);

        System.out.println("ResultManager -> fd0_couche() detail: ("+getAlpha1()+" * "+couche.cT()+" + "+getAlpha2()+" * "+AVG_masse_volumique_sols_sup??rieurs()+" * "+layerCalculator.profondeurPieu()+" ) * "+fd0_Acomp()+" = "+resultArray[0]);
        return resultArray;
    }

    public float fd0_Acomp(){ // return ?? ?? ( dhel /2 )2 =  xxx mm2 = 0.0xx m2
        float value =  (float) (Math.PI*(Math.pow((paramContainerData.getScrewPileManagerData().Dhel_val()/2),2)));
        value = Math.round(value/1000);
        value = value/1000;
        return value;
    }

    public float fd0_trac(){ // return ( alpha1 ?? cT + alpha2 ?? yT ?? (H+Hk) ) ?? Acomp //A =
        ParamLayerData couchePortante = layerCalculator.ParamSol_couchePortante();
        float calc = (getAlpha1() * couchePortante.cT() + getAlpha2() * AVG_masse_volumique_sols_sup??rieurs() * layerCalculator.profondeurPieu() ) * fd0_ATrac();
        System.out.println("ResultManager -> fd0_couche() detail: ("+getAlpha1()+" * "+couchePortante.cT()+" + "+getAlpha2()+" * "+AVG_masse_volumique_sols_sup??rieurs()+" * "+layerCalculator.profondeurPieu()+" ) * "+fd0_Acomp()+" = "+calc);

        return round(calc,2);
    }

    public float fd0_ATrac(){ // return ?? ?? ( dhel /2 )2 =  xxx mm2 = 0.0xx m2
        float value_dfut =  (float) (Math.PI*(Math.pow((paramContainerData.getScrewPileManagerData().Dfut_val()/2),2)));
        float value_dhel =  (float) (Math.PI*(Math.pow((paramContainerData.getScrewPileManagerData().Dhel_val()/2),2)));
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

    public float resistanceSol_couche_Tm(int index){
            ParamLayerData paramSolIndex = paramContainerData.getSol_data_list().get(index);
            double tamp = resistanceSolCalculator.resistanceSol_AVG(round(layerCalculator.profondeur_couche_index(index),2),paramSolIndex); // xx.xx Kpa
            System.out.print(" ResultManager -> resistanceSol_couche("+index+") -> detail: "+ tamp+"Kpa"+ " = ");
            tamp = (tamp/9.80665); // x.xxxxxxx T/m??
            System.out.print(tamp+"T/m??"+ " =~ ");
            tamp = Math.round(tamp*100); //x.xxxxxxx -> xxx
            tamp = tamp/100; // x.xx
            System.out.println(tamp+"T/m??");
            return (float) tamp;
    }

    public float resistanceSol_couche_Kpa(int index){
        ParamLayerData paramSolIndex = paramContainerData.getSol_data_list().get(index);
        double tamp = resistanceSolCalculator.resistanceSol_AVG(round(layerCalculator.profondeur_couche_index(index),2),paramSolIndex); // xx.xx Kpa
        System.out.print(" ResultManager -> resistanceSol_couche("+index+") -> detail: "+ tamp+"Kpa"+ " = ");
        return (float) tamp;
    }

    public String resistanceSol_couche_toDisplay(int index){
        if(paramContainerData.getSol_data_list().get(index).getTypeSol() == TypeSol.REMBLAI){
            return "-";
        }else{
            return String.valueOf(resistanceSol_couche_Tm(index));
        }
    }



    public float perimetre_section_transfersale_fut(){
        return (float) ((Math.PI*paramContainerData.getScrewPileManagerData().Dfut_val())/1000);
    }
    public String perimetre_section_transfersale_fut_toDisplay(){
        float tamp = perimetre_section_transfersale_fut();
        tamp = round(tamp, 2);
        return String.valueOf(tamp);
    }

    public float fdf(){
        float fdf = round(perimetre_section_transfersale_fut(),2) * resistance_AVG_long_du_fut() * (paramContainerData.getScrewPileManagerData().Ip_val()/1000 - paramContainerData.getScrewPileManagerData().Dhel_val()/1000);
        fdf = round(fdf*100, 0);
        fdf = fdf/100;
        System.out.println(" ResultManager -> fdf() -> detail: "+round(perimetre_section_transfersale_fut(),2)+" * "+resistance_AVG_long_du_fut()+ " * ("+paramContainerData.getScrewPileManagerData().Ip_val()/1000 + " - "+paramContainerData.getScrewPileManagerData().Dhel_val()/1000+") = "+fdf);
        return fdf ;
    }

    public float[] fdf_toLayer(int index){
        float[] resultArray = new float[2];
        resultArray[0] = round(perimetre_section_transfersale_fut(),2) * resistance_long_du_fut_hauteur_couche(index)[0] * (paramContainerData.getScrewPileManagerData().Ip_val()/1000 - paramContainerData.getScrewPileManagerData().Dhel_val()/1000);
        resultArray[1] = round(perimetre_section_transfersale_fut(),2) * resistance_long_du_fut_hauteur_couche(index)[1] * (paramContainerData.getScrewPileManagerData().Ip_val()/1000 - paramContainerData.getScrewPileManagerData().Dhel_val()/1000);

        resultArray[0] = round(resultArray[0]*100, 0);
        resultArray[0] = resultArray[0]/100;
        resultArray[1] = round(resultArray[1]*100, 0);
        resultArray[1] = resultArray[1]/100;

        Log.d(" ResultManager"," -> fdf_toLayer() -> detail: "+round(perimetre_section_transfersale_fut(),2)+" * "+resistance_long_du_fut_hauteur_couche(index)[0]+ " * ("+paramContainerData.getScrewPileManagerData().Ip_val()/1000 + " - "+paramContainerData.getScrewPileManagerData().Dhel_val()/1000+") = "+resultArray[0] +"" +
                "\n "+round(perimetre_section_transfersale_fut(),2)+" * "+resistance_long_du_fut_hauteur_couche(index)[1]+ " * ("+paramContainerData.getScrewPileManagerData().Ip_val()/1000 + " - "+paramContainerData.getScrewPileManagerData().Dhel_val()/1000+") = "+resultArray[1]);

        return resultArray ;
    }

    public String fdf_toDisplay(){
        return String.valueOf(fdf());
    }

    public String fdcomp_toDisplay(){
        float calc = (fdf()+fd0_comp())*getCoef_comp();
        Log.d(LOG_TAG," ResultManager -> fdcomp_toDisplay() -> formule: getCoef_comp()*(fdf()+fd0_comp()) , detail: "+getCoef_comp()+" * ("+fdf() +" + "+fd0_comp()+") = "+calc);
        calc = round(calc,2);
        return String.valueOf(calc);
    }
    public float fdcomp(){
        float calc = (fdf()+fd0_comp())*getCoef_comp();
        Log.d(LOG_TAG," ResultManager -> fdcomp_toDisplay() -> formule: getCoef_comp()*(fdf()+fd0_comp()) , detail: "+getCoef_comp()+" * ("+fdf() +" + "+fd0_comp()+") = "+calc);
        calc = round(calc,2);
        return calc;
    }

    public float[] fdcomp_tolayer_toDisplay(int index){
        float[] logArray = new float[3];
        float[] resultArray = new float[2];

        resultArray[0] = (fdf_toLayer(index)[0]+fd0_comp_toLayer(index)[0])*getCoef_comp();
        resultArray[0] = (fdf_toLayer(index)[0]+fd0_comp_toLayer(index)[1])*getCoef_comp();

        return resultArray;
    }

    public String fdtrac_toDisplay(){
        float calc = (fdf()+fd0_trac())*getCoef_trac();
        System.out.println(" ResultManager -> fdtrac_toDisplay() -> detail: "+getCoef_trac()+" * ("+fd0_trac()+" + "+fdf()+") = "+calc);
        calc = round(calc,2);
        return String.valueOf(calc);
    }

    public String fdvar_toDisplay(){
        float calc = (fdf()+fd0_trac())*getCoef_variableLoad();
        calc = round(calc,2);
        System.out.println(" ResultManager -> fdvar_toDisplay() -> detail: "+getCoef_variableLoad()+" * ("+fd0_trac()+" + "+fdf()+") = "+calc);
        return String.valueOf(calc);
    }


    public float resistance_AVG_long_du_fut(){
        float tamp =0f;
        float sum_enfoncement =0f;
        float[][] logArray = new float[layerCalculator.index_couchePortante()][2];
        if(layerCalculator.index_couchePortante() == 1){
            tamp = resistanceSol_couche_Tm(0);
            System.out.println(" ResultManager -> resistance_AVG_long_du_fut() -> detail :("+  resistanceSol_couche_Tm(0)+")");
            return tamp;
        }else {
            for (int i = 0; i < layerCalculator.index_couchePortante(); i++) {
                if (i == layerCalculator.index_couchePortante()-1){//paramContainerData.getSol_data_list().size() - 1) {
                    logArray[i][0] = (layerCalculator.enfoncement_couche_index(i) - paramContainerData.getScrewPileManagerData().Dhel_val()) / 1000;
                } else {
                    logArray[i][0] = layerCalculator.enfoncement_couche_index(i) / 1000;
                }
                logArray[i][1] =  resistanceSol_couche_Tm(i);
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

    public float[] resistance_long_du_fut_hauteur_couche(int index){
        float[] logArray = new float[3];
        float[] resultArray = new float[2];
        if (index == layerCalculator.index_couchePortante()-1){//paramContainerData.getSol_data_list().size() - 1) {
            logArray[0] = (layerCalculator.accumulation_enfoncement_couche_index(index) - paramContainerData.getScrewPileManagerData().Dhel_val()) / 1000;
        } else {
            logArray[0] = layerCalculator.accumulation_enfoncement_couche_index(index) / 1000;
        }

        if(index == 0){
            logArray[1] = 0;
        }else{
            logArray[1] = layerCalculator.accumulation_enfoncement_couche_index(index-1)/1000;
        }
        logArray[2] = resistanceSol_couche_Tm(index);

        resultArray[0] = round(logArray[1] * logArray[2], 2);
        resultArray[1] = round(logArray[0] * logArray[2], 2);

        System.out.println(" ResultManager -> resistance_long_du_fut_hauteur_couche("+index+") -> detail : resultArray[("+logArray[1]+"*"+logArray[2]+") , ("+logArray[0]+"*"+logArray[2]+")] : resultArray["+resultArray[0]+","+resultArray[1]+"]");
        return resultArray;
    }

    public float AVG_masse_volumique_sols_sup??rieurs(){
        float tamp =0f;
        float sum_enfoncement =0f;
        ArrayList<float[]> log_layer = new ArrayList<>();
        if(layerCalculator.index_couchePortante() == 1){
            ParamLayerData couchePortante = layerCalculator.ParamSol_couchePortante();
            tamp = couchePortante.yT() ;
            System.out.println(" ResultManager -> AVG_masse_volumique_sols_sup??rieurs() -> detail :("+ couchePortante.yT()+")");
            return tamp;
        }else {
            if(paramContainerData.getGroundWater_data().isChecked()){
                GroundWater_data groundWater_data = paramContainerData.getGroundWater_data();

                for (int i = 0; i < layerCalculator.index_couchePortante(); i++) {
                    ParamLayerData paramLayerData = paramContainerData.getSol_data_list().get(i);
                    float startHeight;
                    sum_enfoncement += layerCalculator.enfoncement_couche_index(i);

                    if(i == 0) {
                        startHeight = paramContainerData.getScrewPileManagerData().Hk_val();
                    }else{
                        startHeight = layerCalculator.accumulation_enfoncement_couche_index(i-1) + paramContainerData.getScrewPileManagerData().Hk_val();
                    }

                    if(groundWater_data.getNes() <= startHeight) {
                        if(groundWater_data.getAquifere() <= startHeight){
                            log_layer.add(new float[]{layerCalculator.enfoncement_couche_index(i), paramContainerData.getSol_data_list().get(i).yT()});
                        }else if(groundWater_data.getAquifere() < layerCalculator.accumulation_enfoncement_couche_index(i)){
                            log_layer.add(new float[]{groundWater_data.getAquifere() - startHeight, ysb(i)});
                            log_layer.add(new float[]{layerCalculator.enfoncement_couche_index(i) - (groundWater_data.getAquifere() - startHeight), paramContainerData.getSol_data_list().get(i).yT()});
                        }else{
                            log_layer.add(new float[]{layerCalculator.enfoncement_couche_index(i), ysb(i)});
                        }
                    }else if(groundWater_data.getNes() < layerCalculator.accumulation_enfoncement_couche_index(i)){
                        if(groundWater_data.getAquifere() < layerCalculator.accumulation_enfoncement_couche_index(i)){
                            log_layer.add(new float[]{(groundWater_data.getNes() - startHeight), paramContainerData.getSol_data_list().get(i).yT()});
                            log_layer.add(new float[]{groundWater_data.getAquifere() - groundWater_data.getNes(), ysb(i)});
                            log_layer.add(new float[]{layerCalculator.accumulation_enfoncement_couche_index(i) - (groundWater_data.getAquifere() - startHeight), paramContainerData.getSol_data_list().get(i).yT()});
                        }else{
                            log_layer.add(new float[]{(groundWater_data.getNes() - startHeight), paramContainerData.getSol_data_list().get(i).yT()});
                            log_layer.add(new float[]{layerCalculator.accumulation_enfoncement_couche_index(i) -(groundWater_data.getNes() - startHeight) , ysb(i)});
                        }
                    }else{
                        log_layer.add(new float[]{layerCalculator.enfoncement_couche_index(i), paramContainerData.getSol_data_list().get(i).yT()});
                    }
                }

                for(float[] each : log_layer){
                    tamp += each[0] * each[1];
                    Log.d("CODE14758 Sub Layer ","h = "+each[0]+"  y = "+each[1]);
                }

            }else{
                for (int i = 0; i < layerCalculator.index_couchePortante(); i++) {
                    float [] logArray = new float[2];
                    logArray[0] = layerCalculator.enfoncement_couche_index(i) / 1000;
                    logArray[1] = paramContainerData.getSol_data_list().get(i).yT();
                    tamp += logArray[0] * logArray[1];
                    sum_enfoncement += layerCalculator.enfoncement_couche_index(i) / 1000;
                    log_layer.add(logArray);
                }
            }
            System.out.print(" ResultManager -> AVG_masse_volumique_sols_sup??rieurs() -> detail :(");
            for(float[] eachLog : log_layer){
                System.out.print( eachLog[0]+" * "+eachLog[1] + " + ");
            }
            System.out.println(") / "+sum_enfoncement +" = "+ tamp+" / "+sum_enfoncement +" = "+round(tamp / sum_enfoncement,2));
            return round(tamp / sum_enfoncement,2);
        }

    }

    protected float ysb(int index){
        float ysb;
        ParamLayerData dataLayer  = paramContainerData.getSol_data_list().get(index);
        float coef  = (dataLayer.getTypeSol() == TypeSol.SABLEUX)? 2.65f:2.75f;
        ysb = (coef -1)/(1+dataLayer.e());
        Log.d("CODE56842 ysb"+index,"ysb = ("+coef+"-1)/(1+"+dataLayer.e()+") = "+ysb);
        return round(ysb,2);
    }

    public float AVG_masse_volumique_sols_sup??rieurs_toLayer(int index){ //TODO CALCULATE WITH LAYER GIVEN
        float tamp =0f;
        float sum_enfoncement =0f;
        float[][] logArray = new float[index+1][2];
        if(index == 0){
            ParamLayerData couchePortante = layerCalculator.ParamSol_couchePortante();
            tamp = couchePortante.yT() ;
            System.out.println(" ResultManager -> AVG_masse_volumique_sols_sup??rieurs() -> detail :("+ couchePortante.yT()+")");
            return tamp;
        }else {
            for (int i = 0; i < layerCalculator.index_couchePortante(); i++) {
                logArray[i][0] = paramContainerData.getSol_data_list().get(i).yT();
                logArray[i][1] = layerCalculator.enfoncement_couche_index(i)/1000;
                tamp += logArray[i][0] * logArray[i][1];
                sum_enfoncement += layerCalculator.enfoncement_couche_index(i) / 1000;
            }
            System.out.print(" ResultManager -> AVG_masse_volumique_sols_sup??rieurs() -> detail :(");
            for(int i =0; i < logArray.length; i++){
                System.out.print( logArray[i][0]+" * "+logArray[i][1] + " + ");
            }
            System.out.println(") / "+sum_enfoncement +" = "+ tamp+" / "+sum_enfoncement +" = "+round(tamp / sum_enfoncement,2));
            return round(tamp / sum_enfoncement,2);
        }

    }

    public float diff_H_Ip_Pieu(){
        return paramContainerData.getScrewPileManagerData().Ip_val() - paramContainerData.getScrewPileManagerData().H_val();
    }

    public float getCoef_comp(){
        try {
            return coefLayer.getCoef_comp(layerCalculator.ParamSol_couchePortante());
        }catch(RemblaiSupportLayerException ex){
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public float getCoef_trac(){
        try {
            return coefLayer.getCoef_trac(layerCalculator.ParamSol_couchePortante());
        }catch(RemblaiSupportLayerException ex){
            System.out.println(ex.getMessage());
        }
        return 0;
    }

    public float getCoef_variableLoad(){
        try {
            return coefLayer.getCoef_variableLoad(layerCalculator.ParamSol_couchePortante());
        }catch(RemblaiSupportLayerException ex){
            System.out.println(ex.getMessage());
        }
        return 0;
    }


    public void updateData(ParamContainerData dataUpdate){ // propagation du changement de donn??es
        this.paramContainerData = dataUpdate;
        this.layerCalculator.updateData(dataUpdate);
    }



    public AlphaCalculator getAlphaCalculator() {
        return alphaCalculator;
    }

    public LayerCalculator getLayerCalculator() {
        return layerCalculator;
    }

    public List<ParamLayerData> getParamSolDataList() {
        return paramContainerData.getSol_data_list();
    }

    public ScrewPileManagerData getScrewPileManagerData(){
        return paramContainerData.getScrewPileManagerData();
    }

    public ParamContainerData getParamContainerData(){
        return this.paramContainerData;
    }

    public SoilResistanceCalculator getSoilResistanceCalculator() {
        return resistanceSolCalculator;
    }

    public CoefLayer getCoefLayer() { return coefLayer; }
}
