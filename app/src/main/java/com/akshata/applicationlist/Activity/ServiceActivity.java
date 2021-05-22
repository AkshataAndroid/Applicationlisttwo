package com.akshata.applicationlist.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.akshata.applicationlist.Adapter.ServiceAdapter;
import com.akshata.applicationlist.Database.DeviceDB;
import com.akshata.applicationlist.Model.ActivityList;
import com.akshata.applicationlist.R;

import java.util.ArrayList;
import java.util.List;

public class ServiceActivity extends AppCompatActivity {
    ListView mServices;
    Context context;
    PackageManager pm;
    ServiceInfo p;
    TextView totalServices;
    String ServicesName;
    String ser;
    PackageInfo packs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mServices=findViewById(R.id.servicesList);
        totalServices=findViewById(R.id.totalServices);
        Bundle recdData = getIntent().getExtras();
        String servicespackage = recdData.getString("packageName");
        pm = getPackageManager();
        final List<ActivityList> activityLists = getAllRunningServices(servicespackage);
        final Context contextnew=getApplicationContext();
        ServiceAdapter activityAppAdapter = new ServiceAdapter(contextnew, activityLists);
        mServices.setAdapter(activityAppAdapter);
        int  totalser=+mServices.getAdapter().getCount();
        String total=String.valueOf(totalser);
        totalServices.setText("("+total+")");


    }
    protected List<ActivityList> getAllRunningServices( String services ) {
        List<ActivityList> res = new ArrayList<ActivityList>();


        try {
            packs = pm.getPackageInfo(services, PackageManager.GET_SERVICES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packs == null || packs.services == null) {
            return res;
        }else{
            for (int i = 0; i < packs.services.length; i++) {
                p = packs.services[i];
                String[] nameparts;
                String ServicesName = p.name;
                if (p.name != null && p.name != "") {
                    nameparts = p.name.split("\\.");
                    if (nameparts.length > 0) {
                        ServicesName = nameparts[nameparts.length - 1];
                    }
                }
                String packagename=p.packageName;
                ActivityList temp = new ActivityList(ServicesName, packagename);
                ContentValues contentValues = new ContentValues();
                // get  & set with contentvalues
                contentValues.put(DeviceDB.ACTIVITY_NAME, ServicesName);
                contentValues.put(DeviceDB.Activity_PACKAGE_NAME,packagename);
                DeviceDB lb = new DeviceDB(this);
                long row = lb.addServiceListItem( ServicesName,packagename);
                if (row > 0) {
                    Toast.makeText(this, "Your Location Inserted Successfully....",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Something Wrong...", Toast.LENGTH_SHORT).show();
                }
                res.add(temp);
            }
        }


        try {
            PackageInfo pi = getPackageManager().getPackageInfo(
                    services, PackageManager.GET_SERVICES);

            return res;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }


    }
}
