package com.example.aedificantes_calculateur_se_sol.Calculator;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.Compacite;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.Granularite;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.TypeSol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ResistanceSolCalculator {
    private HashMap<Integer, int[]> Ref = new HashMap<Integer, int[]>();

    public ResistanceSolCalculator() {
        Ref.put(1, new int[]{35,23,15,12,8,4,4,3,2});
        Ref.put(2, new int[]{42,30,21,17,12,7,5,4,4});
        Ref.put(3, new int[]{48,35,25,20,14,8,7,6,5});
        Ref.put(4, new int[]{53,38,27,22,16,9,8,7,5});
        Ref.put(5, new int[]{56,40,29,24,17,10,8,7,6});
        Ref.put(6, new int[]{58,42,31,25,18,10,8,7,6});
        Ref.put(8, new int[]{62,44,33,26,19,10,8,7,6});
        Ref.put(10, new int[]{65,46,34,27,19,10,8,7,6});
        Ref.put(15, new int[]{72,51,38,28,20,11,8,7,6});
        Ref.put(20, new int[]{79,56,41,30,20,12,8,7,6});
        Ref.put(25, new int[]{86,61,44,32,20,12,9,8,7});
        Ref.put(30, new int[]{93,66,47,34,21,12,9,8,7});
        Ref.put(35, new int[]{100,70,50,36,22,12,9,8,7});
        Ref.put(40, new int[]{107,75,53,38,23,14,9,8,7});
    }

    public float resistanceSol_AVG(float index, ParamSol layerParam){
        if(index < 1) index = 1;
        int[] local_index = placeOfIndex(index);
        float k,w;
        if(layerParam.getTypeSol() == TypeSol.SABLEUX){
            int  sandType = sandTypeChoiser(layerParam.getGranularite());
            if(local_index[0] == local_index[1]){ return Ref.get(local_index[0])[sandType]; } // si les deux valeurx sont identiques c'est que la valeur est dans le tableau
            k = (Ref.get(local_index[0])[sandType] - Ref.get(local_index[1])[sandType])/(local_index[0]-local_index[1]);
            w = Ref.get(local_index[0])[sandType] - (Ref.get(local_index[0])[sandType] - Ref.get(local_index[1])[sandType])/(local_index[0]-local_index[1])*local_index[0];
            float result = (k*index+w)*factorSandCompacite(layerParam.getCompacite());
            System.out.println(" ResistanceSolCalculator -> resistanceSol_AVG("+index+","+layerParam+") detail: index:"+index+"sable type:"+sandType+" return: "+ result);
            return result ;
        }else{
            int[] index_column_argile = colonneSolArgile(layerParam.Il()); //index de la colonne
            float[] valueOfColumn_argile = new float[]{0.8f,0.9f};
            if(local_index[0] == local_index[1]){
                if(index_column_argile[0] == index_column_argile[1]) {
                    return Ref.get(local_index[0])[index_column_argile[0]];
                }else{
                    k = (Ref.get(local_index[0])[index_column_argile[0]] - Ref.get(local_index[0])[index_column_argile[1]])/(valueOfColumn_argile[0]-valueOfColumn_argile[1]);
                    w = Ref.get(local_index[0])[index_column_argile[0]] - (Ref.get(local_index[0])[index_column_argile[0]] - Ref.get(local_index[0])[index_column_argile[1]])/(valueOfColumn_argile[0]-valueOfColumn_argile[1])*valueOfColumn_argile[0];
                    System.out.println(" ResistanceSolCalculator -> resistanceSol_AVG("+index+","+layerParam+") detail: index:"+index+" argile: ["+index_column_argile[0]+","+layerParam.Il()+","+index_column_argile[1]+"] return: "+ (k*layerParam.Il()+w));
                    return (k*layerParam.Il()+w) ;
                }
            } // si les deux valeurs sont identiques c'est que la valeur est dans le tableau

            float alpha1,alpha2;
            k = (Ref.get(local_index[0])[index_column_argile[0]] - Ref.get(local_index[0])[index_column_argile[0]])/(index_column_argile[0]-index_column_argile[1]);
            w = Ref.get(local_index[0])[index_column_argile[0]] - (Ref.get(local_index[0])[index_column_argile[0]] - Ref.get(local_index[0])[index_column_argile[0]])/(index_column_argile[0]-index_column_argile[1])*index_column_argile[0];
            alpha1 =  k*layerParam.Il()+w ;

            k = (Ref.get(local_index[1])[index_column_argile[0]] - Ref.get(local_index[1])[index_column_argile[0]])/(index_column_argile[0]-index_column_argile[1]);
            w = Ref.get(local_index[1])[index_column_argile[0]] - (Ref.get(local_index[1])[index_column_argile[0]] - Ref.get(local_index[1])[index_column_argile[0]])/(index_column_argile[0]-index_column_argile[1])*index_column_argile[0];
            alpha2 =  k*layerParam.Il()+w ;

            k = (alpha1 - alpha2)/(local_index[0]-local_index[1]);
            w = alpha1 - (alpha1 - alpha2)/(local_index[0]-local_index[1])*local_index[0];
            System.out.println(" ResistanceSolCalculator -> resistanceSol_AVG("+index+","+layerParam+") detail: index: "+index+ " argile: ["+index_column_argile[0]+","+layerParam.Il()+","+index_column_argile[1]+"], hauteur:["+local_index[0]+","+index+","+local_index[1]+"]  alpha1: "+alpha1+" alpha2: "+alpha2+" return: "+ (k*layerParam.Il()+w));
            return (k*index+w) ;

        }
    }

    private int sandTypeChoiser(Granularite granularite) {
        if(granularite == Granularite.FIN) return 2;
        if(granularite == Granularite.PULVERULENT) return 3;
        return 0;
    }
    private float factorSandCompacite(Compacite compacite){
        if(compacite == Compacite.DENSE_SOND_ST || compacite == Compacite.DENSE_SANS_SOND_ST ) return 1.3f;
        return 1;
    }

    private int[] colonneSolArgile(float Il){
        if(Il <= 0.2) return new int[]{0,0};
        if(Il < 0.3) return new int[]{0,1};
        if(Il == 0.3) return new int[]{1,1};
        if(Il < 0.4) return new int[]{1,2};
        if(Il == 0.4) return new int[]{2,2};
        if(Il < 0.5) return new int[]{2,3};
        if(Il == 0.5) return new int[]{3,3};
        if(Il < 0.6) return new int[]{3,4};
        if(Il == 0.6) return new int[]{4,4};
        if(Il < 0.7) return new int[]{4,5};
        if(Il == 0.7) return new int[]{5,5};
        if(Il < 0.8) return new int[]{5,6};
        if(Il == 0.8) return new int[]{6,6};
        if(Il < 0.9) return new int[]{6,7};
        if(Il == 0.9) return new int[]{7,7};
        if(Il < 1) return new int[]{7,8};
        return new int[]{8,8};
    }

    /*
        public float getAlpha2(float index){
            int[] local_index = placeOfIndex(index);
            if(local_index[0] == local_index[1]){ return Ref.get(local_index[0])[1]; }
            float k = (Ref.get(local_index[0])[1] - Ref.get(local_index[1])[1])/(local_index[0]-local_index[1]);
            float w = Ref.get(local_index[0])[1] - (Ref.get(local_index[0])[1] - Ref.get(local_index[1])[1])/(local_index[0]-local_index[1])*local_index[0];
            return k*index+w ;
        }
    */
    private int[] placeOfIndex(float index){
        ArrayList<Integer> list = new ArrayList<Integer>(Ref.keySet());
        Collections.sort(list);
        if(index <= list.get(0)){ return new int[]{list.get(0), list.get(0)}; }
        if(index >= list.get(list.size()-1)){ return new int[]{list.get(list.size()-1), list.get(list.size()-1)}; }
        for(int i =0; i< list.size(); i++){
            if(index == list.get(i)){ return new int[]{list.get(i), list.get(i)}; }
            if(index < list.get(i)){
                return new int[]{list.get(i-1), list.get(i)};
            }
        }
        return new int[]{13, 13};
    }

}
