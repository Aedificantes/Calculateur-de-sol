package com.example.aedificantes_calculateur_se_sol.Calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ResistanceSolCalculator {
    private HashMap<Integer, int[]> Ref = new HashMap<Integer, int[]>();

    public ResistanceSolCalculator() {
        Ref.put(1, new int[]{35, 23});
        Ref.put(2, new int[]{42, 30});
        Ref.put(3, new int[]{48, 35});
        Ref.put(4, new int[]{53, 38});
        Ref.put(5, new int[]{56, 40});
        Ref.put(6, new int[]{58, 42});
        Ref.put(8, new int[]{62, 44});
        Ref.put(10, new int[]{65, 46});
        Ref.put(15, new int[]{72, 51});
        Ref.put(20, new int[]{79, 56});
        Ref.put(25, new int[]{86, 61});
        Ref.put(30, new int[]{93, 66});
        Ref.put(35, new int[]{100, 70});
        Ref.put(40, new int[]{107, 75});
    }

    public float resistanceSol_AVG(float index){
        int[] local_index = placeOfIndex(index);
        if(local_index[0] == local_index[1]){ return Ref.get(local_index[0])[0]; }
        float k = (Ref.get(local_index[0])[0] - Ref.get(local_index[1])[0])/(local_index[0]-local_index[1]);
        float w = Ref.get(local_index[0])[0] - (Ref.get(local_index[0])[0] - Ref.get(local_index[1])[0])/(local_index[0]-local_index[1])*local_index[0];
        System.out.println(" ResistanceSolCalculator -> resistanceSol_AVG("+index+") return: "+ k*index+w);
        return k*index+w ;
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
