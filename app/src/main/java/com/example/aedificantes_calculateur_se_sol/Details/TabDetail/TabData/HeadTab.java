package com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData;

import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.HeadTabBlock;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.TabBlock;

import java.io.Serializable;
import java.util.ArrayList;

public class HeadTab implements Serializable {
    private ArrayList<TabBlock> blockList = new ArrayList<>();
    private int nbWidhtcells,nbHeightcells;

    public HeadTab(int nbWidhtcells, int nbHeightcells) {
        this.nbWidhtcells = nbWidhtcells;
        this.nbHeightcells = nbHeightcells;
    }

    public void addBlock(TabBlock block){
        blockList.add(block);
    }

    public void addBlock(int startWidhtCell,int startHeightCell,int nbWidhtCell,int nbHeightCell, String text){
        blockList.add(new HeadTabBlock(startWidhtCell,startHeightCell,nbWidhtCell,nbHeightCell,text));
    }

    public ArrayList<TabBlock> getBlockList() {
        return blockList;
    }

    public int getNbWidhtcells() {
        return nbWidhtcells;
    }

    public int getNbHeightcells() {
        return nbHeightcells;
    }

    public int nbBlock(){
        return blockList.size();
    }
}
