package com.akshata.applicationlist.Activity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.akshata.applicationlist.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ResourceActivity extends AppCompatActivity {
    TextView png;
    Context context;
    String resources;
    ApplicationInfo app;
    PackageManager pm;


    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        png = findViewById(R.id.text3);
        Bundle recdData = getIntent().getExtras();
        resources = recdData.getString("packageName");
//        int res = getResources().getIdentifier("<your pakecgename>:drawable/abc", "png", resources);
//        String image = String.valueOf(res);
//           png.setText(image);

//        try {
//            res = getPackageManager().getResourcesForApplication(resources);
//            int resourceId = res.getIdentifier(resources+":layout/", null, null);
//            if(0 != resourceId) {
//                String s = res.getString(resourceId);
//                png.setText(s);
//
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//
        // String totatpng=String.valueOf(id);


//        XmlResourceParser icon = getPackageManager().getXml(resources,0,applicationInfo);
//           String manifext=String.valueOf(icon);
//
//
//      png.setText(manifext);
        //int images = getResources().getIdentifier("res", "drawable", resources);
//        File file=new File(resources);
//        File[] list = file.listFiles();
//        int count = 0;
//        for (File f: list){
//            String name = f.getName();
//            if (name.endsWith(".jpg") )
//               // count++;
//            System.out.println("170 " + count);
//        }
////
//        String image = getImageFromDrawable(app.loadIcon(pm));
//        ApplicationInfo applicationInfo =getPackageManager().getApplicationInfo(apps.get(position).getPname(),1);
//        int icon= applicationInfo.icon;
//        int id = getResources().getIdentifier("res/drawable/.png", image, resources);
//
//        String pngimage=String.valueOf(id);
//
        int resID=0;
        int imgnum=1;
        ArrayList<Integer> images = new ArrayList<Integer>();

        do {
            resID=getResources().getIdentifier("img_"+imgnum, "png", resources);
            if (resID!=0)
                images.add(resID);
            imgnum++;
        }
        while (resID!=0);

       int imageMaxNumber=imgnum;

       String pngimage=String.valueOf(imageMaxNumber);
        png.setText(pngimage);
    //    ArrayList<Integer> imageListId = new ArrayList<Integer>();

//we iterate through all the items in the drawable folder
//        Field[] drawables = R.drawable.class.getFields();
//        for (Field f : drawables) {
//            //if the drawable name contains "pic" in the filename...
//            if (f.getName().contains("image"))
//                imageListId.add(getResources().getIdentifier(f.getName(), "drawable", resources));
//           int img= imageListId.size();
//           String image = String.valueOf(img);
//           png.setText(image);
//        }

    }

        public String getImageFromDrawable (Drawable drawable){
            String img = null;
            if (drawable != null) {
                if (drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] arr = stream.toByteArray();
                    img = Base64.encodeToString(arr, Base64.URL_SAFE);
                    return img;
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (drawable instanceof AdaptiveIconDrawable) {

                    }
                }

            }
            return null;
        }

    }


