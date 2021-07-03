package com.example.aedificantes_calculateur_se_sol.Calculator;

import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.HeadTab;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.TabBlockManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Calculator class based on ref table
 */
public class AlphaCalculator {

    private TreeMap<Float, ArrayList<Float>> alphaRef = new TreeMap<Float, ArrayList<Float>>();

    public AlphaCalculator() {
        alphaRef.put(13f,  new ArrayList<Float>(Arrays.asList(7.8f, 2.8f)));
        alphaRef.put(15f,  new ArrayList<Float>(Arrays.asList(8.4f, 3.3f)));
        alphaRef.put(16f,  new ArrayList<Float>(Arrays.asList(9.4f, 3.8f)));
        alphaRef.put(18f,  new ArrayList<Float>(Arrays.asList(10.1f, 4.5f)));
        alphaRef.put(20f,  new ArrayList<Float>(Arrays.asList(12.1f, 5.5f)));
        alphaRef.put(22f,  new ArrayList<Float>(Arrays.asList(15.0f, 7.0f)));
        alphaRef.put(24f,  new ArrayList<Float>(Arrays.asList(18.0f, 9.2f)));
        alphaRef.put(26f,  new ArrayList<Float>(Arrays.asList(23.1f, 12.3f)));
        alphaRef.put(28f,  new ArrayList<Float>(Arrays.asList(29.5f, 16.5f)));
        alphaRef.put(30f,  new ArrayList<Float>(Arrays.asList(38.0f, 22.5f)));
        alphaRef.put(32f,  new ArrayList<Float>(Arrays.asList(48.4f, 31.0f)));
        alphaRef.put(34f,  new ArrayList<Float>(Arrays.asList(64.9f, 44.4f)));
    }

    public float getAlpha1(float index){
        Float[] local_index = placeOfIndex(index);
        if(local_index[0] == local_index[1]){ return alphaRef.get(local_index[0]).get(0); }
        float k = (alphaRef.get(local_index[0]).get(0) - alphaRef.get(local_index[1]).get(0))/(local_index[0]-local_index[1]);
        float w = alphaRef.get(local_index[0]).get(0) - (alphaRef.get(local_index[0]).get(0) - alphaRef.get(local_index[1]).get(0))/(local_index[0]-local_index[1])*local_index[0];
        return k*index+w ;
    }

    public float getAlpha2(float index){
        Float[] local_index = placeOfIndex(index);
        if(local_index[0] == local_index[1]){ return alphaRef.get(local_index[0]).get(1); }
        float k = (alphaRef.get(local_index[0]).get(1) - alphaRef.get(local_index[1]).get(1))/(local_index[0]-local_index[1]);
        float w = alphaRef.get(local_index[0]).get(1) - (alphaRef.get(local_index[0]).get(1) - alphaRef.get(local_index[1]).get(1))/(local_index[0]-local_index[1])*local_index[0];
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

    /**
     * Generate a table to display on line detail clicking in DetailFagment
     * @param index value to estimate alpha values
     * @return table builder to give to DetailtabDrawer
     */
    public TabBlockManager constructTab(Float index) {
        HeadTab headTab = new HeadTab(3,2);
        headTab.addBlock(0,0,1,2,"Valeur de frottement<br>interne dans la zone<br>de calcul φI, degrés");
        headTab.addBlock(1,1,1,1,"α1");
        headTab.addBlock(2,1,1,1,"α2");
        headTab.addBlock(1,0,2,1,"Coefficients");

        TreeMap<Float, ArrayList<Float>> alphaRefWithIndex = (TreeMap<Float, ArrayList<Float>>) alphaRef.clone();

        TabBlockManager<Float, Float> tabBlockManager = new TabBlockManager<>(headTab,alphaRefWithIndex);
        tabBlockManager.addRowData(index,0f,getAlpha1(index),getAlpha2(index));

        return tabBlockManager;
    }

}
