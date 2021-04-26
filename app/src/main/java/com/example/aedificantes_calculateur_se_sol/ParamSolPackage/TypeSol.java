package com.example.aedificantes_calculateur_se_sol.ParamSolPackage;

public enum TypeSol {

    REMBLAI(1,"remblai"),
    SABLEAUX(2,"sableux"),
    ARGILEUX(3,"argileux"),
    LIMONEUX(4,"limoneux"),
    LOAM_SABLEUX(5,"loam sableux");

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
