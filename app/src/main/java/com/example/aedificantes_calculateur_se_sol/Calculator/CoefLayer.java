package com.example.aedificantes_calculateur_se_sol.Calculator;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.ParamLayerData;

import java.util.ArrayList;

/**
 * Calculator class based on ref table
 */
public class CoefLayer {

    private ArrayList<Float[]> REF = new ArrayList<>();

    public CoefLayer() {
        REF.add(new Float[]{0.8f,0.7f,0.7f});
        REF.add(new Float[]{0.8f,0.7f,0.6f});
        REF.add(new Float[]{0.7f,0.6f,0.4f});
        REF.add(new Float[]{0.8f,0.7f,0.5f});
        REF.add(new Float[]{0.7f,0.6f,0.4f});
        REF.add(new Float[]{0.6f,0.5f,0.3f});
    }

    public float getCoef_comp(ParamLayerData layerParam) throws RemblaiSupportLayerException {
            return rowOfCoef(layerParam)[0];
    }

    public float getCoef_trac(ParamLayerData layerParam) throws RemblaiSupportLayerException {
            return rowOfCoef(layerParam)[1];
    }

    public float getCoef_variableLoad(ParamLayerData layerParam) throws RemblaiSupportLayerException {
        return rowOfCoef(layerParam)[2];
    }


    private Float[] rowOfCoef(ParamLayerData layerParam) throws RemblaiSupportLayerException{
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
