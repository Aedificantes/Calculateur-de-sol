package com.example.aedificantes_calculateur_se_sol.Calculator;

import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.HeadTab;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.TabBlockManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class AlphaCalculator {

    private TreeMap<Float, Float[]> alphaRef = new TreeMap<Float, Float[]>();

    public AlphaCalculator() {
        alphaRef.put(13f, new Float[]{7.8f, 2.8f});
        alphaRef.put(15f, new Float[]{8.4f, 3.3f});
        alphaRef.put(16f, new Float[]{9.4f, 3.8f});
        alphaRef.put(18f, new Float[]{10.1f, 4.5f});
        alphaRef.put(20f, new Float[]{12.1f, 5.5f});
        alphaRef.put(22f, new Float[]{15.0f, 7.0f});
        alphaRef.put(24f, new Float[]{18.0f, 9.2f});
        alphaRef.put(26f, new Float[]{23.1f, 12.3f});
        alphaRef.put(28f, new Float[]{29.5f, 16.5f});
        alphaRef.put(30f, new Float[]{38.0f, 22.5f});
        alphaRef.put(32f, new Float[]{48.4f, 31.0f});
        alphaRef.put(34f, new Float[]{64.9f, 44.4f});
    }

    public float getAlpha1(float index){
        Float[] local_index = placeOfIndex(index);
        if(local_index[0] == local_index[1]){ return alphaRef.get(local_index[0])[0]; }
        float k = (alphaRef.get(local_index[0])[0] - alphaRef.get(local_index[1])[0])/(local_index[0]-local_index[1]);
        float w = alphaRef.get(local_index[0])[0] - (alphaRef.get(local_index[0])[0] - alphaRef.get(local_index[1])[0])/(local_index[0]-local_index[1])*local_index[0];
        return k*index+w ;
    }

    public float getAlpha2(float index){
        Float[] local_index = placeOfIndex(index);
        if(local_index[0] == local_index[1]){ return alphaRef.get(local_index[0])[1]; }
        float k = (alphaRef.get(local_index[0])[1] - alphaRef.get(local_index[1])[1])/(local_index[0]-local_index[1]);
        float w = alphaRef.get(local_index[0])[1] - (alphaRef.get(local_index[0])[1] - alphaRef.get(local_index[1])[1])/(local_index[0]-local_index[1])*local_index[0];
        return k*index+w ;
    }

    private Float[] placeOfIndex(float index){
        ArrayList<Float> list = new ArrayList<Float>(alphaRef.keySet());
        Collections.sort(list);
        if(index <= list.get(0)){ return new Float[]{list.get(0), list.get(0)}; }
        if(index >= list.get(list.size()-1)){ return new Float[]{list.get(list.size()-1), list.get(list.size()-1)}; }
        for(int i =0; i< list.size(); i++){
            if(index == list.get(i)){ return new Float[]{list.get(i), list.get(i)}; }
            if(index < list.get(i)){
                return new Float[]{list.get(i-1), list.get(i)};
            }
        }
        return new Float[]{13f, 13f};
    }


    public TabBlockManager constructTab(Float index) {
        HeadTab headTab = new HeadTab(3,2);
        headTab.addBlock(0,0,1,2,"Valeur de frottement<br>interne dans la zone<br>de calcul φI, degrés");
        headTab.addBlock(1,1,1,1,"α1");
        headTab.addBlock(2,1,1,1,"α2");
        headTab.addBlock(1,0,2,1,"Coefficients");

        TreeMap<Float, Float[]> alphaRefWithIndex = (TreeMap<Float, Float[]>) alphaRef.clone();

        TabBlockManager<Float, Float> tabBlockManager = new TabBlockManager<>(headTab,alphaRefWithIndex);
        tabBlockManager.addRowData(index,new Float[alphaRefWithIndex.get(0).length],0f,getAlpha1(index),getAlpha2(index));

        return tabBlockManager;
    }

}
