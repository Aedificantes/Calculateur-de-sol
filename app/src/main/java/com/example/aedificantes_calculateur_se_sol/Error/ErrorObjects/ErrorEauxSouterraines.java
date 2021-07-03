package com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects;

import java.util.List;

public class ErrorEauxSouterraines extends ErrorManager{

    public ErrorEauxSouterraines(List<Error> listError, ErrorOrder order) {
        super(listError, order);
    }


    @Override
    public String title() {
        return this.getOrder().getName();
    }

    @Override
    public int compareTo(ErrorManager o) {
        return this.getOrder().compareTo(o.getOrder());
    }
}
