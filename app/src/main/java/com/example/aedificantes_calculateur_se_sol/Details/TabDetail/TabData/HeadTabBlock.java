package com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class HeadTabBlock extends TextView_TabBlock{
    boolean isColumnTitle = false;
    public HeadTabBlock(int startWidhtCell, int startHeightCell, int nbWidhtCell, int nbHeightCell, String htmlText) {
        super(startWidhtCell, startHeightCell, nbWidhtCell, nbHeightCell, htmlText);
    }
    public HeadTabBlock(int startWidhtCell, int startHeightCell, int nbWidhtCell, int nbHeightCell, String htmlText, boolean isColumnTitle) {
        super(startWidhtCell, startHeightCell, nbWidhtCell, nbHeightCell, htmlText);
        this.isColumnTitle = isColumnTitle;
    }

    public HeadTabBlock(int startWidhtCell, int startHeightCell, int nbWidhtCell, int nbHeightCell) {
        super(startWidhtCell, startHeightCell, nbWidhtCell, nbHeightCell);
    }

    public boolean isColumnTitle(){
        return this.isColumnTitle;
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

    @NonNull
    @NotNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        HeadTabBlock headTabBlock = new HeadTabBlock(getStartWidhtCell(),getStartHeightCell(),getNbWidhtCell(),getNbHeightCell(),getHtmlText(),isColumnTitle);
        return headTabBlock;
    }
}
