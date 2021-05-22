package com.akshata.applicationlist.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.akshata.applicationlist.Adapter.ActivityAdapter;
import com.akshata.applicationlist.Database.DeviceDB;
import com.akshata.applicationlist.Model.ActivityList;
import com.akshata.applicationlist.R;

import java.util.ArrayList;
import java.util.List;

public class ActivitiesActivity extends AppCompatActivity {
    ListView mActivities;
    Context context=this;
    PackageManager pm;
    ActivityInfo p;
    TextView totalActivities;
    String activities ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);
        mActivities=findViewById(R.id.activitiesList);
        totalActivities=findViewById(R.id.totalActivity);
//       DeviceDB mydb = new DeviceDB(this);
//        List list = mydb.getAllParentListItem();
        Bundle recdData = getIntent().getExtras();
         activities = recdData.getString("packageName");
         pm = getPackageManager();
        final List<ActivityList> activityLists = getAllRunningActivities();
        final Context contextnew=getApplicationContext();
        final ActivityAdapter activityAppAdapter = new ActivityAdapter(contextnew, activityLists);
      mActivities.setAdapter(activityAppAdapter);
        int  totalact=mActivities.getAdapter().getCount();
        String total=String.valueOf(totalact);
      totalActivities.setText("("+total+")");
        // Toast.makeText(getContext(), "Total number of Items are:" + installedApp.getAdapter().getCount() , Toast.LENGTH_LONG).show();

        // mPermissions.setText(name);


    }
    protected List<ActivityList> getAllRunningActivities() {
        List<ActivityList> res = new ArrayList<ActivityList>();
        PackageInfo packs = null;

        try {
            packs =pm.getPackageInfo(activities, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packs == null || packs.activities == null) {
            return res;
        }else {
            for (int i = 0; i < packs.activities.length; i++) {
                p = packs.activities[i];

                String[] nameparts;
                String Activityname = p.name;
                if (p.name != null && p.name != "") {
                    nameparts = p.name.split("\\.");
                    if (nameparts.length > 0) {
                        Activityname = nameparts[nameparts.length - 1];
                    }
                }
String packagename=packs.packageName;
                String appname=p.applicationInfo.loadLabel(getPackageManager()).toString();;
                ActivityList temp = new ActivityList(Activityname,packagename);
                ContentValues contentValues = new ContentValues();
                // get  & set with contentvalues
                contentValues.put(DeviceDB.ACTIVITY_NAME, Activityname);
                contentValues.put(DeviceDB.Activity_PACKAGE_NAME,packagename);
                DeviceDB lb = new DeviceDB(this);
                long row = lb.addChildListItem( String.valueOf(p),packagename);
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
                   getPackageName(), PackageManager.GET_ACTIVITIES);

            return res;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
