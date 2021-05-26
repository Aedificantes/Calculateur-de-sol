package com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.HeadTabBlock;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.TabBlock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

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
        boolean isColumnTitle = isTitleCell(startWidhtCell,startHeightCell,nbHeightCell);
        blockList.add(new HeadTabBlock(startWidhtCell,startHeightCell,nbWidhtCell,nbHeightCell,text,isColumnTitle));
    }

    private boolean isTitleCell(int startWidhtCell,int startHeightCell,int nbHeightCell){
        return  startHeightCell+nbHeightCell == this.nbHeightcells && startWidhtCell != 0;
    }

    public void addColumn(int indexNewColumn,String title) {
        this.nbWidhtcells++;
        for(TabBlock eachBlock : blockList){
            if(eachBlock.getStartWidhtCell() >= indexNewColumn){
                eachBlock.setStartWidhtCell(eachBlock.getStartWidhtCell()+1);
            }else if(eachBlock.getStartWidhtCell() <= indexNewColumn && eachBlock.getStartWidhtCell()+ eachBlock.getNbWidhtCell() > indexNewColumn){
                eachBlock.setNbWidhtCell(eachBlock.getNbWidhtCell()+1);
            }
        }
        findEmptyCell(title);
    }

    private void findEmptyCell(String title){
        int tampTab[][] = new int[nbHeightcells][nbWidhtcells];

        System.out.println("findEmptyCells: " );

        for(TabBlock eachBlock : blockList){
            for(int height=0; height < eachBlock.getNbHeightCell(); height++ ){
                for(int width=0; width < eachBlock.getNbWidhtCell(); width++){
                    tampTab[height+eachBlock.getStartHeightCell()][width+eachBlock.getStartWidhtCell()] = 1;
                }
            }
        }

        for(int height = 0; height < nbHeightcells; height++){
            for(int width = 0; width < nbWidhtcells; width++){
                if(tampTab[height][width] == 0){
                    if(isTitleCell(width,height,1)){
                        addBlock(width,height,1,1,title);
                    }else{
                        addBlock(width,height,1,1,"");
                    }
                }
            }
        }
        System.out.println();
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
