package com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects;

import java.util.List;

public abstract class ErrorManager implements Comparable<ErrorManager>{
    private List<Error> listError;
    private ErrorOrder order;

    public ErrorManager(List<Error> listError, ErrorOrder order) {
        this.listError = listError;
        this.order = order;
    }

    public List<Error> getListError() {
        return listError;
    }

    public void setListError(List<Error> listError) {
        this.listError = listError;
    }

    public ErrorOrder getOrder() {
        return order;
    }

    public void setOrder(ErrorOrder order) {
        this.order = order;
    }

    abstract public String title();
}
