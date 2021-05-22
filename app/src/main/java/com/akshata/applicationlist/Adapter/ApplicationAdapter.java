package com.akshata.applicationlist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akshata.applicationlist.Model.AppList;
import com.akshata.applicationlist.R;

import java.util.List;

public class ApplicationAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<AppList> listStorage;


    public ApplicationAdapter(Context context, List<AppList> customizedListView) {
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
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
            convertView = layoutInflater.inflate(R.layout.installed_app_list, parent, false);
          //  convertView.setOnClickListener(null);
            listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.list_app_name);
            listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.app_icon);
            listViewHolder.versionNameText=convertView.findViewById(R.id.version_name);
//            listViewHolder.versioncodeText=convertView.findViewById(R.id.version_code);
//            listViewHolder.lastUpdate=convertView.findViewById(R.id.last_update);
//            listViewHolder.installationdate=convertView.findViewById(R.id.installed_date);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.textInListView.setText(listStorage.get(position).getName());
        listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
        listViewHolder.versionNameText.setText(listStorage.get(position).getVersionname());
//        listViewHolder.versioncodeText.setText("Version code : "+Integer.toString(listStorage.get(position).getVersioncode()));
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date(listStorage.get(position).getLastUpdate());
//        String dateTime = dateFormat.format(date);
//        listViewHolder.lastUpdate.setText("Last update : "+ dateTime);
//
//        Date installation_date = new Date(listStorage.get(position).getInstalled());
//        String installationdate  = dateFormat.format(installation_date);
//        listViewHolder.installationdate.setText("Installed : "+ installationdate);
        return convertView;
    }

    static class ViewHolder{
        TextView textInListView,versionNameText,versioncodeText,lastUpdate,installationdate;
        ImageView imageInListView;
    }
}