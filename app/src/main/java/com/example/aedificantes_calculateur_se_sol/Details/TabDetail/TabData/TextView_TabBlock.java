package com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class TextView_TabBlock extends TabBlock implements Serializable {

    private String htmlText;

    public TextView_TabBlock(int startWidhtCell, int startHeightCell, int nbWidhtCell, int nbHeightCell, String htmlText) {
        super(startWidhtCell, startHeightCell, nbWidhtCell, nbHeightCell);
        this.htmlText = htmlText;
    }
    public TextView_TabBlock(int startWidhtCell, int startHeightCell, int nbWidhtCell, int nbHeightCell) {
        super(startWidhtCell, startHeightCell, nbWidhtCell, nbHeightCell);
        this.htmlText = "";
    }

    public void setHtmlText(String htmlText){
        this.htmlText = htmlText;
    }

    public TextView getTextView(Context context){
        TextView tamp_tv = new TextView(context);
        tamp_tv.setText(Html.fromHtml(htmlText));
        return tamp_tv;
    }

    @Override
    public View getElementView(Context context) {
        return getTextView(context);
    }
}
