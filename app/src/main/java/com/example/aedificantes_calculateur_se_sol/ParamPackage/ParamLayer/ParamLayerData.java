package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Like every other param classes ParamLayerData has his own data classes that can be serializable to be place in ParamContainerData and transfert to other Activity
 */
public class ParamLayerData implements Serializable {
    private Compacite compacite = Compacite.FRIABLE;
    private Granularite granularite = Granularite.GRAVELEUX;
    private TypeSol typeSol = TypeSol.REMBLAI;
    protected Map<IndexColumnName,Float> params= new HashMap<>();
    protected ArrayList<String> logNameParam = new ArrayList<>();
    protected ArrayList<IndexColumnName> paramToSet = new ArrayList<>();
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

    public Map<IndexColumnName, Float> getParams() {
        return params;
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
        return this.params.get(IndexColumnName.IL);
    }
    public float e(){
        return this.params.get(IndexColumnName.E);
    }
    public float cT(){
        return this.params.get(IndexColumnName.CT);
    }
    public float fi(){
        return this.params.get(IndexColumnName.FI);
    }
    public float yT(){
        return this.params.get(IndexColumnName.YT);
    }
    public float Sr(){
        return this.params.get(IndexColumnName.SR);
    }
    public float h(){ // en metre
        return this.params.get(IndexColumnName.H);
    }

    public void set_Il(float val){
        this.params.put(IndexColumnName.IL,val);

    }
    public void set_e(float val){
        this.params.put(IndexColumnName.E,val);
    }
    public void set_cT(float val){
        this.params.put(IndexColumnName.CT,val);
    }
    public void set_fi(float val){
        this.params.put(IndexColumnName.FI,val);
    }
    public void set_yT(float val){
        this.params.put(IndexColumnName.YT,val);
    }
    public void set_Sr(float val){
        this.params.put(IndexColumnName.SR,val);
    }
    public void set_h(float val){ // en metre
        /*
        if(params.size() <= 6){
            fillToIndex(6);
        }
         this.params.set(6,val);*/
        this.params.put(IndexColumnName.H,val);

    }

    private void fillToIndex(int index){
        /*
        for(int i=params.size()-1; i < index; i++ ){
            //params.add(0f);
        }
         */
        for(IndexColumnName each : IndexColumnName.values()){
            this.params.put(each,0f);
        }

    }

    public boolean isAllFill(){
        boolean returned = true;
        for(IndexColumnName each : paramToSet){
            if(params.get(each) == 0){
                return false;
            }
        }
        return returned;
    }

    public boolean is_Il_paramToSet(){
        return this.paramToSet.contains(IndexColumnName.IL);
    }

    public boolean is_e_paramToSet(){
        return this.paramToSet.contains(IndexColumnName.E);
    }

    public boolean is_CT_paramToSet(){
        return this.paramToSet.contains(IndexColumnName.CT);
    }

    public boolean is_FI_paramToSet(){
        return this.paramToSet.contains(IndexColumnName.FI);
    }

    public boolean is_YT_paramToSet(){
        return this.paramToSet.contains(IndexColumnName.YT);
    }

    public boolean is_SR_paramToSet(){
        return this.paramToSet.contains(IndexColumnName.SR);
    }

    public boolean is_h_paramToSet(){
       return this.paramToSet.contains(IndexColumnName.H);
    }
/*
    public boolean find_is_paramToSet(int indexParam){
        for(int each : paramToSet){
            if(each == indexParam){
                return true;
            }
        }
        return false;
    }

 */

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
