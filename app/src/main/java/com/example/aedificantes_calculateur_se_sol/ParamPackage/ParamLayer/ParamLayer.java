package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer;

import android.util.Log;

import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.Error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        for(IndexColumnName each: IndexColumnName.values()) {
            data.params.put(each,0f);
        }
        //addLogName();

    }
    public ParamLayer(TypeSol sol, Granularite granularite, Compacite comp, float... value) {
        data = new ParamLayerData();
        this.data.setTypeSol(sol);
        this.data.setGranularite(granularite);
        this.setCompacite(comp);
        int local_index = 0;
        for(float each: value){
            data.params.put(IndexColumnName.getIndexColumnName_byId(local_index), each);
            local_index++;
        }
        for(int i=value.length; i<7; i++) {
            data.params.put(IndexColumnName.getIndexColumnName_byId(local_index), 0f);
            local_index++;
        }
        Log.d(LOG_TAG,"Create ParamSol with :"+data.params.size() + " values : details: "+data.params.toString());
        //addLogName();

        paramEnabler = new ParamEnabler(this);
    }

    public ParamLayer(ParamLayerData data) {
        this.data = data;
        this.paramEnabler = new ParamEnabler(this);
        //addLogName();
    }

    public boolean isAllFill(){
        boolean returned = true;
        for(IndexColumnName each : data.paramToSet){
            if(data.params.get(each) == 0){
                return false;
            }
        }
        return returned;
    }
    public void setParamToSet(IndexColumnName... columnNames){
        data.paramToSet.clear();
        data.paramToSet.addAll(Arrays.asList(columnNames));
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

    public void addParamToSet(IndexColumnName index){
        data.paramToSet.add(index);
    }

    public void setValueByColumnName(IndexColumnName index, float value){
        data.params.put(index,value);
    }

    public ParamEnabler getParamEnabler() {
        return paramEnabler;
    }

    public Map<IndexColumnName,Float> getParams() {
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

    public List<Error> generateEmptyError() {
        ArrayList<Error> list =  new ArrayList<>();
        Log.e(LOG_TAG, "generateError nb value params: "+data.params.size()+"\n Param to enable: "+data.paramToSet.toString());
        for(IndexColumnName each : data.paramToSet){
            if(data.params.get(each) == 0){
                list.add(new Error(" - "+each.getHTMLName()+" n'a pas de valeur"));
            }
        }
        return list;
    }

    public List<Error> generateMathError() {
        ArrayList<Error> list =  new ArrayList<>();
        Log.d(LOG_TAG, "generateMathError nb value params: "+data.params.size()+"\n Param to enable: "+data.paramToSet.toString());
        ParamLayerData data = getData();
        if(data.Il() < -0.2 || data.Il() > 1.2 && data.Il() != 0 && data.is_Il_paramToSet()){
            list.add(new Error(" - Il n'est pas compris entre -0.2 et 1.2"));
        }else{
            if(!data.is_Il_paramToSet()) {
                Log.d(LOG_TAG, "generateMathError IL n'est pas à remplir");
            }
                Log.d(LOG_TAG, "generateMathError: Il :"+data.Il());
        }
        if(data.e() < 0 || data.e() > 1.1 && data.e() != 0 && data.is_e_paramToSet()){
            list.add(new Error(" - e n'est pas compris entre 0 et 1.1"));
        }
        if(data.fi() < 10 || data.fi() > 37 && data.fi() != 0 && data.is_FI_paramToSet()){
            list.add(new Error(" - φ n'est pas compris entre 10 et 37"));
        }
        if(data.yT() < 1.5 || data.yT() > 2.6 && data.yT() != 0 && data.is_YT_paramToSet()){
            list.add(new Error(" - γ,T/m<sup>3</sup> n'est pas compris entre 1.5 et 2.6"));
        }
        if(data.Sr() < 0 || data.Sr() > 1 && data.Sr() != 0 && data.is_SR_paramToSet()){
            list.add(new Error(" - S<sub>r</sub> n'est pas compris entre 0 et 1"));
        }
        if(data.h() < 0 || data.h() > 40 && data.h() != 0 && data.is_h_paramToSet()){
            list.add(new Error(" - h n'est pas compris entre 0 et 40"));
        }
        Log.d(LOG_TAG, "generateMatchError : "+ list.toString());
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
