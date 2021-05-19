package com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData;

import java.io.Serializable;

public class MarkedElement implements Serializable {

    public static int TOTAL = -1 ;
    private int indexRow, indexColumn;

    public MarkedElement(int indexRow, int indexColumn) {
        this.indexRow = indexRow;
        this.indexColumn = indexColumn;
    }

    public boolean cellIsMarked(int indexCellRow, int indexCellColumn){
        if(indexRow == MarkedElement.TOTAL){
            return this.indexColumn == indexCellColumn;
        }else if(indexColumn == MarkedElement.TOTAL){
            return this.indexRow == indexCellRow;
        }
        return this.indexRow == indexCellRow && this.indexColumn == indexCellColumn;
    }
}


