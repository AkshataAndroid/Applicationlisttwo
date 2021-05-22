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

public class ServiceAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<ActivityList> servicelist;

    public ServiceAdapter(Context context, List<ActivityList> serviceListstemp) {
        layoutInflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        servicelist = serviceListstemp;
    }

    @Override
    public int getCount() {
        return servicelist.size();
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
            convertView = layoutInflater.inflate(R.layout.service_list, parent, false);
            convertView.setOnClickListener(null);
            listViewHolder.textServiceName = convertView.findViewById(R.id.servicesName);
            listViewHolder.textPackagename=convertView.findViewById(R.id.version_name);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.textServiceName.setText(servicelist.get(position).getActivityname());
        listViewHolder.textPackagename.setText(servicelist.get(position).getPackagename());

        return convertView;

    }
    static class ViewHolder{
        TextView textServiceName,textPackagename;

    }
}
