package com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class ContentTabBlock extends TextView_TabBlock{
    public ContentTabBlock(int startWidhtCell, int startHeightCell, int nbWidhtCell, int nbHeightCell, String htmlText, boolean isMarked) {
        super(startWidhtCell, startHeightCell, nbWidhtCell, nbHeightCell, htmlText);
        setMarqued(isMarked);
    }

    public ContentTabBlock(int startWidhtCell, int startHeightCell, int nbWidhtCell, int nbHeightCell) {
        super(startWidhtCell, startHeightCell, nbWidhtCell, nbHeightCell);
    }

    @Override
    public View getElementView(Context context) {
        TextView tamp = super.getTextView(context);
        tamp.setTextSize(18);
        //tamp.setTypeface(null, Typeface.BOLD);
        tamp.setGravity(Gravity.CENTER);
        if(isMarqued()){
            tamp.setBackgroundColor(Color.GREEN);
        }
        return tamp;
    }
}
