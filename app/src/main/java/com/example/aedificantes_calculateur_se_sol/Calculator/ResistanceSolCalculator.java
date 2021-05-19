package com.example.aedificantes_calculateur_se_sol.Calculator;

import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.HeadTab;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.TabBlockManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.Compacite;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.Granularite;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.TypeSol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class ResistanceSolCalculator {
    private TreeMap<Float, Integer[]> Ref = new TreeMap<Float, Integer[]>();

    public ResistanceSolCalculator() {
        Ref.put(1f, new Integer[]{35,23,15,12,8,4,4,3,2});
        Ref.put(2f, new Integer[]{42,30,21,17,12,7,5,4,4});
        Ref.put(3f, new Integer[]{48,35,25,20,14,8,7,6,5});
        Ref.put(4f, new Integer[]{53,38,27,22,16,9,8,7,5});
        Ref.put(5f, new Integer[]{56,40,29,24,17,10,8,7,6});
        Ref.put(6f, new Integer[]{58,42,31,25,18,10,8,7,6});
        Ref.put(8f, new Integer[]{62,44,33,26,19,10,8,7,6});
        Ref.put(10f, new Integer[]{65,46,34,27,19,10,8,7,6});
        Ref.put(15f, new Integer[]{72,51,38,28,20,11,8,7,6});
        Ref.put(20f, new Integer[]{79,56,41,30,20,12,8,7,6});
        Ref.put(25f, new Integer[]{86,61,44,32,20,12,9,8,7});
        Ref.put(30f, new Integer[]{93,66,47,34,21,12,9,8,7});
        Ref.put(35f, new Integer[]{100,70,50,36,22,12,9,8,7});
        Ref.put(40f, new Integer[]{107,75,53,38,23,14,9,8,7});
    }

    public float resistanceSol_AVG(float index, ParamSolData layerParam){
        if(index < 1) index = 1;
        Float[] local_index = placeOfIndex(index);
        float k,w;
        if(layerParam.getTypeSol() == TypeSol.SABLEUX){
            int  sandType = sandTypeChooser(layerParam.getGranularite());
            if(local_index[0] == local_index[1]){ return Ref.get(local_index[0])[sandType]; } // si les deux valeurs sont identiques c'est que la valeur est dans le tableau
            k = (Ref.get(local_index[0])[sandType] - Ref.get(local_index[1])[sandType])/(local_index[0]-local_index[1]);
            w = Ref.get(local_index[0])[sandType] - (Ref.get(local_index[0])[sandType] - Ref.get(local_index[1])[sandType])/(local_index[0]-local_index[1])*local_index[0];
            float result = (k*index+w)*factorSandCompacite(layerParam.getCompacite());
            System.out.println(" ResistanceSolCalculator -> resistanceSol_AVG("+index+","+layerParam+") detail: index:"+index+"sable type:"+sandType+" return: "+ result);
            return result ;
        }else{
            int[] index_column_argile = columnSolArgile(layerParam.Il()); //index de la colonne
            float[] valueOfColumn_argile = new float[]{0.8f,0.9f}; // TODO VERIFY UTILITY AND HARDCODED VALUES !!!
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

    private int sandTypeChooser(Granularite granularite) {
        if(granularite == Granularite.FIN) return 2;
        if(granularite == Granularite.PULVERULENT) return 3;
        return 0;
    }
    private float factorSandCompacite(Compacite compacite){
        if(compacite == Compacite.DENSE_SOND_ST || compacite == Compacite.DENSE_SANS_SOND_ST ) return 1.3f;
        return 1;
    }

    private int[] columnSolArgile(float Il){
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

    private Float[] placeOfIndex(float index){
        ArrayList<Float> list = new ArrayList<Float>(Ref.keySet());
        Collections.sort(list);
        if(index <= list.get(0)){ return new Float[]{list.get(0), list.get(0)}; }
        if(index >= list.get(list.size()-1)){ return new Float[]{list.get(list.size()-1), list.get(list.size()-1)}; }
        for(int i =0; i< list.size(); i++){
            if(index == list.get(i)){ return new Float[]{list.get(i), list.get(i)}; }
            if(index < list.get(i)){
                return new Float[]{list.get(i-1), list.get(i)};
            }
        }
        return new Float[]{40f, 40f};
    }

    public TabBlockManager constructTab(Float index, ParamSolData data) {
        HeadTab headTab = new HeadTab(10,5);

        headTab.addBlock(0,0,1,5,"partie gauche");
        headTab.addBlock(1,0,9,1,"droite haut");
        headTab.addBlock(1,1,9,1,"doite mid haut");

        headTab.addBlock(1,2,1,1,"mid");
        headTab.addBlock(2,2,1,1,"mid");
        headTab.addBlock(3,2,1,1,"mid");
        headTab.addBlock(4,2,1,1,"");
        headTab.addBlock(5,2,1,1,"");
        headTab.addBlock(6,2,1,1,"");
        headTab.addBlock(7,2,1,1,"");
        headTab.addBlock(8,2,1,1,"");
        headTab.addBlock(9,2,1,1,"");

        headTab.addBlock(1,3,9,1,"droit mid bas");

        headTab.addBlock(1,4,1,1,"≤0.2");
        headTab.addBlock(2,4,1,1,"0.3");
        headTab.addBlock(3,4,1,1,"0.4");
        headTab.addBlock(4,4,1,1,"0.5");
        headTab.addBlock(5,4,1,1,"0.6");
        headTab.addBlock(6,4,1,1,"0.7");
        headTab.addBlock(7,4,1,1,"0.8");
        headTab.addBlock(8,4,1,1,"0.9");
        headTab.addBlock(9,4,1,1,"1");

        TreeMap<Float, Integer[]>  tabRefWithIndex = (TreeMap<Float, Integer[]>) Ref.clone();

        TabBlockManager<Float, Integer> tabManager = new TabBlockManager<>(headTab,tabRefWithIndex);

        return tabManager;
    }

    private int indexOfGivenKey(Float key, TreeMap<Float, Integer[]> tabRefWithIndex){
        int index =-1;
        for(Float each : tabRefWithIndex.keySet()){
            index ++;
            if(key <= each){return index;}
        }
        return tabRefWithIndex.keySet().size()-1;
    }

    private void treeChooser_generateTab(float index, ParamSolData layerParam) {
        if (index < 1) index = 1;
        Float[] local_index = placeOfIndex(index);
        float k, w;
        if (layerParam.getTypeSol() == TypeSol.SABLEUX) {
            int sandType = sandTypeChooser(layerParam.getGranularite());
            if (local_index[0] == local_index[1]) {  // si les deux valeurs sont identiques c'est que la valeur est dans le tableau
                /*TODO
                    -highlight cell at line: local_index[0] & column: sandType
                 */
                return;
            }
                /*TODO
                    -add line between local_index[0] and local_index[1]
                    -add line name index
                    -add value with 0 or nothing and just new calculate value at line: indexOfGivenKey(Float key,...) & column : sandType
                    -highlight line local_index[0]+1;
                 */
            return;
        } else {
            int[] index_column_argile = columnSolArgile(layerParam.Il()); //column index
            float[] valueOfColumn_argile = new float[]{0.8f, 0.9f};
            if (local_index[0] == local_index[1]) {
                if (index_column_argile[0] == index_column_argile[1]) {
                    //return Ref.get(local_index[0])[index_column_argile[0]];
                    //TODO Highlight cell line:local_index[0] & column: index_column_argile[0]
                    return;
                } else {
                    /*TODO
                        -add column between index_column_argile[0] and index_column_argile[1]
                        -add column name layerParam.Il()
                        -add values with 0 or nothing and just new calculate value in cell at line: local_index[0] & column : index_column_argile[0]+1
                        -highlight column index_column_argile[0]+1;
                    */
                    return;
                }
            } // si les deux valeurs sont identiques c'est que la valeur est dans le tableau

             /*TODO
                -add line between local_index[0] and local_index[1]
                -add line name index
                -add column between index_column_argile[0] and index_column_argile[1]
                -add column name layerParam.Il()
                -add values with 0 or nothing and just new calculate value in cell at line: local_index[0]+1 & column : index_column_argile[0]+1
                -highlight column index_column_argile[0]+1 and line local_index[0]+1 ;
             */

            return;

        }
    }

}
