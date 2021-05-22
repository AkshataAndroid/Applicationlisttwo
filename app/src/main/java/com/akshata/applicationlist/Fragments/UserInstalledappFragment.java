package com.akshata.applicationlist.Fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.akshata.applicationlist.Activity.DetailActivity;
import com.akshata.applicationlist.Adapter.ApplicationAdapter;
import com.akshata.applicationlist.Database.DeviceDB;
import com.akshata.applicationlist.Model.AppList;
import com.akshata.applicationlist.R;
import com.akshata.applicationlist.Service.SocketConnectionService;
import com.akshata.applicationlist.Service.SocketService;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserInstalledappFragment extends Fragment {
    PackageManager packageManager;
    View view;
    final SocketService mSocketservice = new SocketService();
    public static final String ACTION_LOCATION_BROADCAST = SystemInstalledappFragment.class.getName() + "SystemInstalledBroadcast";
    public static final String VERSION_NAME = "version_name";
    public static final String APP_NAME = "app_name";
    public static  final String ICON= "image";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        final ListView installedApp= view.findViewById(R.id.installed_app_list);
        final List<AppList> installedApps = getInstalledApps();
        SocketService.instance().Expermemnt(getActivity());
        SocketConnectionService.instance().Expermemnt(getActivity());

        if(mSocketservice.instance().isConnected == true){
            // Toast.makeText(getActivity(),"Socket  connected",Toast.LENGTH_SHORT).show();
            Log.d("Socket","Not Connected");

        }else{
            // Toast.makeText(getActivity(),"Socket not able connect",Toast.LENGTH_SHORT).show();
            Log.d("Socket","Not Connected");
        }


        final ApplicationAdapter installedAppAdapter = new ApplicationAdapter(getContext(), installedApps);
        installedApp.setAdapter(installedAppAdapter);

        installedApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                String TempListViewClickedValue = installedApps.get(position).getName();
                String versionname=installedApps.get(position).getVersionname();
                Drawable image =installedApps.get(position).getIcon();
//                LocalBroadcastManager.getInstance(getContext()).registerReceiver(
//                        new BroadcastReceiver() {
//                            @Override
//                            public void onReceive(Context context, Intent intent) {
//                                String versionName = intent.getStringExtra(UserInstalledappFragment.VERSION_NAME);
//                                String appName = intent.getStringExtra(UserInstalledappFragment.APP_NAME);
//                               // String uid = intent.getStringExtra(DetailActivity.UID);
//
//                                ContentValues contentValues = new ContentValues();
//                                // get  & set with contentvalues
//                                contentValues.put(LocationDB.VERSIONNAME, versionName);
//                                contentValues.put(LocationDB.APP, appName);
//                               // contentValues.put(LocationDB.TARGET_SDK, uid);
//                                LocationDB lb = new LocationDB(context);
//                                long row = lb.insert(versionName, appName);
//                                if (row > 0) {
//                                    Toast.makeText(context, "Your Location Inserted Successfully....",
//                                            Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(context, "Something Wrong...", Toast.LENGTH_SHORT).show();
//                                }
//
//
//                            }
//                        }, new IntentFilter(UserInstalledappFragment.ACTION_LOCATION_BROADCAST)
//                );
                //   if(image==null)
//                if(image == adaptiveIconDrawable){
//                    try {
//                        Drawable drawable = packageManager.getApplicationIcon(packageName);
//                        Bitmap bmp = ((BitmapDrawable)drawable).getBitmap();
//                        intent.putExtra("image",bmp);
//                    } catch (PackageManager.NameNotFoundException e) {
//                        e.printStackTrace();
//                    }

//                }else{
                //  Bitmap bmp = ((BitmapDrawable)image).getBitmap();
                Bitmap bmp = ((BitmapDrawable)image).getBitmap();
                if(image instanceof BitmapDrawable)
                {

                    intent.putExtra("image",bmp);
                }
                else if(image instanceof AdaptiveIconDrawable) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        Drawable backgroundDr = ((AdaptiveIconDrawable) image).getBackground();
                        Drawable foregroundDr = ((AdaptiveIconDrawable) image).getForeground();

                        Drawable[] drr = new Drawable[2];
                        drr[0] = backgroundDr;
                        drr[1] = foregroundDr;

                        LayerDrawable layerDrawable = new LayerDrawable(drr);

                        int width = layerDrawable.getIntrinsicWidth();
                        int height = layerDrawable.getIntrinsicHeight();

                        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                        Canvas canvas = new Canvas(bitmap);

                        layerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        layerDrawable.draw(canvas);
                        intent.putExtra("image",bitmap);
                    }
                    else
                    {
                        //  Bitmap bmp = ((BitmapDrawable)image).getBitmap();
                        intent.putExtra("image",bmp);
                    }
                }

                //   }


                //  Bitmap bitmap = BitmapFactory.decodeResource(getResources(),image);

                intent.putExtra("name", TempListViewClickedValue);
                intent.putExtra("packageName",versionname);


                startActivity(intent);
                //    mSocketservice.stream.emit("sending data",versionname,TempListViewClickedValue);

                sendMessage(versionname,TempListViewClickedValue,image);

            }
        });
