package com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

public class TabBlockManager<T extends Serializable & Comparable,V extends Serializable>  implements Serializable {
    private HeadTab headTab;
    private ArrayList<TabBlock> blockList = new ArrayList<>();
    private TreeMap<T,V[]> contentData;
    private MarkedElementsManager markedElementsManager;


    public TabBlockManager(HeadTab headTab, TreeMap<T,V[]> data, MarkedElementsManager markedElementsManager) {
        this.headTab = headTab;
        this.contentData = data;
        this.markedElementsManager = markedElementsManager;
        updateContentTabBlocks();
    }

    public TabBlockManager(HeadTab headTab, TreeMap<T,V[]> data) {
        this.headTab = headTab;
        this.contentData = data;
        this.markedElementsManager = new MarkedElementsManager();
        updateContentTabBlocks();
    }

    public void updateContentTabBlocks(){
        this.blockList.clear();
        int indexRow = headTab.getNbHeightcells();
        for(T eachKey : contentData.keySet()){
            V[] line = contentData.get((T)eachKey);
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

    public void addRowData(T title, V[] bufferSized, V defaultVal, V... values){
        //contentData.put(title, new V[]{defaultVal, defaultVal});
        for(int i=0; i < values.length; i++){
            if(i < bufferSized.length){
                bufferSized[i] = values[i];
            }
        }
        if(bufferSized.length < values.length){
            for(int i = values.length-1; i < bufferSized.length; i++){
                bufferSized[i] = defaultVal;
            }
        }
        contentData.put(title,bufferSized);
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

    public void addColumn(String title, V[] bufferSized, V defaultVal, V... values){

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

    public TreeMap<T,V[]> getContentData() {
        return contentData;
    }

    public void setContentData(TreeMap<T,V[]> contentData) {
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
