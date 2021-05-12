package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol;

import java.io.Serializable;

public enum Compacite implements Serializable {

    FRIABLE(0,"fiable"),
    MOYEN_DENSE(1,"moyennement dense"),
    DENSE_SOND_ST(2,"dense sondate stat"),
    DENSE_SANS_SOND_ST(3,"dense SANS sondage stat");

    private int indice ;
    private String name ;

    private Compacite(int indice,String name) {
        this.indice = indice ;
        this.name = name;
    }


    public int getIndice() {
        return  this.indice ;
    }

    public String getNameLowerCase() {
        return  this.name;
    }

    @Override
    public String toString() {
        return  this.name;
    }
}
