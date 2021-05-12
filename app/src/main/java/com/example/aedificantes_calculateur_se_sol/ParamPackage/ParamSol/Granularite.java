package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol;

import java.io.Serializable;

public enum Granularite implements Serializable {

    GRAVELEUX(0,"graveleux"),
    GROSSIER(1,"grossier"),
    MOYEN(2,"moyen"),
    FIN(3,"fin"),
    PULVERULENT(4,"pulverulent");

    private int indice ;
    private String name ;

    private Granularite(int indice,String name) {
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

