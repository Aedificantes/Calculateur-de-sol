package com.example.aedificantes_calculateur_se_sol.ParamPackage.Saving;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainer;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.Compacite;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.Granularite;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.TypeSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuManagerData;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class FileLoader {

    private static final String LOG_TAG = "CLA_03";

    private String url;

    public FileLoader(String url) {
        this.url = url;
    }

    public ParamContainer loadAndParse(){
        String content ="";
        ArrayList<ParamSolData> list_sol_data = new ArrayList<>();
        PieuManagerData pieuManagerData;
        ParamContainer paramContainer;
        try {
            content = readFile(url);
        }catch (Exception e){
            Log.e(LOG_TAG, "error loadAndParse->FILEREADING \n"+e.getMessage()+"\n"+e.getCause());
        }
        Log.d(LOG_TAG,"file IMPORT : \n"+content);

        try {
            JSONObject main = new JSONObject(content);
            JSONArray solArray = main.getJSONArray("SOL");
            for(int i=0; i<solArray.length(); i++){
                JSONObject rowSol = (JSONObject) solArray.get(i);
                ParamSolData tampSol = new ParamSolData();
                tampSol.setTypeSol(TypeSol.getTypeSol_byId(rowSol.getInt("TYPE_SOL")));
                tampSol.setGranularite(Granularite.getGranularite_byId(rowSol.getInt("GRANULARITE")));
                tampSol.setCompacite(Compacite.getCompacite_byId(rowSol.getInt("COMPACITE")));
                tampSol.set_Il((float) rowSol.getDouble("IL"));
                tampSol.set_e((float) rowSol.getDouble("E"));
                tampSol.set_cT((float) rowSol.getDouble("CT"));
                tampSol.set_fi((float) rowSol.getDouble("FI"));
                tampSol.set_yT((float) rowSol.getDouble("YT"));
                tampSol.set_Sr((float) rowSol.getDouble("SR"));
                tampSol.set_h((float) rowSol.getDouble("H"));
                list_sol_data.add(tampSol);
            }
            JSONObject pieu = main.getJSONObject("PIEU");
            pieuManagerData = new PieuManagerData(
                    (float) pieu.getDouble("DFUT"),
                    (float) pieu.getDouble("DHEL"),
                    (float) pieu.getDouble("HK"),
                    (float) pieu.getDouble("H"),
                    (float) pieu.getDouble("IP"));

            paramContainer = new ParamContainer(list_sol_data, pieuManagerData);
            return paramContainer;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "error loadAndParse->JSONPARSING \n"+e.getMessage()+"\n"+e.getCause());

            e.printStackTrace();
        }


        return null;
    }

    private String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            reader.close();
        }catch(Exception e){
            Log.e(LOG_TAG, "readFile error \n "+e.getMessage()+"\n"+e.getCause());
        }
        return stringBuilder.toString();

    }





    public static String getPath(Context context, Uri uri) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // DocumentProvider
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                    // TODO handle non-primary volumes
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


}
