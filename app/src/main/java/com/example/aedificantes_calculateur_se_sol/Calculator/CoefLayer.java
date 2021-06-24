package com.example.aedificantes_calculateur_se_sol.Calculator;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.TypeSol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class CoefLayer {

    //private TreeMap<String, ArrayList<Float>> Ref = new TreeMap<>();
    private ArrayList<Float[]> REF = new ArrayList<>();

    public CoefLayer() {
        /*
        Ref.put("argile et limon solide, semi-solide et plastique rigide", new ArrayList<Float>(Arrays.asList(0.8f,0.7f,0.7f)));
        Ref.put("argile et limon plastique souple", new ArrayList<Float>(Arrays.asList(0.8f,0.7f,0.6f)));
        Ref.put("argile et limon fluide-plastique", new ArrayList<Float>(Arrays.asList(0.7f,0.6f,0.4f)));
        Ref.put("sables à faible humidité et loam sableux solide", new ArrayList<Float>(Arrays.asList(0.8f,0.7f,0.5f)));
        Ref.put("sables humides et loam sableux plastique", new ArrayList<Float>(Arrays.asList(0.7f,0.6f,0.4f)));
        Ref.put("sables à forte teneur en eau et loams sableux fluides", new ArrayList<Float>(Arrays.asList(0.6f,0.5f,0.3f)));
         */
        REF.add(new Float[]{0.8f,0.7f,0.7f});
        REF.add(new Float[]{0.8f,0.7f,0.6f});
        REF.add(new Float[]{0.7f,0.6f,0.4f});
        REF.add(new Float[]{0.8f,0.7f,0.5f});
        REF.add(new Float[]{0.7f,0.6f,0.4f});
        REF.add(new Float[]{0.6f,0.5f,0.3f});
    }

    public float getCoef_comp(ParamSolData layerParam) throws RemblaiSupportLayerException {
            return rowOfCoef(layerParam)[0];
    }

    public float getCoef_trac(ParamSolData layerParam) throws RemblaiSupportLayerException {
            return rowOfCoef(layerParam)[1];
    }

    public float getCoef_variableLoad(ParamSolData layerParam) throws RemblaiSupportLayerException {
        return rowOfCoef(layerParam)[2];
    }


    private Float[] rowOfCoef(ParamSolData layerParam) throws RemblaiSupportLayerException{
        switch (layerParam.getTypeSol()){
            case ARGILEUX:
            case LIMONEUX:
                if(layerParam.Sr() > 0.8){
                  return REF.get(2);
                }else if(layerParam.Sr() > 0.5){
                  return REF.get(1);
                }else{
                  return REF.get(0);
                }
            case SABLEUX:
            case LOAM_SABLEUX:
                if(layerParam.Sr() > 0.8){
                    return REF.get(5);
                }else if(layerParam.Sr() > 0.5){
                    return REF.get(4);
                }else{
                    return REF.get(3);
                }
            case REMBLAI:
                throw new RemblaiSupportLayerException("Il n'est possible de determiner le coefficient de l'horizon si celui-ci est un Remblai, d'ailleurs le Rembali ne peut-être une couche portante !");
        }
        throw new RemblaiSupportLayerException("Valeur hors champs de donnée TypeSol");

    }


    /* if convert to table is needed
    Convert table as treemap and put this as keys:
    argile et limon solide, semi-solide et plastique rigide
    argile et limon plastique souple
    argile et limon fluide-plastique
    sables à faible humidité et loam sableux solide
    sables humides et loam sableux plastique
    sables à forte teneur en eau et loams sableux fluides
     */
}