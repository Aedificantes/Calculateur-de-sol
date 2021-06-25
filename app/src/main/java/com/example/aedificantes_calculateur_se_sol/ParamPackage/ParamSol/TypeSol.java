package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol;

import android.graphics.Color;

import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.HatchTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.HorizontalLineTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.LineTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.PointTexture;
import com.example.aedificantes_calculateur_se_sol.Schema.texturePackage.Texture;

import java.io.Serializable;

public enum TypeSol implements Serializable {

    REMBLAI(0,"remblai", new HatchTexture(5, Color.BLUE)),
    SABLEUX(1,"sableux", new PointTexture(0,Color.parseColor("#3EAB99"))),
    ARGILEUX(2,"argileux", new HorizontalLineTexture(5,Color.parseColor("#DF9C35"))),
    LIMONEUX(3,"limoneux", new LineTexture(20,Color.RED)),
    LOAM_SABLEUX(4,"loam sableux", new LineTexture(0,Color.RED));

    private int indice ;
    private String name ;
    private Texture texture;

    private TypeSol(int indice, String name, Texture texture) {
        this.indice = indice ;
        this.name = name;
        this.texture = texture;
    }


    public int getIndice() {
        return  this.indice ;
    }

    public Texture getTexture(){ return this.texture;}

    public String getNameLowerCase() {
        return  this.name;
    }

    public static TypeSol getTypeSol_byId(int id){
        for(TypeSol each: values()){
            if(id == each.getIndice()){
                return each;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return  this.name;
    }
}
