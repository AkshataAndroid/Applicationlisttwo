package com.akshata.applicationlist.Activity;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.akshata.applicationlist.BuildConfig;
import com.akshata.applicationlist.Fragments.SystemInstalledappFragment;
import com.akshata.IPackageStatsObserver;
import com.akshata.applicationlist.R;
import com.akshata.applicationlist.Service.SocketConnectionService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class DetailActivity  extends Activity implements View.OnClickListener {

    TextView textView,textVersion,textinstalledLocation,
            textPackage,textVersionCode,textLocation,textMinimum,textTarget,textUid,textiInstalldate,
            textUpdateDate,textapkDirectory,textdataDir,textdataprotecteddir,textnativelibraries,textsplitApk,textmin,textTitle;
    ImageView mIcon,mPermission,mActivities,mServices,mCertificates,mResourse,mBrodcast,mProvider,mManifest;
    ImageView backButton;
    ScrollView scrollView;
    PackageInfo packageInfo;
    PackageManager pm;
    String packageName;
    AppOpsManager   appOpsManager;
    public static final String ACTION_LOCATION_BROADCAST = SystemInstalledappFragment.class.getName() + "SystemInstalledBroadcast";
    String versionName;
    String dataDir;
    String versionCode;
    String minSdkVersion;
    String targetSdkVersion;
    String uid;

    public static final String VERSION_NAME = "version_name";
    public static final String VERSION_CODE = "version_code";
    public static final String DATA_DIR = "data";
    public static final String MINIMUM_SDK = "min";
    public static final String TARGET_SDK = "tar";
    public static final String UID = "uid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatils);
        Bundle recdData = getIntent().getExtras();
        String name = recdData.getString("name");
        packageName= recdData.getString("packageName");
        Bitmap bitmap = (Bitmap)this.getIntent().getParcelableExtra("image");
        ScrollView sView = findViewById(R.id.scrollView1);
        sView.setVerticalScrollBarEnabled(false);
//Textview
        textView = (TextView) findViewById(R.id.text);
        mIcon = findViewById(R.id.application_image);
        textVersion=findViewById(R.id.version);
        textPackage=findViewById(R.id.text3);
        backButton= findViewById(R.id.imageback);
        textLocation=findViewById(R.id.text7);
        textVersionCode=findViewById(R.id.text5);
        textMinimum=findViewById(R.id.text9);
        textTarget=findViewById(R.id.text11);
        textUid=findViewById(R.id.text13);
        textiInstalldate=findViewById(R.id.text15);
        textUpdateDate=findViewById(R.id.text17);
        textapkDirectory=findViewById(R.id.textapp9);
        textdataDir=findViewById(R.id.textapp11);
        textinstalledLocation=findViewById(R.id.text7);
        textdataprotecteddir=findViewById(R.id.textapp13);
        textnativelibraries=findViewById(R.id.textapp15);
        textsplitApk=findViewById(R.id.textapp5);
        textmin=findViewById(R.id.text8);
        scrollView=findViewById(R.id.scrollView1);
        textTitle=findViewById(R.id.titleAppName);
        textTitle.setMovementMethod(new ScrollingMovementMethod());
        textTitle.setHorizontallyScrolling(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if(scrollY==0) {
                        textTitle.setVisibility(View.GONE);
                    }
                    else{   textTitle.setVisibility(View.VISIBLE);

//                    }else{
//                        textTitle.setVisibility(View.GONE);
//
                    }
                }
            });
        }


        //Imageview
        mPermission=findViewById(R.id.image1);
        mActivities=findViewById(R.id.image2);
        mServices=findViewById(R.id.image3);
        mCertificates=findViewById(R.id.image4);
        mResourse=findViewById(R.id.image5);
        mBrodcast=findViewById(R.id.image6);
        mProvider=findViewById(R.id.image7);
        mManifest=findViewById(R.id.image8);

        //Onclicklistener
        mPermission.setOnClickListener(this);
        mActivities.setOnClickListener(this);
        mServices.setOnClickListener(this);
        mCertificates.setOnClickListener(this);
        mResourse.setOnClickListener(this);
        mBrodcast.setOnClickListener(this);
        mProvider.setOnClickListener(this);
        mManifest.setOnClickListener(this);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(DetailActivity.this,MainActivity.class);
                startActivity(i);

            }
        });
        Context context = getApplicationContext();
        PackageManager manager = context.getPackageManager();

        try {
//            PackageManager pm = getPackageManager();

            Method getPackageSizeInfo = manager.getClass().getMethod(
                    "getPackageSizeInfo", String.class, IPackageStatsObserver.class);

            getPackageSizeInfo.invoke(manager, packageName,
                    new IPackageStatsObserver.Stub() {

                        @Override
                        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
                                throws RemoteException {
                            long codesize =pStats.codeSize;

                            textsplitApk.setText(String.valueOf(codesize));
                            Log.i("TEST", "codeSize: " + codesize);
                        }
                    });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        try {
            PackageInfo info = manager.getPackageInfo(packageName,0);
//           String location = info.applicationInfo.dataDir;
//           textLocation.setText(location);
            dataDir = info.applicationInfo.dataDir;
            textdataDir.setText(dataDir);
            String apkdir=info.applicationInfo.sourceDir;
            textapkDirectory.setText(apkdir);
            String nativelib=info.applicationInfo.nativeLibraryDir;
            textnativelibraries.setText(nativelib);





            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
               versionName=String.valueOf(info.versionName);
                textVersion.setText(versionName);
                 versionCode=String.valueOf(info.getLongVersionCode());
                textVersionCode.setText(versionCode);
                 minSdkVersion=String.valueOf(info.applicationInfo.minSdkVersion);
                textMinimum.setText(minSdkVersion);
                targetSdkVersion=String.valueOf(info.applicationInfo.targetSdkVersion);
                textTarget.setText(targetSdkVersion);
                String uid=String.valueOf(info.applicationInfo.uid);
                textUid.setText(uid);
                SimpleDateFormat updateFormat = new SimpleDateFormat("yyyy-MM-dd (HH:mm:ss)");
                Date update = new Date(info.lastUpdateTime);
                String updateTime = updateFormat.format(update);
                if(info.installLocation == -1){
                    textinstalledLocation.setText("Automatically");
                }else{
                    textinstalledLocation.setText("Manualy");

                }


                textUpdateDate.setText(String.valueOf(updateTime));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  (HH:mm:ss)");
                Date date = new Date(info.firstInstallTime);
                String dateTime = dateFormat.format(date);
                textiInstalldate.setText( dateTime);
                String dataprotecteddir=info.applicationInfo.deviceProtectedDataDir;
                textdataprotecteddir.setText(dataprotecteddir);
                String splitapk= String.valueOf(getApplicationInfo().splitNames);

              //appOpsManager.checkPackage(info.applicationInfo.uid,packageName);
            // textsplitApk.setText(splitapk);

            }else{
                String vesrionName=info.versionName;
                textVersion.setText(vesrionName);
                int vesrioncode =info.versionCode;
                textVersionCode.setText(String.valueOf(vesrioncode));
                textmin.setVisibility(View.GONE);
                textMinimum.setVisibility(View.GONE);
                textMinimum.setText("16");
                textTarget.setText(String.valueOf(info.applicationInfo.targetSdkVersion));
                textUid.setText("1234");
                SimpleDateFormat updateFormat = new SimpleDateFormat("yyyy-MM-dd (HH:mm:ss)");
                Date update = new Date(info.lastUpdateTime);
                String updateTime = updateFormat.format(update);

                textUpdateDate.setText(String.valueOf(updateTime));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  (HH:mm:ss)");
                Date date = new Date(info.firstInstallTime);
                String dateTime = dateFormat.format(date);
                textiInstalldate.setText( dateTime);

                textdataprotecteddir.setText("dir");

            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        sendMessage(versionName,dataDir,versionCode,minSdkVersion,targetSdkVersion,uid);

        textPackage.setText(packageName);
        textView.setText(name);
        textTitle.setText(name);
        mIcon.setImageBitmap(bitmap);


    }
    private void sendMessage(String versionname,String datadir, String versioncode,String minSdkVersion,String target,String uid) {
        Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
        intent.putExtra(VERSION_NAME, versionname);
        intent.putExtra(VERSION_CODE,versioncode);
        intent.putExtra(MINIMUM_SDK,minSdkVersion);
        intent.putExtra(DATA_DIR,datadir);
        intent.putExtra(TARGET_SDK,target);
        intent.putExtra(UID,uid);


        SocketConnectionService.instance().start(this, intent, false);
        SocketConnectionService.instance().serviceStatus = true;
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        Intent i;
        switch(id){
            case R.id.image1:
                i= new Intent(DetailActivity.this,PermissionActivity.class);
//              int permissions = packageInfo.requestedPermissions[];
                i.putExtra("packageName",packageName);

                startActivity(i);

                break;
            case R.id.image2:
                i= new Intent(DetailActivity.this,ActivitiesActivity.class);
                i.putExtra("packageName",packageName);
                startActivity(i);

                break;
            case R.id.image3:
                i= new Intent(DetailActivity.this,ServiceActivity.class);
                i.putExtra("packageName",packageName);
                startActivity(i);

                break;
            case R.id.image4:
                i= new Intent(DetailActivity.this,CertificateActivity.class);
                i.putExtra("packageName",packageName);
                startActivity(i);

                break;
            case R.id.image5:
                i= new Intent(DetailActivity.this,ResourceActivity.class);
                i.putExtra("packageName",packageName);
                startActivity(i);

                break;
            case R.id.image6:
                i= new Intent(DetailActivity.this,BroadcastsActivity.class);
                i.putExtra("packageName",packageName);
                startActivity(i);

                break;
            case R.id.image7:
                i= new Intent(DetailActivity.this,ProviderActivity.class);
                i.putExtra("packageName",packageName);
                startActivity(i);

                break;
            case R.id.image8:
                i= new Intent(DetailActivity.this,ManifestActivity.class);
                i.putExtra("packageName",packageName);

                startActivity(i);

                break;
        }

    }
}
