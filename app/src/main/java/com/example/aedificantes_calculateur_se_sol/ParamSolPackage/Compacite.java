package com.example.aedificantes_calculateur_se_sol.ParamSolPackage;

public enum Compacite {

    FRIABLE(1,"fiable"),
    MOYEN_DENSE(2,"moyennement dense"),
    DENSE_SOND_ST(3,"dense sondate stat"),
    DENSE_SANS_SOND_ST(4,"dense SANS sondage stat");

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
