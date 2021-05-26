package com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class TabBlockManager<T extends Serializable & Comparable,V extends Serializable>  implements Serializable {
    private HeadTab headTab;
    private ArrayList<TabBlock> blockList = new ArrayList<>();
    private TreeMap<T,ArrayList<V>> contentData;
    private MarkedElementsManager markedElementsManager;


    public TabBlockManager(HeadTab headTab, TreeMap<T,ArrayList<V>> data, MarkedElementsManager markedElementsManager) {
        this.headTab = headTab;
        this.contentData = data;
        this.markedElementsManager = markedElementsManager;
        updateContentTabBlocks();
    }

    public TabBlockManager(HeadTab headTab, TreeMap<T,ArrayList<V>> data) {
        this.headTab = headTab;
        this.contentData = data;
        this.markedElementsManager = new MarkedElementsManager();
        updateContentTabBlocks();
    }

    public void updateContentTabBlocks(){
        this.blockList.clear();
        int indexRow = headTab.getNbHeightcells();
        for(T eachKey : contentData.keySet()){
            ArrayList<V> line = contentData.get((T)eachKey);
            blockList.add(new ContentTabBlock(0,indexRow,1, 1,eachKey.toString(), markedElementsManager.isCellMarqued(indexRow-headTab.getNbHeightcells(),0)));
            int indexColumn = 1;
            for(V eachElem : line){
                blockList.add(new ContentTabBlock(indexColumn,indexRow,1, 1,eachElem.toString(),markedElementsManager.isCellMarqued(indexRow-headTab.getNbHeightcells(),indexColumn)));
                indexColumn++;
            }
            indexRow++;
        }
        System.out.println("Out of drawContent");
    }

    public void addTabBlock(TabBlock block){
        this.blockList.add(block);
    }

    public void addRowData(T title,V defaultVal, V... values){
        ArrayList<V> list = new ArrayList<>();
        T oneKey =  contentData.firstKey();
        for(int i=0; i < values.length; i++){
            if(i < contentData.get(oneKey).size()){
                list.add(values[i]);
            }
        }
        if(list.size() < contentData.get(oneKey).size()){
            for(int i = list.size()-1; i < contentData.get(oneKey).size()-1; i++){
                list.add(defaultVal);
            }
        }
        contentData.put(title,list);
        markedElementsManager.add_marked_row(indexOfGivenKey(title));
        updateContentTabBlocks();
    }

    public void addRowData_givenColumn(T title,V defaultVal, V value, int indexColumn){
        ArrayList<V> list = new ArrayList<>();
        T oneKey =  contentData.firstKey();

        list.addAll(Collections.nCopies((contentData.get(oneKey).size()), defaultVal));
        list.set(indexColumn, value);

        contentData.put(title,list);
        markedElementsManager.add_marked_row(indexOfGivenKey(title));
        updateContentTabBlocks();
    }

    private int indexOfGivenKey(T key){
        int index =-1;
        for(T each : contentData.keySet()){
            index ++;
            if(key.compareTo(each) <= 0){return index;}
        }
        return contentData.keySet().size()-1;
    }

    public void addColumn(String title,V defaultVal,int indexNewColumn, V... values){
        //T oneKey =  contentData.firstKey();
        headTab.addColumn(indexNewColumn,title);

        int index = 0;
        for(T eachKey : contentData.keySet()){
            if(index < values.length){
                contentData.get(eachKey).add(indexNewColumn,values[index]);
            }else{
                contentData.get(eachKey).add(indexNewColumn,defaultVal);
            }
            index++;
        }
        markedElementsManager.add_marked_column(indexNewColumn);
        updateContentTabBlocks();
    }

    public void addColumn_dataGivenRow(String title, V defaultVal, int indexNewColumn, int indexRow, V value) {
        int index = 0;

        for (T eachKey : contentData.keySet()) {
            if (index == indexRow) {
                  contentData.get(eachKey).add(indexNewColumn, value);
            } else {
                contentData.get(eachKey).add(indexNewColumn, defaultVal);
            }
            index++;
        }

        headTab.addColumn(indexNewColumn+1, title);
        markedElementsManager.add_marked_column(indexNewColumn+1);
        updateContentTabBlocks();
    }



//    public void addContentData(TreeMap<T, V[]> data){
      //TODO add or replace block with values after HeadTab

  //  }

    public ArrayList<TabBlock> getAllTabBlocks(){
        ArrayList<TabBlock> tampblockList = (ArrayList<TabBlock>) blockList.clone();
        tampblockList.addAll(headTab.getBlockList());
        return tampblockList;
    }





    public HeadTab getHeadTab() {
        return headTab;
    }

    public void setHeadTab(HeadTab headTab) {
        this.headTab = headTab;
    }

    public ArrayList<TabBlock> getBlockList() {
        return blockList;
    }

    public void setBlockList(ArrayList<TabBlock> blockList) {
        this.blockList = blockList;
    }

    public TreeMap<T,ArrayList<V>> getContentData() {
        return contentData;
    }

    public void setContentData(TreeMap<T,ArrayList<V>> contentData) {
        this.contentData = contentData;
        updateContentTabBlocks();
    }

    public MarkedElementsManager getMarquedElements() {
        return markedElementsManager;
    }

    public void setMarkedElementsManager(MarkedElementsManager markedElementsManager) {
        this.markedElementsManager = markedElementsManager;
        updateContentTabBlocks();
    }


}
