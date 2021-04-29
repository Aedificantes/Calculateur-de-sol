package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol;

import com.example.aedificantes_calculateur_se_sol.Error.Error;

import java.util.ArrayList;
import java.util.List;

public class ParamSol {
    private Compacite compacite = Compacite.FRIABLE;
    private Granularite granularite = Granularite.GRAVELEUX;
    private TypeSol typeSol = TypeSol.REMBLAI;
    //public int p1=0,p2=0,p3=0,p4=0,p5=0,p6=0,p7=0;
    public ArrayList<Float> params = new ArrayList<>();
    public ArrayList<Integer> paramToSet = new ArrayList<>();
    public ArrayList<String> logNameParam = new ArrayList<>();
    private ParamEnabler paramEnabler;


    public ParamSol() {
        paramEnabler = new ParamEnabler(this);
        for(int i=0; i<7; i++) {
            params.add(0f);
        }
        logNameParam.add("Facteur de plasticité des sols argileux");
        logNameParam.add("Indices des vides");
        logNameParam.add("Cohésion du sol");
        logNameParam.add("Angle de frottement interne des sols");
        logNameParam.add("Masse volumique de l'horizon");
        logNameParam.add("Indice d'humidite");
        logNameParam.add("Epaisseur de l'horizon");

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
    public void setParamToSet(int... index){
        paramToSet.clear();
        for(int each: index){
            paramToSet.add(each);
        }
    }
    public void addParamToSet(int index){
        paramToSet.add(index);
    }

    public void setValueByIndex(int index, float value){
        params.set(index,value);
    }

    public ParamEnabler getParamEnabler() {
        return paramEnabler;
    }

    public ArrayList<Float> getParams() {
        return params;
    }

    public void setParamEnabler(ParamEnabler paramEnabler) {
        this.paramEnabler = paramEnabler;
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

    public List<Error> generateError() {
        ArrayList<Error> list =  new ArrayList<>();
        for(int each : paramToSet){
            if(params.get(each) == 0){
                list.add(new Error(" - "+logNameParam.get(each)+" n'a pas de valeur"));
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return "ParamSol{" +
                "compacite=" + compacite +
                ", granularite=" + granularite +
                ", typeSol=" + typeSol +
                ", params=" + params +
                ", paramToSet=" + paramToSet +
                ", paramEnabler=" + paramEnabler +
                '}';
    }

}
