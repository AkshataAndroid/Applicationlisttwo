package com.akshata.applicationlist.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.admin.DeviceAdminInfo;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akshata.applicationlist.BuildConfig;
import com.akshata.applicationlist.Database.DeviceDB;
import com.akshata.applicationlist.R;
import com.akshata.applicationlist.RootUtil;
import com.akshata.applicationlist.Service.SocketConnectionService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AnalyticsFragment extends Fragment {
    private static final int REQUEST_CODE = 0;
    View view;

    ArrayList<String> SubjectNames;
    RecyclerView recyclerview;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    TextView versionname, securityUpdate, totalApp, totalspace, usedSpace, freeSpaceText, internalPath, userApp, systemapp, rootText, busyboxText, batteryInfo, numberofSim, ImeiOne, ImeiTwo;
    String CVersion;
    DeviceAdminInfo deviceAdminInfo;
    ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5;
    PackageManager pm;
    static int numberOfNonSystemApps = 0;
    int numberOfSystemApps = 0;
    int deviceStatus;
    String currentBatteryStatus = "Battery Info";
    IntentFilter intentfilter;
    String available="Available";
    String unavailable="UnAvailable";
    int batteryLevel;
    public static final String ACTION_LOCATION_BROADCAST = SystemInstalledappFragment.class.getName() + "SystemInstalledBroadcast";
    public static final String CVERSION = "version_name";
    public static final String FREESPACE = "free_space";
//    public static  final String ICON= "image";
//    String cVersion, float freeSpace, float totalSpace, int numberOfInstalledApps,
//    int numberOfSystemApps, String imeiNumber1, String imeiNumber2, String androidId
    int simCount;
    String imeiNumber1;
    String imeiNumber2;

    public static Fragment newInstance(String s, String s1) {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_analytics, container, false);
        versionname = view.findViewById(R.id.text3);
        securityUpdate = view.findViewById(R.id.text5);
        totalApp = view.findViewById(R.id.text12);
        totalspace = view.findViewById(R.id.text17);
        freeSpaceText = view.findViewById(R.id.text19);
        userApp = view.findViewById(R.id.text13);
        systemapp = view.findViewById(R.id.text14);
        progressBar1 = view.findViewById(R.id.progressbar1);
        progressBar2 = view.findViewById(R.id.progressbar2);
        progressBar3 = view.findViewById(R.id.progressbar3);
        progressBar4 = view.findViewById(R.id.progressbar4);
        progressBar5 = view.findViewById(R.id.progressbar5);
        ScrollView sView = view.findViewById(R.id.scrollView);
        usedSpace = view.findViewById(R.id.text18);
        rootText = view.findViewById(R.id.text7);
        internalPath = view.findViewById(R.id.text21);
        busyboxText = view.findViewById(R.id.text9);
        batteryInfo = view.findViewById(R.id.text24);
        numberofSim = view.findViewById(R.id.text31);
        ImeiOne = view.findViewById(R.id.text33);
        ImeiTwo = view.findViewById(R.id.text34);
        sView.setVerticalScrollBarEnabled(false);
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver(
//                new BroadcastReceiver() {
//                    @Override
//                    public void onReceive(Context context, Intent intent) {
//                        String versionName = intent.getStringExtra(SystemInstalledappFragment.VERSION_NAME);
//                        String appName = intent.getStringExtra(SystemInstalledappFragment.APP_NAME);
//
//                        ContentValues contentValues = new ContentValues();
//                        // get  & set with contentvalues
//                        contentValues.put(LocationDB.VERSIONNAME, versionName);
//                        contentValues.put(LocationDB.APP, appName);
//
//                        LocationDB lb = new LocationDB(context);
//                        long row = lb.insert(versionName, appName);
//                        if (row > 0) {
//                            Toast.makeText(context, "Your Location Inserted Successfully....",
//                                    Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "Something Wrong...", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//                }, new IntentFilter(UserInstalledappFragment.ACTION_LOCATION_BROADCAST)
//        );

        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            getActivity().checkCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE");
        }
        String androidId=Settings.System.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
        String androidID2=Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {



            ImeiOne.setText(androidId);

            ImeiTwo.setText(androidID2);

        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            imeiNumber1 = telephonyManager.getDeviceId(1); //(API level 23)
             imeiNumber2 = telephonyManager.getDeviceId(2);
//            String deviceID = ((TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE))
//                    .getDeviceId();
            ImeiOne.setText(imeiNumber1);
            ImeiTwo.setText(imeiNumber2);
        }
        final SubscriptionManager subscriptionManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            subscriptionManager = SubscriptionManager.from(getActivity());
            final List<SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
            simCount = activeSubscriptionInfoList.size();
            numberofSim.setText(simCount+" Sim available");

        }else{
            numberofSim.setText("null");
        }
        int numberOfInstalledApps =getActivity().getPackageManager().getInstalledApplications(0).size();
        String strI = String.valueOf(numberOfInstalledApps);
        totalApp.setText(strI);


        List<ApplicationInfo> appList = getActivity().getPackageManager().getInstalledApplications(0);
        for(ApplicationInfo info : appList) {
            if((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                numberOfNonSystemApps++;
                String user = String.valueOf(numberOfNonSystemApps);
                userApp.setText("User - "+user);
                if (null != progressBar1) {
                    progressBar1.setMax((int) numberOfInstalledApps);
                    progressBar1.setProgress((int)numberOfNonSystemApps);
                }
            }
        }

        List<ApplicationInfo> appListtwo = getActivity().getPackageManager().getInstalledApplications(0);
        for(ApplicationInfo info : appListtwo) {
            if((info.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                numberOfSystemApps++;
                String system = String.valueOf(numberOfSystemApps);
                systemapp.setText("System - " +system);
                if (null != progressBar2) {
                    progressBar2.setMax((int) numberOfInstalledApps);
                    progressBar2.setProgress((int)numberOfSystemApps);
                }
            }
        }
        File dir = Environment.getExternalStorageDirectory();
        String path = dir.getAbsolutePath();
        internalPath.setText(path);
        if(RootUtil.isDeviceRooted()){

            rootText.setText(available);
        }else{
            rootText.setText(unavailable);
        }

        try {
            Process p = Runtime.getRuntime().exec("busybox | head -1");
            InputStream a = p.getInputStream();
            InputStreamReader read = new InputStreamReader(a);

//                String line = (new BufferedReader(read)).readLine();
//                String version = line.split("\\s+")[1];
                busyboxText.setText(available);


        } catch (IOException e) {
            busyboxText.setText(unavailable);
            e.printStackTrace();
        }
        final DecimalFormat outputFormat = new DecimalFormat("#.#");
        final float totalSpace = DeviceMemory.getInternalStorageSpace();
        final float occupiedSpace = DeviceMemory.getInternalUsedSpace();
        final float freeSpace = DeviceMemory.getInternalFreeSpace();
        totalspace.setText(String.valueOf(totalSpace)+" GB");
        if (null != progressBar3) {
            progressBar3.setMax((int) totalSpace);
            progressBar3.setProgress((int)occupiedSpace);
        }
        if (null != usedSpace) {

            usedSpace.setText("Used - "+outputFormat.format(occupiedSpace) + " GB");
        }
        if (null != freeSpaceText) {
            freeSpaceText.setText("Free - "+outputFormat.format(freeSpace) + " GB");
        }
        if (null != progressBar4) {
            progressBar4.setMax((int) totalSpace);
            progressBar4.setProgress((int)freeSpace);
        }

       String CVersion = Build.VERSION.RELEASE;
        String securityupdate=Build.VERSION.SECURITY_PATCH;
        securityUpdate.setText(Build.VERSION.SECURITY_PATCH);
        if(CVersion=="11" ){
            versionname.setText("HoneyComb"+String.valueOf(CVersion));
        }else if(CVersion== "12"){
            versionname.setText("HoneyComb"+String.valueOf(CVersion));
        }
       else if(CVersion== "13"){
            versionname.setText("HoneyComb"+String.valueOf(CVersion));
        }else if(CVersion== "14"){
            versionname.setText("Ice Cream Sandwich"+String.valueOf(CVersion));

        }else if(CVersion== "15"){
            versionname.setText("Ice Cream Sandwich"+String.valueOf(CVersion));
        }else if(CVersion== "16"){
            versionname.setText("Jelly bean"+String.valueOf(CVersion));

        }else if(CVersion== "17"){
            versionname.setText("Jelly bean"+String.valueOf(CVersion));
        }else if(CVersion== "18"){
            versionname.setText("Jelly bean"+String.valueOf(CVersion));
        }else if(CVersion== "19"){
            versionname.setText("Kitkat"+String.valueOf(CVersion));
        }else if(CVersion== "20"){
            versionname.setText("Lollipop"+String.valueOf(CVersion));
        }else if(CVersion== "21"){
            versionname.setText("Lollipop"+String.valueOf(CVersion));
        }else if(CVersion== "22"){
            versionname.setText("Marshmellow"+String.valueOf(CVersion));
        }else if(CVersion== "23"){
            versionname.setText("Nougat"+String.valueOf(CVersion));
        }else if(CVersion== "24"){
            versionname.setText("Nougat"+String.valueOf(CVersion));
        }else if(CVersion== "25"){
            versionname.setText("Nougat"+String.valueOf(CVersion));
        }
        else if(CVersion== "26"){
            versionname.setText("Oreo"+String.valueOf(CVersion));
        }else if(CVersion=="27" ){
            versionname.setText("Oreo"+String.valueOf(CVersion));
        }else if(CVersion== "28"){
            versionname.setText("Pie "+String.valueOf(CVersion));
        }else if(CVersion== "29"){
            versionname.setText("Android 10"+String.valueOf(CVersion));
        }
        ContentValues contentValues = new ContentValues();
        // get  & set with contentvalues
        contentValues.put(DeviceDB.VERSION_NUMBER,CVersion );
        contentValues.put(DeviceDB.SIMCOUNT, String.valueOf(simCount));
        contentValues.put(DeviceDB.TOTAL_SPACE, String.valueOf(totalSpace));
//        contentValues.put(DeviceDB.APP, String.valueOf(freeSpace));


        contentValues.put(DeviceDB.NUMBEROFINSTALLEDAPPS, strI);
        contentValues.put(DeviceDB.IMEINUMBERONE, imeiNumber1);
        contentValues.put(DeviceDB.IMEINUMBERTWO, imeiNumber2);
        contentValues.put(DeviceDB.SECURITY_UPDATE, securityupdate);
        contentValues.put(DeviceDB.INTERNAL_PATH, path);


        DeviceDB lb = new DeviceDB(getContext());
        long row = lb.addDeviceDetailsListItem(CVersion,String.valueOf(simCount), String.valueOf(totalSpace),strI,imeiNumber1,imeiNumber2,securityupdate,path);
        if (row > 0) {
            Toast.makeText(getContext(), "Your Location Inserted Successfully....",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
        }

        sendMessage(CVersion,freeSpace,totalSpace,simCount,numberOfInstalledApps,imeiNumber1,imeiNumber2,androidId);

        return view;
//MobileDetails



    }

    private void sendMessage(String cVersion, float freeSpace, float totalSpace, int numberOfInstalledApps, int numberOfSystemApps, String imeiNumber1, String imeiNumber2, String androidId) {
        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(CVERSION, cVersion);
        intent.putExtra(FREESPACE, freeSpace);

        SocketConnectionService.instance().start(getActivity(), intent, false);
        SocketConnectionService.instance().serviceStatus = true;
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }


    public static String getDeviceIDFromReflection(Context context) {
        String deviceID = "";
        try {
            Class multiSimUtilsClazz = Class.forName("android.provider.MultiSIMUtils");
            Method getDefaultMethod = multiSimUtilsClazz.getMethod("getDefault", Context.class);
            Object object = getDefaultMethod.invoke(null, context);
            Method method = multiSimUtilsClazz.getMethod("getDeviceId", int.class);
            deviceID = (String) method.invoke(object, 0);
        } catch (Exception e) {
            Log.e(TAG,"getPhoneIMEI: err ", e);
        }

        return deviceID;
    }
    public static String getDeviceIDFromSystem(Context context) {
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = null;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE);
            //context.checkCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE");
        }
        if(tm != null) {
            try {
                deviceID = tm.getDeviceId();
            } catch (Exception e) {
                Log.e(TAG, "getPhoneIMEIFromTelephony: no phone imei", e);
            }
        }
        return deviceID;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public void onResume() {
        getActivity().registerReceiver(this.broadcastreceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onResume();
    }

    private BroadcastReceiver broadcastreceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            deviceStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
           batteryLevel=(int)(((float)level / (float)scale) * 100.0f);

            if (null != progressBar5) {
                progressBar5.setMax( scale);
                progressBar5.setProgress(batteryLevel);
            }

            if(deviceStatus == BatteryManager.BATTERY_STATUS_CHARGING){

                batteryInfo.setText("  Charging at "+batteryLevel+" %");


            }

            if(deviceStatus == BatteryManager.BATTERY_STATUS_DISCHARGING){

                batteryInfo.setText(" Discharged at "+batteryLevel+" %");



            }

            if (deviceStatus == BatteryManager.BATTERY_STATUS_FULL){

                batteryInfo.setText(" Battery Full at "+batteryLevel+" %");

            }

            if(deviceStatus == BatteryManager.BATTERY_STATUS_UNKNOWN){

                batteryInfo.setText("  Unknown at "+batteryLevel+" %");
            }


            if (deviceStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING){

                batteryInfo.setText("  Not Charging at "+batteryLevel+" %");

            }

            ContentValues cValues = new ContentValues();

            // get  & set with contentvalues

             cValues.put(DeviceDB.BATTERYSTATUS, String.valueOf(batteryLevel));
//            cValues.put(DeviceDB.BATTERYSTATUS, String.valueOf(deviceStatus));


            DeviceDB db = new DeviceDB(getContext());
            long row = db.addBatteryListItem(String.valueOf(batteryLevel));
            if (row > 0) {
                Toast.makeText(getContext(), "Your Location Inserted Successfully....",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Something Wrong...", Toast.LENGTH_SHORT).show();
            }
        }
    };
    public static class DeviceMemory {

        public static float getInternalStorageSpace() {
//            StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
//            //StatFs statFs = new StatFs("/data");
//            float total = ((float)statFs.getBlockCount() * statFs.getBlockSize()) / 1048;
//            return total;
            final long SIZE_KB = 1024L;
            final long SIZE_GB = SIZE_KB * SIZE_KB * SIZE_KB;
            long availableSpace = 1L;
            StatFs stat = new StatFs(Environment.getDataDirectory().getAbsolutePath());
            float total = ((float)stat.getBlockCount() * stat.getBlockSize()) / availableSpace;
            return total/SIZE_GB;
        }

        public static float getInternalFreeSpace() {
            final long SIZE_KB = 1024L;
            final long SIZE_GB = SIZE_KB * SIZE_KB * SIZE_KB;
            long availableSpace = 1L;
            StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
            //StatFs statFs = new StatFs("/data");
            float free  = ((float)statFs.getAvailableBlocks() * statFs.getBlockSize()) / availableSpace;
            return free/SIZE_GB;
        }

        public static float getInternalUsedSpace() {
            final long SIZE_KB = 1024L;
            final long SIZE_GB = SIZE_KB * SIZE_KB * SIZE_KB;
            long availableSpace = 1L;

            StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
            //StatFs statFs = new StatFs("/data");
            float total = ((float)statFs.getBlockCount() * statFs.getBlockSize()) / availableSpace;
            float free  = ((float)statFs.getAvailableBlocks() * statFs.getBlockSize()) / availableSpace;
            float busy  = total - free;
            return busy/SIZE_GB;
        }
    }
}
