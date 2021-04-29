package com.example.aedificantes_calculateur_se_sol.Calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AlphaCalculator {

    private HashMap<Integer, float[]> alphaRef = new HashMap<Integer, float[]>();

    public AlphaCalculator() {
        alphaRef.put(13, new float[]{7.8f, 2.8f});
        alphaRef.put(15, new float[]{8.4f, 3.3f});
        alphaRef.put(16, new float[]{9.4f, 3.8f});
        alphaRef.put(18, new float[]{10.1f, 4.5f});
        alphaRef.put(20, new float[]{12.1f, 5.5f});
        alphaRef.put(22, new float[]{15.0f, 7.0f});
        alphaRef.put(24, new float[]{18.0f, 9.2f});
        alphaRef.put(26, new float[]{23.1f, 12.3f});
        alphaRef.put(28, new float[]{29.5f, 16.5f});
        alphaRef.put(30, new float[]{38.0f, 22.5f});
        alphaRef.put(32, new float[]{48.4f, 31.0f});
        alphaRef.put(34, new float[]{64.9f, 44.4f});
    }

    public float getAlpha1(float index){
        int[] local_index = placeOfIndex(index);
        if(local_index[0] == local_index[1]){ return alphaRef.get(local_index[0])[0]; }
        float k = (alphaRef.get(local_index[0])[0] - alphaRef.get(local_index[1])[0])/(local_index[0]-local_index[1]);
        float w = alphaRef.get(local_index[0])[0] - (alphaRef.get(local_index[0])[0] - alphaRef.get(local_index[1])[0])/(local_index[0]-local_index[1])*local_index[0];
        return k*index+w ;
    }

    public float getAlpha2(float index){
        int[] local_index = placeOfIndex(index);
        if(local_index[0] == local_index[1]){ return alphaRef.get(local_index[0])[1]; }
        float k = (alphaRef.get(local_index[0])[1] - alphaRef.get(local_index[1])[1])/(local_index[0]-local_index[1]);
        float w = alphaRef.get(local_index[0])[1] - (alphaRef.get(local_index[0])[1] - alphaRef.get(local_index[1])[1])/(local_index[0]-local_index[1])*local_index[0];
        return k*index+w ;
    }

    private int[] placeOfIndex(float index){
        ArrayList<Integer> list = new ArrayList<Integer>(alphaRef.keySet());
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
