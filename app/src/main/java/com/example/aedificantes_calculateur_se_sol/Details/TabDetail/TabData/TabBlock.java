package com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData;

import android.content.Context;
import android.view.View;

import java.io.Serializable;

public abstract class TabBlock implements Serializable {

    private int startWidhtCell,startHeightCell,nbWidhtCell,nbHeightCell;
    private boolean isMarqued;

    public TabBlock(int startWidhtCell, int startHeightCell, int nbWidhtCell, int nbHeightCell) {
        this.startWidhtCell = startWidhtCell;
        this.startHeightCell = startHeightCell;
        this.nbWidhtCell = nbWidhtCell;
        this.nbHeightCell = nbHeightCell;
    }

    public TabBlock(int startWidhtCell, int startHeightCell, int nbWidhtCell, int nbHeightCell, boolean isMarqued) {
        this.startWidhtCell = startWidhtCell;
        this.startHeightCell = startHeightCell;
        this.nbWidhtCell = nbWidhtCell;
        this.nbHeightCell = nbHeightCell;
        this.isMarqued = isMarqued;
    }

    public int getStartWidhtCell() {
        return startWidhtCell;
    }

    public void setStartWidhtCell(int startWidhtCell) {
        this.startWidhtCell = startWidhtCell;
    }

    public int getStartHeightCell() {
        return startHeightCell;
    }

    public void setStartHeightCell(int startHeightCell) {
        this.startHeightCell = startHeightCell;
    }

    public int getNbWidhtCell() {
        return nbWidhtCell;
    }

    public void setNbWidhtCell(int nbWidhtCell) {
        this.nbWidhtCell = nbWidhtCell;
    }

    public int getNbHeightCell() {
        return nbHeightCell;
    }

    public void setNbHeightCell(int nbHeightCell) {
        this.nbHeightCell = nbHeightCell;
    }

    public boolean isMarqued() {
        return isMarqued;
    }

    public void setMarqued(boolean marqued) {
        isMarqued = marqued;
    }

    public abstract View getElementView(Context context);
}
