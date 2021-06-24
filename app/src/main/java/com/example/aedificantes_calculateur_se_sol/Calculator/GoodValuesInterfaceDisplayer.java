package com.example.aedificantes_calculateur_se_sol.Calculator;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.aedificantes_calculateur_se_sol.Calculator.ResultButtonLoader;
import com.example.aedificantes_calculateur_se_sol.Calculator.ResultManager;
import com.example.aedificantes_calculateur_se_sol.R;

import java.util.ArrayList;
import java.util.List;

public class GoodValuesInterfaceDisplayer {

    private ViewGroup parent;
    private ViewGroup scene;

    private Button BT_openGlobalResult;

    private boolean isShow = false;

    public GoodValuesInterfaceDisplayer(final ResultButtonLoader buttonLoader, ViewGroup parent) {
        this.parent = parent;

        this.scene = new ConstraintLayout(parent.getContext());
        BT_openGlobalResult = new Button(scene.getContext());
        BT_openGlobalResult.setText("   Poursuivre vers les résultats   ");
        BT_openGlobalResult.setBackgroundResource(R.drawable.open_global_result_button);
        BT_openGlobalResult.setTextColor(Color.parseColor("#4A4A4A"));
        BT_openGlobalResult.setTextSize(30);
        BT_openGlobalResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLoader.launchResultsView();
            }
        });

        this.scene.addView(BT_openGlobalResult);

    }

    public void hide(){
        if(isShow){
            parent.removeView(scene);
            isShow = !isShow;
        }
    }

    public void show(){
        if(!isShow){
            parent.addView(scene);
            //scene.getLayoutParams().width = scene.getLayoutParams().MATCH_PARENT;
            //scene.getLayoutParams().height = scene.getLayoutParams().MATCH_PARENT;
            isShow = !isShow;
        }
    }
}
