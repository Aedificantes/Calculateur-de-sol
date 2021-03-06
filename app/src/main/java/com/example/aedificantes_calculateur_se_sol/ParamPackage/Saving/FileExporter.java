package com.example.aedificantes_calculateur_se_sol.ParamPackage.Saving;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainerData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer.ParamLayerData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Parse un ParamContainer en  json
 */
public class FileExporter {

    private static final String LOG_TAG = "CLA_02";

    private ParamContainerData paramContainerData;

    public FileExporter(ParamContainerData containerParam) {
        paramContainerData = containerParam;
    }

    public JSONObject generate(){
        JSONObject main = new JSONObject();
        JSONArray sol = new JSONArray();
        try {
            for (ParamLayerData solData : paramContainerData.getSol_data_list()) {
                sol.put(solData.convert_to_json());
            }
            main.put("PIEU", paramContainerData.getScrewPileManagerData().convert_to_JSON());
            main.put("SOL", sol);
            main.put("EAUX_SOUTERRAINES", paramContainerData.getGroundWater_data().convert_to_JSON());
        }catch (Exception e){
            Log.d(LOG_TAG, e.getMessage());
        }

        return main;
    }

    public void save(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
        Date date = new Date();
        String fileName = "AE_sauvegarde_"+formatter.format(date)+".json";
        writeFileOnInternalStorage(fileName, prettyPrint_JSON(generate()));
        Log.i(LOG_TAG,"le fichier à bien été téléchargé sous le  nom: "+fileName);
    }

    private void writeFileOnInternalStorage(String sFileName, String sBody){
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        try {
            File gpxfile = new File(dir, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
        } catch (Exception e){
            Log.e(LOG_TAG,e.getMessage());
            e.printStackTrace();
        }
    }



    /*
    add following line to graddle
    - implementation 'com.google.code.gson:gson:2.8.6'
     */
    public static String prettyPrint_JSON(JSONObject obj){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(obj.toString());
        return gson.toJson(je);
    }

}
