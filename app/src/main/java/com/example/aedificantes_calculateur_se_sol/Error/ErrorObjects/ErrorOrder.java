package com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects;

public enum ErrorOrder implements Comparable<ErrorOrder>{
    PIEUX(0,"Pieux"),
    PARAMSOL(1,"Ligne n°"),
    EAUX_SOUTERRAINES(2,"Eaux souterraines");


    private int indice ;
    private String name ;

    private ErrorOrder(int indice,String name) {
        this.indice = indice ;
        this.name = name;
    }


    public int getIndice() {
        return  this.indice ;
    }

    public String getName() {
        return  this.name;
    }

    @Override
    public String toString() {
        return  this.name;
    }


}
