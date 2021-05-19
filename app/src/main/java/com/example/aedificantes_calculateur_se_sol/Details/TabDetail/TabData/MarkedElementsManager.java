package com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData;

import android.graphics.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MarkedElementsManager implements Serializable {

    private List<MarkedElement> marquedElement_list;
    private int nbWidthCells, nbHeightCells;

    public MarkedElementsManager() {
        marquedElement_list = new ArrayList<>();
    }

    public void add_marked_row(int row){
        marquedElement_list.add(new MarkedElement(row,MarkedElement.TOTAL));
    }

    public void add_marked_column(int column){
        marquedElement_list.add(new MarkedElement(MarkedElement.TOTAL,column));
    }

    public void add_marked_cell(int row, int column){
        marquedElement_list.add(new MarkedElement(row,column));
    }

    public void add_marked_cells(Point... cells_coord){
        for(Point each : cells_coord){
            marquedElement_list.add(new MarkedElement(each.x,each.y));
        }
    }

    public void add_marked_cellsGroup(int row, int column, int nbRowCells, int nbColumnCells){

    }

    public boolean isCellMarqued(int row, int column){
        for(MarkedElement each : marquedElement_list){
            if(each.cellIsMarked(row,column)){ return  true; }
        }
        return false;
    }

}




