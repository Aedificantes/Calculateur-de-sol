package com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol;

public enum TypeSol {

    REMBLAI(0,"remblai"),
    SABLEUX(1,"sableux"),
    ARGILEUX(2,"argileux"),
    LIMONEUX(3,"limoneux"),
    LOAM_SABLEUX(4,"loam sableux");

    private int indice ;
    private String name ;

    private TypeSol(int indice,String name) {
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
