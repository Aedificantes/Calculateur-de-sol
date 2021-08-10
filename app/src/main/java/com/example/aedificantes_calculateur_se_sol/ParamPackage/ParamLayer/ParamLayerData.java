package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Like every other param classes ParamLayerData has his own data classes that can be serializable to be place in ParamContainerData and transfert to other Activity
 */
public class ParamLayerData implements Serializable {
    private Compacite compacite = Compacite.FRIABLE;
    private Granularite granularite = Granularite.GRAVELEUX;
    private TypeSol typeSol = TypeSol.REMBLAI;
    public ArrayList<Float> params = new ArrayList<>();
    public ArrayList<String> logNameParam = new ArrayList<>();
    public ArrayList<Integer> paramToSet = new ArrayList<>();
    private boolean isLoadLayer = false;


    public ParamLayerData() {
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

    public void set_Il(float val){
        if(params.size() == 0){
            fillToIndex(0);
        }
        this.params.set(0, val);
    }
    public void set_e(float val){
        if(params.size() == 1){
            fillToIndex(1);
        }
         this.params.set(1,val);
    }
    public void set_cT(float val){
        if(params.size() <= 2){
            fillToIndex(2);
        }
         this.params.set(2,val);
    }
    public void set_fi(float val){
        if(params.size() <= 3){
            fillToIndex(3);
        }
         this.params.set(3,val);
    }
    public void set_yT(float val){
        if(params.size() <= 4){
            fillToIndex(4);
        }
         this.params.set(4,val);
    }
    public void set_Sr(float val){
        if(params.size() <= 5){
            fillToIndex(5);
        }
         this.params.set(5,val);
    }
    public void set_h(float val){ // en metre
        if(params.size() <= 6){
            fillToIndex(6);
        }
         this.params.set(6,val);
    }

    private void fillToIndex(int index){
        for(int i=params.size()-1; i < index; i++ ){
            params.add(0f);
        }
    }

    public boolean isAllFill(){
        boolean returned = true;
        for(int each : paramToSet){
            if(params.get(each) == 0){
                return false;
            }
        }
        return returned;
    }

    public boolean is_Il_paramToSet(){
        return find_is_paramToSet(0);
    }

    public boolean is_e_paramToSet(){
        return find_is_paramToSet(1);
    }

    public boolean is_CT_paramToSet(){
        return find_is_paramToSet(2);
    }

    public boolean is_FI_paramToSet(){
        return find_is_paramToSet(3);
    }

    public boolean is_YT_paramToSet(){
        return find_is_paramToSet(4);
    }

    public boolean is_SR_paramToSet(){
        return find_is_paramToSet(5);
    }

    public boolean is_h_paramToSet(){
       return find_is_paramToSet(6);
    }

    public boolean find_is_paramToSet(int indexParam){
        for(int each : paramToSet){
            if(each == indexParam){
                return true;
            }
        }
        return false;
    }

    public void setLogNameParam(ArrayList<String> logNameParam) {
        this.logNameParam = logNameParam;
    }

    public JSONObject convert_to_json() throws JSONException {
        JSONObject solDataJSON = new JSONObject();
        solDataJSON.put("TYPE_SOL", this.getTypeSol().getIndice());
        solDataJSON.put("GRANULARITE", this.getGranularite().getIndice());
        solDataJSON.put("COMPACITE", this.getCompacite().getIndice());

        solDataJSON.put("E", this.e());
        solDataJSON.put("CT", this.cT());
        solDataJSON.put("FI", this.fi());
        solDataJSON.put("H", this.h());
        solDataJSON.put("IL", this.Il());
        solDataJSON.put("SR", this.Sr());
        solDataJSON.put("YT", this.yT());

        return solDataJSON;
    }

    @Override
    public String toString() {
        return "ParamSolData{" +
                "compacite=" + compacite +
                ", granularite=" + granularite +
                ", typeSol=" + typeSol +
                ", params=" + params +
                '}';
    }
}
