package com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects;

import java.util.List;

public class ErrorParamSolManager extends ErrorManager implements Comparable<ErrorManager>{

    private Integer rowIndexInList;

    public ErrorParamSolManager(List<Error> listError, ErrorOrder order, Integer index) {
        super(listError, order);
        rowIndexInList = index;
    }

    public Integer getRowIndexInList() {
        return rowIndexInList;
    }

    @Override
    public int compareTo(ErrorManager o) {
        if(o instanceof ErrorParamSolManager){
            return this.getRowIndexInList().compareTo(((ErrorParamSolManager) o).getRowIndexInList());
        }else{
            return this.getOrder().compareTo(o.getOrder());
        }
    }

    @Override
    public String title() {
        return this.getOrder().getName()+" "+rowIndexInList;
    }
}
