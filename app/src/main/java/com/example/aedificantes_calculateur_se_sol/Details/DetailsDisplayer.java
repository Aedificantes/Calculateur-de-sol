package com.example.aedificantes_calculateur_se_sol.Details;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.aedificantes_calculateur_se_sol.Calculator.DetailResultManager;
import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.DetailTabLauncher;
import com.example.aedificantes_calculateur_se_sol.Error.Error;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorCreater;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorListAdpatator;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSolData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuManagerData;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuParamManager;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class DetailsDisplayer {
    private ExpandableListView elvInvestments;
    private DetailsMenuAdaptator detailsMenuAdaptator;
    private Context context;
    private DetailTabLauncher launcher;
    private ViewGroup toAddListDetails;

    private DetailResultManager detailResultManager;
    private boolean isShow = true;

    public DetailsDisplayer(Context context, ViewGroup layout_Result, DetailTabLauncher launcher) {
        this.context = context;
        this.launcher = launcher;
        elvInvestments = new ExpandableListView(context);
        detailsMenuAdaptator = new DetailsMenuAdaptator(context, launcher);
        elvInvestments.setAdapter(detailsMenuAdaptator);
        toAddListDetails = layout_Result;
        toAddListDetails.addView(elvInvestments);
    }

    public void display(TreeMap<DetailTitle, List<Detail>> dataList){
        detailsMenuAdaptator.display(dataList);
        show();
    }

    public void generateAndDisplay(List<ParamSolData> listParam, PieuManagerData pieuManager){
        TreeMap<DetailTitle,List<Detail>> tampHash = new TreeMap<DetailTitle,List<Detail>>();
        this.detailResultManager = new DetailResultManager(listParam,pieuManager);
        tampHash = detailResultManager.generateDetails();

        display(tampHash);
        detailsMenuAdaptator.notifyDataSetChanged();
        System.out.println(tampHash.keySet().size());
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


}
