package com.example.aedificantes_calculateur_se_sol.ParamPackage.Saving;

import android.util.Log;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainer;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;

import org.json.JSONArray;
import org.json.JSONObject;

public class FileExporter {

    private static final String LOG_TAG = "CLA_02";

    ParamContainer paramContainer;

    public FileExporter(ParamContainer paramContainer) {
        this.paramContainer = paramContainer;
    }

    public JSONObject generate(){
        JSONObject main =new JSONObject();
        JSONObject pieuParam =new JSONObject();
        JSONArray solList = new JSONArray();

        try {
            for (ParamSolData eachSol : paramContainer.getSol_data_list()){
                JSONObject eachJSonSol = new JSONObject();
                eachJSonSol.put("E", eachSol.e());
                eachJSonSol.put("CT", eachSol.cT());
                eachJSonSol.put("FI", eachSol.fi());
                solList.put(eachJSonSol);
            }
            main.put("SOLPARAM", solList);
        }catch (Exception e){
            Log.e(LOG_TAG, e.getMessage());
        }

        return main;
    }

    public static String convert_JSON_to_String(JSONObject jsonObject){
        return new String("test");

    }
}
