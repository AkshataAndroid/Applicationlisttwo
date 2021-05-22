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

import com.akshata.applicationlist.Adapter.ProviderAdapter;
import com.akshata.applicationlist.Database.DeviceDB;
import com.akshata.applicationlist.Model.ActivityList;
import com.akshata.applicationlist.R;

import java.util.ArrayList;
import java.util.List;

public class BroadcastsActivity extends AppCompatActivity {
    ListView mBroadcasts;
    PackageManager pm;
    String broadcasters;
    ActivityInfo p;
    TextView mTotalBroadcasters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcasts);
        mBroadcasts=findViewById(R.id.broadcastes);
        mTotalBroadcasters=findViewById(R.id.totalBroadcasts);
        pm = getPackageManager();
        final Context contextnew=getApplicationContext();
        Bundle recdData = getIntent().getExtras();
        broadcasters = recdData.getString("packageName");
        final List<ActivityList> activityLists = getAllRunningProvider();
        ProviderAdapter permissionAdapter = new ProviderAdapter(contextnew, activityLists);
        mBroadcasts.setAdapter(permissionAdapter);
        int  totalbrod= mBroadcasts.getAdapter().getCount();
        String total=String.valueOf(totalbrod);
        mTotalBroadcasters.setText("("+total+")");

    }
    protected List<ActivityList> getAllRunningProvider() {
        List<ActivityList> res = new ArrayList<ActivityList>();
        PackageInfo packs = null;

        try {
            packs =pm.getPackageInfo(broadcasters, PackageManager.GET_RECEIVERS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packs == null || packs.receivers == null) {
            return res;
        }else {
            for (int i = 0; i < packs.receivers.length; i++) {
                p = packs.receivers[i];
                String[] nameparts;
                String BroadcasterName = p.name;
                if (p.name != null && p.name != "") {
                    nameparts = p.name.split("\\.");
                    if (nameparts.length > 0) {
                        BroadcasterName = nameparts[nameparts.length - 1];
                    }
                }
                ActivityList temp = new ActivityList(BroadcasterName, p.packageName);
                String packagename=packs.packageName;
//                String appname=p.applicationInfo.loadLabel(getPackageManager()).toString();;
                ContentValues contentValues = new ContentValues();
                // get  & set with contentvalues
                contentValues.put(DeviceDB.BROADCAST_NAME, BroadcasterName);
                contentValues.put(DeviceDB.BROADCAST_PACKAGE_NAME,packagename);
                DeviceDB lb = new DeviceDB(this);
                long row = lb.addBroadcastersListItem( String.valueOf(p),packagename);
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
                    getPackageName(), PackageManager.GET_PROVIDERS);

            return res;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