//        int total=installedApp.getAdapter().getCount();
//        String strI = String.valueOf(total);
//        AnalyticsFragment fragment= new AnalyticsFragment();
//        Bundle bdl=new Bundle();
//        bdl.putInt("a",total);
//        fragment.setArguments(bdl);

        //   Toast.makeText(getContext(), "Total number of Items are:" + installedApp.getAdapter().getCount() , Toast.LENGTH_LONG).show();
        // String cid= String.valueOf(total);
        return view;
    }
    private void sendMessage(String versionname,String appName,Drawable icon) {
        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(VERSION_NAME, versionname);
        intent.putExtra(APP_NAME, appName);
        intent.putExtra(ICON, String.valueOf(icon));

        SocketConnectionService.instance().start(getActivity(), intent, false);
        SocketConnectionService.instance().serviceStatus = true;
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private List<AppList> getInstalledApps() {

        List<AppList> res = new ArrayList<AppList>();
        List<PackageInfo> packs = getActivity().getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!isSystemPackage(p))) {

                String appName = p.applicationInfo.loadLabel(getActivity().getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getActivity().getPackageManager());
                String packgeName = p.packageName;
                String  vname=p.versionName;
                String  vcode=String.valueOf(p.versionCode);
                String target=String.valueOf(p.applicationInfo.targetSdkVersion);
                String uid=String.valueOf(p.applicationInfo.uid);
                String datadir=String.valueOf(p.applicationInfo.dataDir);
                SimpleDateFormat updateFormat = new SimpleDateFormat("yyyy-MM-dd (HH:mm:ss)");
                Date lastupdate = new Date(p.lastUpdateTime);
                Date firstinstall = new Date(p.firstInstallTime);
                String update = updateFormat.format(lastupdate);
                String install = updateFormat.format(firstinstall);
                String installeddatetime=String.valueOf(install);
                String lastupdatetime =String.valueOf(update);

                String  activities= null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    activities = String.valueOf(p.applicationInfo.minSdkVersion);

                }


                // String code=String.valueOf(versionCode);

                Collections.sort(res, new Comparator<AppList>() {
                    @Override
                    public int compare(AppList o1, AppList o2) {
                        return o1.getName().compareTo(o2.getName());
                    }


                });
                ContentValues contentValues = new ContentValues();
                // get  & set with contentvalues
                contentValues.put(DeviceDB.VERSIONNAME,packgeName );
                contentValues.put(DeviceDB.APP, appName);
                contentValues.put(DeviceDB.VNAME, vname);
                contentValues.put(DeviceDB.VCODE, vcode);
                contentValues.put(DeviceDB.Activities, activities);
                contentValues.put(DeviceDB.TARGET, target);
                contentValues.put(DeviceDB.UID, uid);
                contentValues.put(DeviceDB.INSTALLDATE, installeddatetime);
                contentValues.put(DeviceDB.LASTUPATEDATE, lastupdatetime);
                contentValues.put(DeviceDB.DATA_DIR, datadir);


                DeviceDB lb = new DeviceDB(getContext());
                long row = lb.insert(packgeName, appName,vname,vcode,activities,target,uid,installeddatetime,lastupdatetime,datadir);
                if (row > 0) {
                    Toast.makeText(getContext(), "Your Location Inserted Successfully....",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
                }



                // String versionCode = p.applicationInfo.loadLogo(getActivity().getPackageManager().getDrawable(packageManager));
//                long lastUpdated =  p.lastUpdateTime;
//                long installed =  p.firstInstallTime;
                res.add(new AppList(appName,icon, packgeName));
            }
        }
        return res;

    }
    private boolean isSystemPackage(PackageInfo pkgInfo) {
        boolean isSystemApp = ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
        return isSystemApp;
    }
}

