package com.akshata.applicationlist.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
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

public class ProviderActivity extends AppCompatActivity {
    ListView mProvider;
    PackageManager pm;
    String providers;
    ProviderInfo p;
    TextView mTotalProviders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        mProvider=findViewById(R.id.provider);
        mTotalProviders=findViewById(R.id.totalProvider);
        pm = getPackageManager();
        final Context contextnew=getApplicationContext();
        Bundle recdData = getIntent().getExtras();
        providers = recdData.getString("packageName");
        final List<ActivityList> activityLists = getAllRunningProvider();
        ProviderAdapter permissionAdapter = new ProviderAdapter(contextnew, activityLists);
        mProvider.setAdapter(permissionAdapter);
        int  totalpro= mProvider.getAdapter().getCount();
        String total=String.valueOf(totalpro);
        mTotalProviders.setText("("+total+")");

    }
    protected List<ActivityList> getAllRunningProvider() {
        List<ActivityList> res = new ArrayList<ActivityList>();
        PackageInfo packs = null;

        try {
            packs =pm.getPackageInfo(providers, PackageManager.GET_PROVIDERS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packs == null || packs.providers == null) {
            return res;
        }else {
            for (int i = 0; i < packs.providers.length; i++) {
                p = packs.providers[i];
                String[] nameparts;
                String ProvidersName = p.name;
                if (p.name != null && p.name != "") {
                    nameparts = p.name.split("\\.");
                    if (nameparts.length > 0) {
                        ProvidersName = nameparts[nameparts.length - 1];
                    }
                }
                ActivityList temp = new ActivityList(ProvidersName, p.packageName);
                String packagename=packs.packageName;
//                String appname=p.applicationInfo.loadLabel(getPackageManager()).toString();;
                ContentValues contentValues = new ContentValues();
                // get  & set with contentvalues
                contentValues.put(DeviceDB.PROVIDERS_NAME, ProvidersName);
                contentValues.put(DeviceDB.PROVIDERS_PACKAGE_NAME,packagename);
                DeviceDB lb = new DeviceDB(this);
                long row = lb.addProvidersListItem( String.valueOf(p),packagename);
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
