package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol;

import java.io.Serializable;
import java.util.ArrayList;

public class ParamSolData implements Serializable {
    private Compacite compacite = Compacite.FRIABLE;
    private Granularite granularite = Granularite.GRAVELEUX;
    private TypeSol typeSol = TypeSol.REMBLAI;
    public ArrayList<Float> params = new ArrayList<>();
    public ArrayList<String> logNameParam = new ArrayList<>();
    private boolean isLoadLayer = false;


    public ParamSolData() {
    }

    public Compacite getCompacite() {
        return compacite;
    }

    public void setCompacite(Compacite compacite) {
        this.compacite = compacite;
    }

    public Granularite getGranularite() {
        return granularite;
    }

    public void setGranularite(Granularite granularite) {
        this.granularite = granularite;
    }

    public TypeSol getTypeSol() {
        return typeSol;
    }

    public void setTypeSol(TypeSol typeSol) {
        this.typeSol = typeSol;
    }

    public ArrayList<Float> getParams() {
        return params;
    }

    public void setParams(ArrayList<Float> params) {
        this.params = params;
    }

    public ArrayList<String> getLogNameParam() {
        return logNameParam;
    }

    public boolean isLoadLayer() {
        return isLoadLayer;
    }

    public void setLoadLayer(boolean loadLayer) {
        isLoadLayer = loadLayer;
    }

    public float Il(){
        return this.params.get(0);
    }
    public float e(){
        return this.params.get(1);
    }
    public float cT(){
        return this.params.get(2);
    }
    public float fi(){
        return this.params.get(3);
    }
    public float yT(){
        return this.params.get(4);
    }
    public float Sr(){
        return this.params.get(5);
    }
    public float h(){ // en metre
        return this.params.get(6);
    }

    public void setLogNameParam(ArrayList<String> logNameParam) {
        this.logNameParam = logNameParam;
    }
}
