package com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class HeadTabBlock extends TextView_TabBlock{
    public HeadTabBlock(int startWidhtCell, int startHeightCell, int nbWidhtCell, int nbHeightCell, String htmlText) {
        super(startWidhtCell, startHeightCell, nbWidhtCell, nbHeightCell, htmlText);
    }

    public HeadTabBlock(int startWidhtCell, int startHeightCell, int nbWidhtCell, int nbHeightCell) {
        super(startWidhtCell, startHeightCell, nbWidhtCell, nbHeightCell);
    }

    @Override
    public View getElementView(Context context) {
        TextView tamp = super.getTextView(context);
        tamp.setTextSize(20);
        tamp.setTypeface(null, Typeface.BOLD);
        tamp.setGravity(Gravity.CENTER);
        tamp.setBackgroundColor(Color.parseColor("#978BF2"));
        return tamp;
    }
}