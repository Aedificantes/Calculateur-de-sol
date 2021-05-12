package com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PieuManagerData implements Serializable {
    private ArrayList<Float> listData = new ArrayList<>();

    public PieuManagerData(float... data) {
        for(float each :  data){
            listData.add(each);
        }
    }

    public float Dfut_val(){
        return this.listData.get(0);
    }
    public float Dhel_val(){
        return this.listData.get(1);
    }
    public float Hk_val(){
        return this.listData.get(3);
    }
    public float Ip_val(){
        return this.listData.get(2);
    }
    public float H_val(){
        return this.listData.get(4);
    }



}
