package com.example.aedificantes_calculateur_se_sol.Details.TabDetail;

import androidx.gridlayout.widget.GridLayout;

import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.TabBlock;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.TabBlockManager;

import java.util.ArrayList;

public class DetailTabDrawer{
    //private HeadTab headTab;
    //private TreeMap<T, V[]> contentData = new TreeMap<T, V[]>();
    //private int indexData;
    //private boolean isRow;

    private TabBlockManager tabManager;

    /*
    public DetailTabDrawer(HeadTab headTab, TreeMap<T, V[]> contentData,int indexData, boolean isRow) {
        this.headTab = headTab;
        this.contentData = contentData;
        this.indexData = indexData;
        this.isRow = isRow;
    }
    */
    public DetailTabDrawer(TabBlockManager manager) {
        this.tabManager = manager;
    }

    public void toDrawGridLayout(GridLayout gridLayout){
        gridLayout.setColumnCount(tabManager.getHeadTab().getNbWidhtcells());
        gridLayout.setRowCount(tabManager.getHeadTab().getNbHeightcells() + tabManager.getContentData().keySet().size());
        System.out.println("Row Count : "+gridLayout.getRowCount());
        //drawHead(gridLayout);
        //drawContent(gridLayout);
        for(TabBlock eachTabBlock : (ArrayList<TabBlock>)tabManager.getAllTabBlocks()){
            drawBlockGridLayout(gridLayout,eachTabBlock);
        }
    }
/*
    private void drawHead(GridLayout gridLayout){
        for(TabBlock eachBlock : tabManager.getHeadTab().getBlockList()){
            androidx.gridlayout.widget.GridLayout.LayoutParams params = new androidx.gridlayout.widget.GridLayout.LayoutParams();
            params.width = androidx.gridlayout.widget.GridLayout.LayoutParams.WRAP_CONTENT;
            params.height = androidx.gridlayout.widget.GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = androidx.gridlayout.widget.GridLayout.spec(eachBlock.getStartWidhtCell(), eachBlock.getNbWidhtCell(), androidx.gridlayout.widget.GridLayout.FILL,1f);
            params.rowSpec = androidx.gridlayout.widget.GridLayout.spec(eachBlock.getStartHeightCell(), eachBlock.getNbHeightCell(), androidx.gridlayout.widget.GridLayout.FILL,1f);


            TextView view = new TextView(gridLayout.getContext());
            view.setText(eachBlock.getText());
            view.setTextSize(20);
            view.setTypeface(null, Typeface.BOLD);
            view.setGravity(Gravity.CENTER);

            if(isMarked(eachBlock.getStartWidhtCell(), eachBlock.getStartHeightCell())){
                view.setBackgroundColor(Color.GREEN);
            }

            view.setBackgroundColor(Color.parseColor("#978BF2"));

            gridLayout.addView(eachBlock.getElementView(gridLayout.getContext()), params);
        }
    }
    */
/*
    private void drawContent(GridLayout gridLayout){
        int indexRow = headTab.getNbHeightcells();
        for(T eachKey : contentData.keySet()){
            V[] line = contentData.get((T)eachKey);
            drawElementOfContent(gridLayout,0,indexRow,1, 1,eachKey.toString(), isMarked(indexRow, 0));
            int indexColumn = 1;
            for(V eachElem : line){
                drawElementOfContent(gridLayout,indexColumn,indexRow,1, 1,eachElem.toString(), isMarked(indexRow, indexColumn));
                indexColumn++;
            }
            indexRow++;
        }
        System.out.println("Out of drawContent");
    }
    */
    private void drawBlockGridLayout(GridLayout gridLayout, TabBlock block){
        androidx.gridlayout.widget.GridLayout.LayoutParams params = new androidx.gridlayout.widget.GridLayout.LayoutParams();
        params.width = androidx.gridlayout.widget.GridLayout.LayoutParams.WRAP_CONTENT;
        params.height = androidx.gridlayout.widget.GridLayout.LayoutParams.WRAP_CONTENT;
        params.columnSpec = androidx.gridlayout.widget.GridLayout.spec(block.getStartWidhtCell(), block.getNbWidhtCell(), androidx.gridlayout.widget.GridLayout.FILL,1f);
        params.rowSpec = androidx.gridlayout.widget.GridLayout.spec(block.getStartHeightCell(), block.getNbHeightCell(), androidx.gridlayout.widget.GridLayout.FILL,1f);

        /*
        TextView view = new TextView(gridLayout.getContext());

        view.setText(text);
        view.setTextSize(18);
        view.setGravity(Gravity.CENTER);
        view.setTypeface(null, Typeface.BOLD);
        if(highlight){
            view.setBackgroundColor(Color.GREEN);
        }

         */

        gridLayout.addView(block.getElementView(gridLayout.getContext()), params);
    }
    /*
    private boolean isMarked(int indexRow, int indexColumn){
        if(isRow){
            return (indexRow == headTab.getNbHeightcells()+indexData);
        }else{
            return (indexColumn == indexData);
        }
    }

     */

    /*
    private void drawElementOfContent(GridLayout gridLayout, int startWidhtCell, int startHeightCell, int nbWidhtCell, int nbHeightCell, String text, boolean highlight){
        androidx.gridlayout.widget.GridLayout.LayoutParams params = new androidx.gridlayout.widget.GridLayout.LayoutParams();
        params.width = androidx.gridlayout.widget.GridLayout.LayoutParams.WRAP_CONTENT;
        params.height = androidx.gridlayout.widget.GridLayout.LayoutParams.WRAP_CONTENT;
        params.columnSpec = androidx.gridlayout.widget.GridLayout.spec(startWidhtCell, nbWidhtCell, androidx.gridlayout.widget.GridLayout.FILL,1f);
        params.rowSpec = androidx.gridlayout.widget.GridLayout.spec(startHeightCell, nbHeightCell, androidx.gridlayout.widget.GridLayout.FILL,1f);

        TextView view = new TextView(gridLayout.getContext());
        view.setText(text);
        view.setTextSize(18);
        view.setGravity(Gravity.CENTER);
        view.setTypeface(null, Typeface.BOLD);
        if(highlight){
            view.setBackgroundColor(Color.GREEN);
        }

        gridLayout.addView(view, params);
    }

     */
}
