package com.techventures.tucitaconnect.activities.venue.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;


import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.model.domain.service.Service;
import com.techventures.tucitaconnect.utils.common.ViewUtils;

import java.util.List;

public class ExpandableWithoutParentAdapter extends BaseExpandableListAdapter {

    private LayoutInflater inflater;
    private List<Service> services;
    private ViewUtils viewUtils;
    private final ExpandableListView listViewParent;
    private Typeface typeface;


    public ExpandableWithoutParentAdapter(List<Service> services, ExpandableListView listViewParent, ViewUtils viewUtils, Typeface typeface){

        this.services = services;

        this.listViewParent = listViewParent;

        this.viewUtils = viewUtils;

        this.typeface = typeface;

        if (services.size() == 1){

            listViewParent.setGroupIndicator(null);

            viewUtils.setListViewHeightBasedOnChildren(listViewParent);

        }else{

            listViewParent.setGroupIndicator(listViewParent.getResources().getDrawable(R.drawable.indicator));

            viewUtils.setListViewHeightBasedOnChildren(listViewParent);

        }

    }

    public void setInflater(LayoutInflater inflater) {

        this.inflater = inflater;

    }

    @Override
    public int getGroupCount() {

        return 1;

    }

    @Override
    public int getChildrenCount(int groupPosition) {

        int childCount = services.size() - getGroupCount();

        return childCount;

    }

    @Override
    public Object getGroup(int groupPosition) {

        return null;

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return null;

    }

    @Override
    public long getGroupId(int groupPosition) {

        return 0;

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return 0;

    }

    @Override
    public boolean hasStableIds() {

        return false;

    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.item_service, null);

        }

        Service service = services.get(0);

        TextView textView = (TextView) convertView.findViewById(R.id.textName);

        textView.setText(service.getName());

        final TextView textDuration = (TextView) convertView.findViewById(R.id.textDuration);

        textDuration.setTextColor(convertView.getResources().getColor(R.color.blackSecondary));

        textDuration.setText(service.getDurationInfo());

        textView.setTypeface(typeface);

        textDuration.setTypeface(typeface);

        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        childPosition = childPosition + getGroupCount();

        if(services.size() != childPosition) {

            if (convertView == null) {

                Service service = services.get(childPosition);

                convertView = inflater.inflate(R.layout.item_service, null);

                final TextView textName = (TextView) convertView.findViewById(R.id.textName);

                textName.setText(service.getName());

                final TextView textDuration = (TextView) convertView.findViewById(R.id.textDuration);

                textDuration.setTextColor(convertView.getResources().getColor(R.color.blackSecondary));

                textDuration.setText(service.getDurationInfo());

                textName.setTypeface(typeface);

                textDuration.setTypeface(typeface);

            }

        }

        return convertView;

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

        super.onGroupCollapsed(groupPosition);

        viewUtils.setListViewHeightBasedOnChildren(listViewParent);

    }

    @Override
    public void onGroupExpanded(int groupPosition) {

        super.onGroupExpanded(groupPosition);

        viewUtils.setListViewHeightBasedOnChildren(listViewParent);

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;

    }

}
