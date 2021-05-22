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

public class ProviderAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<ActivityList> providerList;

    public ProviderAdapter(Context context, List<ActivityList> serviceListstemp) {
        layoutInflater =(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        providerList = serviceListstemp;
    }

    @Override
    public int getCount() {
        return providerList.size();
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
            listViewHolder.textPackageName=convertView.findViewById(R.id.version_name);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.textServiceName.setText(providerList.get(position).getActivityname());
        listViewHolder.textPackageName.setText(providerList.get(position).getPackagename());


        return convertView;

    }
    static class ViewHolder{
        TextView textServiceName,textPackageName;

    }
}
