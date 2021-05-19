package com.example.aedificantes_calculateur_se_sol.Error;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ExpandableListView;


import com.example.aedificantes_calculateur_se_sol.Details.Detail;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamSol.ParamSol;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.Pieu.PieuParamManager;

import java.util.HashMap;
import java.util.List;

public class ErrorDisplayer {

    private ExpandableListView elvInvestments;
    private ErrorListAdpatator errorListAdpatator;
    private Context context;
    private ViewGroup toAddListError;

    private ErrorCreater errorCreater = new ErrorCreater();
    private boolean isShow = true;

    public ErrorDisplayer(Context context, ViewGroup layout_Result) {
        this.context = context;
        elvInvestments = new ExpandableListView(context);
        errorListAdpatator = new ErrorListAdpatator(context);
        elvInvestments.setAdapter(errorListAdpatator);
        toAddListError = layout_Result;
        toAddListError.addView(elvInvestments);
    }

    public void display(HashMap<String, List<Error>> dataList){
        errorListAdpatator.display(dataList);
        show();
    }

    public void generateAndDisplay(List<ParamSol> listParam, PieuParamManager pieuManager){
        HashMap<String,List<Error>> tampHash = new HashMap<String,List<Error>>();
        tampHash.putAll(errorCreater.generate_LineEmptyError(listParam) );
        tampHash.putAll(errorCreater.generate_PieuEmptyError(pieuManager) );

        display(tampHash);
        errorListAdpatator.notifyDataSetChanged();
        System.out.println(tampHash.keySet().size());
    }

    public void hide(){
        if(isShow) {
            toAddListError.removeView(elvInvestments);
            isShow = !isShow;
        }
    }

    public void show(){
        if(!isShow){
            toAddListError.addView(elvInvestments);
            isShow = !isShow;
        }
    }


}
