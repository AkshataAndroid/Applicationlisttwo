package com.akshata.applicationlist.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.akshata.applicationlist.Adapter.PermissionAdapter;
import com.akshata.applicationlist.Database.DeviceDB;
import com.akshata.applicationlist.Model.PermissionList;
import com.akshata.applicationlist.R;

import java.util.ArrayList;
import java.util.List;

public class PermissionActivity extends Activity {
    ListView mPermissions;
    PackageManager pm;
    String p;
    String perm;
    TextView mTotalPermissions;
    String permissiongr=android.Manifest.permission.PACKAGE_USAGE_STATS;
    String grant,reject;
    String PermissionName;
    ImageView backimage;
    int targetSdkVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        mPermissions=findViewById(R.id.permissions);
        mTotalPermissions=findViewById(R.id.totalPermission);
        pm = getPackageManager();
        final Context contextnew=getApplicationContext();
        backimage=findViewById(R.id.imageback);
        Bundle recdData = getIntent().getExtras();
        String packageName = recdData.getString("packageName");
        final List<PermissionList> activityLists = getPermissionsByPackageName(packageName);

        PermissionAdapter permissionAdapter = new PermissionAdapter(contextnew, activityLists);
        mPermissions.setAdapter(permissionAdapter);
        int  totalper= mPermissions.getAdapter().getCount();
        String total=String.valueOf(totalper);
        mTotalPermissions.setText("("+total+")");
//        backimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(PermissionActivity.this,DetailActivity.class);
//                startActivity(i);
//            }
//        });


    }
    protected List<PermissionList> getPermissionsByPackageName(String packageName){
        // Initialize a new string builder instance
        List<PermissionList> res = new ArrayList<PermissionList>();
        PackageInfo packs = null;
        try {
            packs =pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        PackageManager mgr=getPackageManager();
        if (packs == null || packs.requestedPermissions == null) {
            return res;
        }else {

            for (int i = 0; i < packs.requestedPermissions.length; i++) {
                if ((packs.requestedPermissionsFlags[i]) != 0) {
                    String permissions = packs.requestedPermissions[i];
                    String[] nameparts;
                    PermissionName = permissions;

                    if (permissions != null && permissions != "") {
                        nameparts = permissions.split("\\.");
                        if (nameparts.length > 0) {
                            PermissionName = nameparts[nameparts.length - 1];


                        }
                    }

                    if (PackageManager.PERMISSION_GRANTED == mgr.checkPermission(permissions, packageName)) {
                        // if (PackageManager.PERMISSION_GRANTED == mgr.checkPermission(android.Manifest.permission.INTERNET,packageName)) {

                        grant = "granted";
                        // }
                    } else {
                        grant = "Rejected";
                    }
                    String packagename=packs.packageName;

                    PermissionList temp = new PermissionList(PermissionName, grant);
                    ContentValues contentValues = new ContentValues();
                    // get  & set with contentvalues
                    contentValues.put(DeviceDB.PERMISSIOJ_NAME, PermissionName);
                    contentValues.put(DeviceDB.PERMISSION_PACKAGE_NAME,packagename);
                    DeviceDB lb = new DeviceDB(this);
                    long row = lb.addPermissionListItem( PermissionName,packagename);
                    if (row > 0) {
                        Toast.makeText(this, "Your Location Inserted Successfully....",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Something Wrong...", Toast.LENGTH_SHORT).show();
                    }
                    res.add(temp);
                }

            }
        }

        try {
            PackageInfo pi = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_PERMISSIONS);

            return res;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }




        }
