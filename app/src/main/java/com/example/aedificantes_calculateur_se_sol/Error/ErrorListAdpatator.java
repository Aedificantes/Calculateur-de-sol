package com.example.aedificantes_calculateur_se_sol.Error;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.Error;
import com.example.aedificantes_calculateur_se_sol.Error.ErrorObjects.ErrorManager;
import  com.example.aedificantes_calculateur_se_sol.R;

import android.content.Context;
import android.icu.text.AlphabeticIndex;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.SortedList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

public class ErrorListAdpatator extends BaseExpandableListAdapter {
    //private List<Integer> list_ligneWithError;
    //private TreeSet<ErrorManager> list_errorsByLine;
    private List<ErrorManager> list_errorManager;
    private Context context;

    public ErrorListAdpatator(Context context) {
        System.out.println("create of ErrorParamsDisplayer");
        this.context = context;
        //this.list_ligneWithError = new ArrayList<>();
        //list_errorsByLine = new TreeSet<>();
        list_errorManager = new ArrayList<>();
    }

    public void display(List<ErrorManager> toDisplayData){
        //list_errorsByLine = toDisplayData;
        //list_ligneWithError = new ArrayList<>(toDisplayData.keySet());
        list_errorManager = toDisplayData;
        Collections.sort(list_errorManager);
    }

    @Override
    public int getGroupCount() {
        return list_errorManager.size();
        //return list_errorsByLine.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //return list_errorsByLine.get(getGroup(groupPosition)).size();
        return list_errorManager.get(groupPosition).getListError().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        //return list_ligneWithError.get(groupPosition);
        return list_errorManager.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //return list_errorsByLine.get(getGroup(groupPosition)).get(childPosition);
        return list_errorManager.get(groupPosition).getListError().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.groupelem_list_error, null);
        }

        TextView TV_error_string = (TextView) convertView.findViewById(R.id.TV_line_group_error);

        //TV_error_string.setText("Ligne n->"+this.list_ligneWithError.get(groupPosition));
        TV_error_string.setText(this.list_errorManager.get(groupPosition).title());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.childelem_list_error, null);
        }

        TextView tvItem = (TextView) convertView.findViewById(R.id.TV_error);

        Error error = (Error) getChild(groupPosition, childPosition);
        tvItem.setText(Html.fromHtml(error.getError_string()));


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
