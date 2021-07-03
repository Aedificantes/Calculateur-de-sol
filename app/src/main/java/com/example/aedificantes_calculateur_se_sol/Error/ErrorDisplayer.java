package com.example.aedificantes_calculateur_se_sol.Error;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;


import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.ErrorManager;
import com.example.aedificantes_calculateur_se_sol.ParamPackage.ParamContainer;
import com.example.aedificantes_calculateur_se_sol.R;

import java.util.ArrayList;
import java.util.List;

public class ErrorDisplayer {

    private ExpandableListView elvInvestments;
    private View CL_global;

    private ErrorListAdpatator errorListAdpatator;
    private Context context;
    private ViewGroup toAddListError;

    private ErrorCreater errorCreater = new ErrorCreater();
    private boolean isShow = true;

    public ErrorDisplayer(Context context, ViewGroup layout_Result) {
        this.context = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CL_global = layoutInflater.inflate(R.layout.list_error_layout, null);

        elvInvestments = CL_global.findViewById(R.id.elvInvestments);

        errorListAdpatator = new ErrorListAdpatator(context);
        elvInvestments.setAdapter(errorListAdpatator);
        toAddListError = layout_Result;

        toAddListError.addView(CL_global);
        CL_global.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    public void display(List<ErrorManager> dataList){
        errorListAdpatator.display(dataList);
        show();
    }
//List<ParamSol> listParam, PieuParamManager pieuManager
    public void generateAndDisplay(ParamContainer paramContainer){
        List<ErrorManager> tampHash = new ArrayList<ErrorManager>();
        tampHash.addAll(errorCreater.generate_LineEmptyError(paramContainer.getSol_list()) );
        tampHash.addAll(errorCreater.generate_PieuEmptyError(paramContainer.getPieuManager()) );
        tampHash.addAll(errorCreater.generate_EauxSouterainesEmptyError(paramContainer.getGroundWater()) );

        display(tampHash);
        errorListAdpatator.notifyDataSetChanged();
        System.out.println(tampHash.size());
    }

    public void hide(){
        if(isShow) {
            //toAddListError.removeView(elvInvestments);
            toAddListError.removeView(CL_global);

            isShow = !isShow;
        }
    }

    public void show(){
        if(!isShow){
            //toAddListError.addView(elvInvestments);
            toAddListError.addView(CL_global);
            isShow = !isShow;
        }
    }


}
