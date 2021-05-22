package com.akshata.applicationlist.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.akshata.applicationlist.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ManifestActivity extends AppCompatActivity {
    TextView manifest;
    PackageManager pm;
    Manifest manifestnew;
    PackageInfo p ;
    XmlResourceParser xmlParser;
    private Object Manifest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifest);
        manifest = (TextView) findViewById(R.id.manifest);
         pm = getPackageManager();
        final Context context = getApplicationContext();
        Bundle recdData = getIntent().getExtras();
        String packageName = recdData.getString("packageName");


        String _permissions = "";

        try
        {
            final AssetManager _am = context.createPackageContext(packageName, 0).getAssets();
           // InputStream xmlStream = assetManager.open("xmlfile.xml");
            final XmlResourceParser _xmlParser = _am.openXmlResourceParser(0, "AndroidManifest.xml");
            int _eventType = _xmlParser.getStyleAttribute();

            while (_eventType != XmlPullParser.END_DOCUMENT)
            {
//                if ((_eventType == XmlPullParser.START_TAG))
//
//                {

                   for (byte i = 0; i < _xmlParser.getAttributeCount();  i++)
                   {
//                        if (_xmlParser.getAttributeName(i).equals("name"))
//                        {


                        _permissions += "<"+_xmlParser.getName() +" : " +  _xmlParser.getAttributeValue(i)+ "/>"+"\n";

                      //  }
                 //   }
               }
                _eventType = _xmlParser.nextToken();
            }
            _xmlParser.close(); // Pervents memory leak.
        }
        catch (final XmlPullParserException exception)
        {
            exception.printStackTrace();
        }
        catch (final PackageManager.NameNotFoundException exception)
        {
            exception.printStackTrace();
        }
        catch (final IOException exception)
        {
            exception.printStackTrace();
        }

       manifest.setText(_permissions);




    }



}
