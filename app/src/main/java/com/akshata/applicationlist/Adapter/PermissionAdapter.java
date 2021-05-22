package com.akshata.applicationlist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.akshata.applicationlist.Model.PermissionList;
import com.akshata.applicationlist.R;

import java.util.List;

public class PermissionAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<PermissionList> permissionLists;


    public PermissionAdapter(Context context, List<PermissionList> customizedListView) {
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        permissionLists = customizedListView;
    }

    @Override
    public int getCount() {
        return permissionLists.size();
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
            convertView = layoutInflater.inflate(R.layout.permission_list, parent, false);
            convertView.setOnClickListener(null);
            listViewHolder.textPermissionName = (TextView)convertView.findViewById(R.id.permissionName);
            listViewHolder.textGranted=convertView.findViewById(R.id.granted);
           // listViewHolder.textNormal=convertView.findViewById(R.id.normalpermission);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.textPermissionName.setText(permissionLists.get(position).getName());
       // listViewHolder.textNormal.setText(permissionLists.get(position).getNormal());
        listViewHolder.textGranted.setText(permissionLists.get(position).getGranted());


        return convertView;
    }

    static class ViewHolder{
        TextView textPermissionName,textGranted,textNormal;

    }

}
