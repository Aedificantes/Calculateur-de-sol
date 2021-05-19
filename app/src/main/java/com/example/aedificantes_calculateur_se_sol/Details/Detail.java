package com.example.aedificantes_calculateur_se_sol.Details;

import android.text.Html;
import android.text.Spanned;

import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.TabData.TabBlockManager;

public class Detail {
    private String html_converted_detail="";
    private TabBlockManager tabBlockManager;
    private boolean hasTab = false;

    public Detail(String str_detail) {
        this.html_converted_detail = str_detail;
    }

    public Detail(TabBlockManager drawer){
        this.hasTab = true;
        tabBlockManager = drawer;
    }


    public void add_String(String str){
        html_converted_detail += str;
    }
    public void add_String_ln(String str){
        add_String(str);
        add_LineReturn();
    }
    public void add_ln_String(String str){
        add_LineReturn();
        add_String(str);
    }
    public void add_ln_String_ln(String str){
        add_LineReturn();
        add_String(str);
        add_LineReturn();
    }
    public void add_LineReturn(){
        html_converted_detail += "<br/>";
    }


    public Spanned get_displayableDetail(){
        return Html.fromHtml(html_converted_detail);
    }

    public TabBlockManager getTabManager() {
        return tabBlockManager;
    }

    public boolean hasTab() {
        return hasTab;
    }
}
