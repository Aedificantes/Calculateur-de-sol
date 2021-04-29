package com.example.aedificantes_calculateur_se_sol.Error;
import  com.example.aedificantes_calculateur_se_sol.R;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ErrorListAdpatator extends BaseExpandableListAdapter {
    private List<Integer> list_ligneWithError;
    private HashMap<Integer, List<Error>> list_errorsByLine;
    private Context context;

    public ErrorListAdpatator(Context context) {
        System.out.println("create of ErrorParamsDisplayer");
        this.context = context;
        this.list_ligneWithError = new ArrayList<>();
        list_errorsByLine = new HashMap<>();
    }

    public void display(HashMap<Integer, List<Error>> toDisplayData){
        list_errorsByLine = toDisplayData;
        list_ligneWithError = new ArrayList<>(toDisplayData.keySet());
    }

    @Override
    public int getGroupCount() {
        return list_ligneWithError.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list_errorsByLine.get(getGroup(groupPosition)).size();

    }

    @Override
    public Object getGroup(int groupPosition) {
        return list_ligneWithError.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list_errorsByLine.get(getGroup(groupPosition)).get(childPosition);
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

        TV_error_string.setText("Ligne n°"+(groupPosition+1));

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
        tvItem.setText(error.getError_string());


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
