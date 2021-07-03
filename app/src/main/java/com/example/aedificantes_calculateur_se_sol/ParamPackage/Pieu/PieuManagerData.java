package com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu;

import com.example.aedificantes_calculateur_se_sol.Error.VerificateObservable;
import com.example.aedificantes_calculateur_se_sol.Error.VerificateObserver;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class PieuManagerData implements Serializable, VerificateObserver {
    private ArrayList<Float> listData = new ArrayList<>();

    public PieuManagerData(float... data) {
        for(float each :  data){
            listData.add(each);
        }
    }

    public float Dfut_val(){
        return  this.listData.get(0);
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


    public void set_Dfut_val(float val){
         this.listData.set(0,val);
    }
    public void set_Dhel_val(float val){
         this.listData.set(1,val);
    }
    public void set_Hk_val(float val){
         this.listData.set(3,val);
    }
    public void set_Ip_val(float val){
         this.listData.set(2,val);
    }
    public void set_H_val(float val){
         this.listData.set(4,val);
    }




    @Override
    public String toString() {
        return "PieuManagerData{" +
                "listData=" + listData +
                '}';
    }

    public JSONObject convert_to_JSON() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("DHEL",Dhel_val());
        obj.put("DFUT",Dfut_val());
        obj.put("HK",Hk_val());
        obj.put("IP",Ip_val());
        obj.put("H",H_val());
        return obj;
    }

    @Override
    public boolean isFill() {
        return
                Dhel_val() != 0f &&
                Dfut_val() != 0f &&
                Ip_val() != 0f &&
                H_val() != 0f &&
                Hk_val() != 0f;
    }
}
