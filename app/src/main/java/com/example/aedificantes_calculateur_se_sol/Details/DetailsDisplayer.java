package com.example.aedificantes_calculateur_se_sol.Details;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.aedificantes_calculateur_se_sol.Calculator.DetailResultManager;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.DetailTabLauncher;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainerData;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class DetailsDisplayer {

    private final String LOG_TAG= "CLA_67418";

    private ExpandableListView elvInvestments;
    private DetailsMenuAdaptator detailsMenuAdaptator;
    private Context context;
    private DetailTabLauncher launcher;
    private ViewGroup toAddListDetails;

    private DetailResultManager detailResultManager;
    private boolean isShow = false;

    public DetailsDisplayer(Context context, ViewGroup layout_Result, DetailTabLauncher launcher) {
        this.context = context;
        this.launcher = launcher;
        elvInvestments = new ExpandableListView(context);
        detailsMenuAdaptator = new DetailsMenuAdaptator(context, launcher);
        elvInvestments.setAdapter(detailsMenuAdaptator);
        toAddListDetails = layout_Result;
    }

    private void display(TreeMap<DetailTitle, List<Detail>> dataList){
        detailsMenuAdaptator.display(dataList);
    }

    public void generateAndDisplay(ParamContainerData data){
        TreeMap<DetailTitle,List<Detail>> tampHash = new TreeMap<DetailTitle,List<Detail>>();
        this.detailResultManager = new DetailResultManager(data);
        tampHash = detailResultManager.generateDetails();

        display(tampHash);
        show();
        detailsMenuAdaptator.notifyDataSetChanged();
        Log.d(LOG_TAG,"Size of list error " +tampHash.keySet().size());
    }

    public void generateAndDisplay_toPDF(ParamContainerData data, int width, int height){
        TreeMap<DetailTitle,List<Detail>> tampHash = new TreeMap<DetailTitle,List<Detail>>();
        this.detailResultManager = new DetailResultManager(data);
        tampHash = detailResultManager.generateDetails();

        show(width, height);
        display(tampHash);
        for(int i=0; i < tampHash.keySet().size(); i++){ // to expand all group and si child inside
            elvInvestments.expandGroup(i);
        }
        detailsMenuAdaptator.notifyDataSetChanged();
        Log.d(LOG_TAG,"Size of list error " +tampHash.keySet().size());
    }

    public void display_elements(int... elements){
        for(int i =0; i < elvInvestments.getCount(); i++){
            elvInvestments.collapseGroup(i);
        }
        for(int elem : elements){
            elvInvestments.expandGroup(elem);
        }
        Arrays.sort(elements);
        elvInvestments.setSelection(elements[elements.length -1]);
        detailsMenuAdaptator.notifyDataSetChanged();
    }


    public void hide(){
        if(isShow) {
            toAddListDetails.removeView(elvInvestments);
            isShow = !isShow;
        }
    }

    public void show(){
        if(!isShow){
            toAddListDetails.addView(elvInvestments);
            isShow = !isShow;
        }
    }


    public void show(int widht, int height){
        if(!isShow){
            toAddListDetails.addView(elvInvestments);
            elvInvestments.getLayoutParams().height = height;
            elvInvestments.getLayoutParams().width = widht;
            isShow = !isShow;
            Log.d(LOG_TAG,"Le DetailsDisplayer ajoute sa view : w:"+widht+" h:"+height);
        }else{
            Log.d(LOG_TAG,"Le DetailsDisplayer ne peut pas ajouter sa vue, elle est déjà affiché .");
        }
    }


}
