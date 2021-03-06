package com.example.aedificantes_calculateur_se_sol.Calculator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import  com.example.aedificantes_calculateur_se_sol.R;

import java.util.ArrayList;
import java.util.List;

/**
 * this class has to display element in give, viewParent
 * give table result from calculate value
 */
public class ResultDisplayer {

    private final String LOG_TAG = "CLA_9512";

    private ViewGroup parent;

    private List<TextView> list_TV_tab1 = new ArrayList<>();
    private List<TextView> list_TV_tab2 = new ArrayList<>();

    private LinearLayout LL_Result;
    private LinearLayout LL_tab2_couche;
    private LinearLayout LL_tab2_profondeur;
    private LinearLayout LL_tab2_hauteur;
    private LinearLayout LL_tab2_f;

    private boolean isShow = false;

    public ResultDisplayer(ViewGroup parent) {
        LL_Result = (LinearLayout) View.inflate(parent.getContext(), R.layout.result_layout, null);
        this.parent = parent;
        LL_tab2_couche = LL_Result.findViewById(R.id.LL_tab2_couche);
        LL_tab2_profondeur = LL_Result.findViewById(R.id.LL_tab2_profondeur);
        LL_tab2_hauteur = LL_Result.findViewById(R.id.LL_tab2_hauteur);
        LL_tab2_f = LL_Result.findViewById(R.id.LL_tab2_f);

        list_TV_tab1.add((TextView) LL_Result.findViewById(R.id.TV_Tab1_0));
        list_TV_tab1.add((TextView) LL_Result.findViewById(R.id.TV_Tab1_1));
        list_TV_tab1.add((TextView) LL_Result.findViewById(R.id.TV_Tab1_2));
        list_TV_tab1.add((TextView) LL_Result.findViewById(R.id.TV_Tab1_3));
        list_TV_tab1.add((TextView) LL_Result.findViewById(R.id.TV_Tab1_4));
        list_TV_tab1.add((TextView) LL_Result.findViewById(R.id.TV_Tab1_5));
        list_TV_tab1.add((TextView) LL_Result.findViewById(R.id.TV_Tab1_6));
        list_TV_tab1.add((TextView) LL_Result.findViewById(R.id.TV_Tab1_7));

        list_TV_tab2.add((TextView) LL_Result.findViewById(R.id.TV_Tab2_4));
        list_TV_tab2.add((TextView) LL_Result.findViewById(R.id.TV_Tab2_5));

        list_TV_tab2.add((TextView) LL_Result.findViewById(R.id.TV_Tab2_6));
        list_TV_tab2.add((TextView) LL_Result.findViewById(R.id.TV_Tab2_7));
        list_TV_tab2.add((TextView) LL_Result.findViewById(R.id.TV_Tab2_8));

        list_TV_tab2.add((TextView) LL_Result.findViewById(R.id.TV_Tab2_9));
        list_TV_tab2.add((TextView) LL_Result.findViewById(R.id.TV_Tab2_10));
        list_TV_tab2.add((TextView) LL_Result.findViewById(R.id.TV_Tab2_11));

    }

    public void updateData(ResultManager resultManager){
        list_TV_tab1.get(0).setText(String.valueOf(resultManager.getLayerCalculator().index_couchePortante()));
        list_TV_tab1.get(1).setText(String.valueOf(resultManager.getLayerCalculator().profondeurPieu()));
        list_TV_tab1.get(4).setText(String.valueOf(resultManager.fd0_Acomp()));
        list_TV_tab1.get(6).setText(String.valueOf(resultManager.fd0_comp()));
        list_TV_tab1.get(5).setText(String.valueOf(resultManager.fd0_ATrac()));
        list_TV_tab1.get(7).setText(String.valueOf(resultManager.fd0_trac()));

        LL_tab2_couche.removeAllViews();
        LL_tab2_profondeur.removeAllViews();
        LL_tab2_hauteur.removeAllViews();
        LL_tab2_f.removeAllViews();
        for(int  i=0 ; i< resultManager.getLayerCalculator().index_couchePortante(); i++) {
            System.out.println("create of TabLine");
            LL_tab2_couche.addView(create_TV_withWeight(LL_tab2_couche.getContext(), String.valueOf(i)));
            LL_tab2_profondeur.addView(create_TV_withWeight(LL_tab2_profondeur.getContext(), String.valueOf(resultManager.getLayerCalculator().profondeur_couche_index(i))));
            LL_tab2_hauteur.addView(create_TV_withWeight(LL_tab2_hauteur.getContext(), resultManager.enfoncement_couche_index_ToDisplay(i)));
            LL_tab2_f.addView(create_TV_withWeight(LL_tab2_f.getContext(), resultManager.resistanceSol_couche_toDisplay(i)));
        }
        list_TV_tab2.get(0).setText(String.valueOf(resultManager.perimetre_section_transfersale_fut_toDisplay()));
        list_TV_tab2.get(1).setText(String.valueOf(resultManager.fdf_toDisplay()));

        list_TV_tab2.get(2).setText(String.valueOf(resultManager.fdcomp_toDisplay()));
        list_TV_tab2.get(3).setText(String.valueOf(resultManager.fdtrac_toDisplay()));
        list_TV_tab2.get(4).setText(String.valueOf(resultManager.fdvar_toDisplay()));

        list_TV_tab2.get(5).setText("Capacit?? portante sous: charges de compression Fd ( ??c = "+resultManager.getCoef_comp()+")");
        list_TV_tab2.get(6).setText("charges de traction Fd ( ??c = "+resultManager.getCoef_trac()+")");
        list_TV_tab2.get(7).setText("charges variables Fd ( ??c = "+resultManager.getCoef_variableLoad()+")");

    }

    private TextView create_TV_withWeight(Context context, String text){
        TextView tampTV = new TextView(context);
        tampTV.setText(text);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1.0f
        );
        tampTV.setLayoutParams(param);
        tampTV.setGravity(Gravity.CENTER);
        return tampTV;
    }

    public void show(){
        Log.d(LOG_TAG,"Le ResultDIsplayer ajoute son tableau ?? la view");
        parent.addView(LL_Result);
        LL_Result.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        LL_Result.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
    }

    public void show(int widht, int height){
        Log.d(LOG_TAG,"Le ResultDIsplayer ajoute son tableau ?? la view avec un taille d??fini : w:"+widht+" h:"+height);
        parent.addView(LL_Result);

        LL_Result.getLayoutParams().height = height;
        LL_Result.getLayoutParams().width = widht;
    }
}
