package com.example.aedificantes_calculateur_se_sol.Calculator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import  com.example.aedificantes_calculateur_se_sol.R;

public class ResultDisplayer {
    private Context context;
    private ViewGroup parent;

    private LinearLayout LL_Result;

    private boolean isShow = false;

    public ResultDisplayer(Context context, ViewGroup parent) {
        LL_Result = (LinearLayout) View.inflate(context, R.layout.result_layout, null);
        this.parent = parent;
    }

    public void hide(){
        if(isShow){
            parent.removeView(LL_Result);
            isShow = !isShow;
        }
    }

    public void show(){
        if(!isShow){
            parent.addView(LL_Result);
            LL_Result.getLayoutParams().width = LL_Result.getLayoutParams().MATCH_PARENT;
            LL_Result.getLayoutParams().height = LL_Result.getLayoutParams().MATCH_PARENT;
            isShow = !isShow;
        }
    }
}
