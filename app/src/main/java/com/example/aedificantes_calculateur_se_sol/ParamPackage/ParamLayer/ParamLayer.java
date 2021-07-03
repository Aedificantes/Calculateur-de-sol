package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer;

import android.util.Log;

import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.Error;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that manage element display in ReclyclerView ParamLayer
 */
public class ParamLayer {
    private static final String LOG_TAG = "CLA_04";
    private ParamLayerData data;

    private ParamEnabler paramEnabler;


    public ParamLayer() {
        paramEnabler = new ParamEnabler(this);
        data = new ParamLayerData();
        for(int i=0; i<7; i++) {
            data.params.add(0f);
        }
        addLogName();

    }
    public ParamLayer(TypeSol sol, Granularite granularite, Compacite comp, float... value) {
        data = new ParamLayerData();
        this.data.setTypeSol(sol);
        this.data.setGranularite(granularite);
        this.setCompacite(comp);
        for(float each: value){
            data.params.add(each);
        }
        for(int i=value.length; i<7; i++) {
            data.params.add(0f);
        }
        System.out.println("Create ParamSol with :"+data.params.size() + " values : details: "+data.params.toString());
        addLogName();

        paramEnabler = new ParamEnabler(this);
    }

    public ParamLayer(ParamLayerData data) {
        this.data = data;
        this.paramEnabler = new ParamEnabler(this);
        addLogName();
    }

    private void addLogName(){
        data.logNameParam.add("Facteur de plasticité des sols argileux");
        data.logNameParam.add("Indices des vides");
        data.logNameParam.add("Cohésion du sol");
        data.logNameParam.add("Angle de frottement interne des sols");
        data.logNameParam.add("Masse volumique de l'horizon");
        data.logNameParam.add("Indice d'humidite");
        data.logNameParam.add("Epaisseur de l'horizon");
    }

    public boolean isAllFill(){
        boolean returned = true;
        for(int each : data.paramToSet){
            if(data.params.get(each) == 0){
                return false;
            }
        }
        return returned;
    }
    public void setParamToSet(int... index){
        data.paramToSet.clear();
        for(int each: index){
            data.paramToSet.add(each);
        }
    }

    public float Il(){
        return this.data.Il();
    }
    public float e(){
        return this.data.e();
    }
    public float cT(){
        return this.data.cT();
    }
    public float fi(){
        return this.data.fi();
    }
    public float yT(){
        return this.data.yT();
    }
    public float Sr(){
        return this.data.Sr();
    }
    public float h(){ // en metre
        return this.data.h();
    }

    public void addParamToSet(int index){
        data.paramToSet.add(index);
    }

    public void setValueByIndex(int index, float value){
        data.params.set(index,value);
    }

    public ParamEnabler getParamEnabler() {
        return paramEnabler;
    }

    public ArrayList<Float> getParams() {
        return data.params;
    }

    public void setParamEnabler(ParamEnabler paramEnabler) {
        this.paramEnabler = paramEnabler;
    }

    public Compacite getCompacite() {
        return data.getCompacite();
    }

    public void setCompacite(Compacite compacite) { data.setCompacite(compacite); }

    public Granularite getGranularite() {
        return data.getGranularite();
    }

    public void setGranularite(Granularite granularite) {
        data.setGranularite(granularite);
    }

    public TypeSol getTypeSol() {
        return data.getTypeSol();
    }

    public void setTypeSol(TypeSol typeSol) {
        data.setTypeSol(typeSol);
    }

    public boolean isLoadLayer() { return data.isLoadLayer(); }

    public void setLoadLayer(boolean loadLayer) {
        data.setLoadLayer(loadLayer);
    }

    public ParamLayerData getData() {
        return data;
    }

    public List<Error> generateError() {
        ArrayList<Error> list =  new ArrayList<>();
        Log.e(LOG_TAG, "generateError nb value params: "+data.params.size()+"\n Param to enable: "+data.paramToSet.toString());
        for(int each : data.paramToSet){
            if(data.params.get(each) == 0){
                list.add(new Error(" - "+data.logNameParam.get(each)+" n'a pas de valeur"));
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return "ParamSol{" +
                "compacite=" + data.getCompacite() +
                ", granularite=" + data.getGranularite() +
                ", typeSol=" + data.getTypeSol() +
                ", params=" + data.params +
                ", paramToSet=" + data.paramToSet +
                ", paramEnabler=" + paramEnabler +
                '}';
    }

}