package com.example.aedificantes_calculateur_se_sol.Details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.aedificantes_calculateur_se_sol.Details.TabDetail.DetailTabLauncher;
import com.example.aedificantes_calculateur_se_sol.R;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class DetailsMenuAdaptator extends BaseExpandableListAdapter {

    private List<DetailTitle> list_titleDetails;
    private TreeMap<DetailTitle, List<Detail>> list_ContentDetails;
    private Context context;
    private DetailTabLauncher launcher;

    public DetailsMenuAdaptator(Context context, DetailTabLauncher launcher) {
        System.out.println("create of DetailsMenuAdaptator");
        this.context = context;
        this.launcher = launcher;
        this.list_titleDetails = new ArrayList<>();
        list_ContentDetails = new TreeMap<>();
    }

    public void display(TreeMap<DetailTitle, List<Detail>> toDisplayData){
        list_ContentDetails = toDisplayData;
        list_titleDetails = new ArrayList<>(toDisplayData.keySet());
    }

    @Override
    public int getGroupCount() {
        return list_titleDetails.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list_ContentDetails.get(getGroup(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list_titleDetails.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list_ContentDetails.get(getGroup(groupPosition)).get(childPosition);
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

        TextView TV_detail_string = (TextView) convertView.findViewById(R.id.TV_line_group_error);
        DetailTitle title = this.list_titleDetails.get(groupPosition);
        TV_detail_string.setText(title.getNumber()+" - "+title.getText());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.childelem_list_error, null);
        }

        TextView tvItem = (TextView) convertView.findViewById(R.id.TV_error);

        Detail detail = (Detail) getChild(groupPosition, childPosition);
        tvItem.setText(detail.get_displayableDetail());
        tvItem.setTextSize(25);

        tvItem.setOnClickListener(v -> {
            if(detail.hasTab()){
                launcher.openTabLayout(detail.getTabManager());
            }
        });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
