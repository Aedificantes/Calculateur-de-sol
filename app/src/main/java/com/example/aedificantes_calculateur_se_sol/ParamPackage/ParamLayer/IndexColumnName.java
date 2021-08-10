package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamLayer;

import android.graphics.Color;

import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.HatchTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.HorizontalLineTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.LineTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.PointTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.Texture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum IndexColumnName implements Serializable {

    IL(0,"Il","I<sub>l</sub>","Facteur de plasticité des sols argileux"),
    E(1,"e","e","Indices des vides"),
    CT(2,"cT","c,T/m<sup>2</sup>","Cohésion du sol"),
    FI(3,"φ","φ","Angle de frottement interne des sols"),
    YT(4,"yT","y,T/m<sup>3</sup>","Masse volumique de l'horizon"),
    SR(5,"Sr","S<sub>r</sub>","Indice d'humidite"),
    H(6,"h","h,m","Epaisseur de l'horizon");

    private final int indice ;
    private final String name ;
    private final String HTMLName;
    private final String extendName;

    IndexColumnName(int indice, String name, String HTMLName, String extendName) {
        this.indice = indice ;
        this.name = name;
        this.HTMLName = HTMLName;
        this.extendName = extendName;
    }


    public int getIndice() {
        return  this.indice ;
    }

    public String getName() {
        return  this.name;
    }

    public String getHTMLName() {
        return HTMLName;
    }

    public String getExtendName() {
        return extendName;
    }

    public static IndexColumnName getIndexColumnName_byId(int id){
        for(IndexColumnName each: values()){
            if(id == each.getIndice()){
                return each;
            }
        }
        return null;
    }

    public static List<IndexColumnName> valuesToIndex(int index){
        int local_index = 0;
       List<IndexColumnName> indexColumnName_return = new ArrayList<>();
        for(IndexColumnName each : IndexColumnName.values()){
            if(local_index == index){break;}
            indexColumnName_return.add(each);
            local_index++;
        }
        return indexColumnName_return;
    }

    @Override
    public String toString() {
        return  this.name;
    }
}
