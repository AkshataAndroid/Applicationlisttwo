package com.akshata.applicationlist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.akshata.applicationlist.Model.ActivityList;
import com.akshata.applicationlist.R;

import java.util.List;

public class ActivityAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
    private List<ActivityList> activityLists;

    public ActivityAdapter(Context context, List<ActivityList> activityListstemp) {
        layoutInflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        activityLists = activityListstemp;
    }

    @Override
    public int getCount() {
        return activityLists.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.activities_list, parent, false);
            convertView.setOnClickListener(null);
            listViewHolder.textAcitivityName = convertView.findViewById(R.id.list_app_name);
            listViewHolder.textPackagename=convertView.findViewById(R.id.version_name);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.textPackagename.setText(activityLists.get(position).getPackagename());
        listViewHolder.textAcitivityName.setText(activityLists.get(position).getActivityname());

        return convertView;

    }
    static class ViewHolder{
        TextView textAcitivityName,textPackagename;

    }
}
