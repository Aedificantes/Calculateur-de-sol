package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol;

public enum Granularite {

    GRAVELEUX(1,"graveleux"),
    GROSSIER(2,"grossier"),
    MOYEN(3,"moyen"),
    FIN(4,"fin"),
    PULVERULENT(5,"pulverulent");

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

