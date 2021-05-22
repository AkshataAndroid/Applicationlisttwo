package com.akshata.applicationlist.Service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;

@SuppressLint("NewApi")
public final class SocketConnectionService implements ViewTreeObserver.OnWindowFocusChangeListener {
    Context context;

    public static boolean m_started = false;

    private Intent m_customData;
    private static final SocketConnectionService m_instance = new SocketConnectionService();
//    private ControlInjector m_controlInjection = new ControlInjector();

    public static Activity m_currentActivity;

    public static String pkgName = null;
    public static Boolean serviceStatus = false;

    public static SocketConnectionService instance() {
        return m_instance;
    }

    static String version() {
        return "1.0.0";
    }
    //3
    private void reinitialize() {
        Log.d("TAG", "message Yo yo yo");


        // this.m_device.runRegistrationLoop();
    }
    //2
    public SocketConnectionService start(Application application, Intent customData) {
        this.m_customData = customData;
        if (this.m_started) {
            return this;
        }
        this.m_started = true;
        Log.i("ListOfApp", "Initialising Socket " + version());
        context = application.getApplicationContext();
        Intent startServerIntent = new Intent(context, SocketService.class);
        startServerIntent.setAction("START");
        context.startService(startServerIntent);
        reinitialize();
        return this;
    }

    //1
    public SocketConnectionService start(Activity currentActivity, Intent i, boolean b) {
        this.m_customData = i;
        this.m_started = b;
        //this.m_currentActivity = null;

        start(currentActivity.getApplication(), i);
        setActivity(currentActivity);
        return this;
    }
    //4
    void setActivity(Activity activity) {
        if (this.m_currentActivity == activity) {
            return;
        }
        this.m_currentActivity = activity;
//        this.m_controlInjection.setActivity(activity);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

    }

    public String api() {
        if (pkgName == null) {
            pkgName = context.getPackageName();
        }
        ApplicationInfo app = null;
        try {
            app = context.getPackageManager().getApplicationInfo(pkgName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Bundle bundle = app.metaData;

        String mData = bundle.getString("applisturl");

        return mData;
    }

    public SocketConnectionService customData(Intent customData) {
        this.m_customData = customData;

        return this;
    }

    public Intent customData() {
        return this.m_customData;
    }

    Activity getActivity() {
        return this.m_currentActivity;
    }

    public void Expermemnt(Activity mainActivity) {
        //   Toast.makeText(mainActivity, "Hello there", Toast.LENGTH_SHORT).show();
    }


}
