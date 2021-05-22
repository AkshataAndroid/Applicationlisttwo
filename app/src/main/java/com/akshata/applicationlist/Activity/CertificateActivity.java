package com.akshata.applicationlist.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.akshata.applicationlist.Database.DeviceDB;
import com.akshata.applicationlist.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class CertificateActivity extends AppCompatActivity {
    TextView mOrganizationNamePublisher,mSubjectCity,mSubjectCountry,mSubjectState,mPublisherCountry,mPublisherCity,mPublisherState,mOrganizationNameSubject,mOrganizationPublisher,mOrganizationSubject,textValidUntil,textCreated,textSerialnumber,textAlgorithem,textMd5,textsha1,textsha256;
    PackageManager pm;
    String packageName;
    String SHAKEY1;
    String MD5;
    String SHAKEY256;
    String createdDate,validDate,algorithem,serialNumber,organisation,organisationName,cityName,stateName,CountryName;
    PackageInfo packs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificate);
        mOrganizationPublisher=findViewById(R.id.textt5);
        mOrganizationSubject=findViewById(R.id.text35);
        textCreated=findViewById(R.id.text5);
        textValidUntil=findViewById(R.id.text7);
        textSerialnumber =findViewById(R.id.text9);
        textAlgorithem=findViewById(R.id.text3);
        textMd5=findViewById(R.id.text12);
        textsha1=findViewById(R.id.text14);
        textsha256=findViewById(R.id.text16);
        mOrganizationNamePublisher=findViewById(R.id.textt3);
        mOrganizationNameSubject=findViewById(R.id.text33);
        mSubjectCity=findViewById(R.id.text39);
        mSubjectCountry=findViewById(R.id.text37);
        mSubjectState=findViewById(R.id.textapp32);
        mPublisherCity=findViewById(R.id.textt9);
        mPublisherCountry=findViewById(R.id.textt7);
        mPublisherState=findViewById(R.id.textapp2);



        pm = getPackageManager();
        final Context contextnew=getApplicationContext();
        Bundle recdData = getIntent().getExtras();
       packageName = recdData.getString("packageName");

//        String name = null;
//        String org=null;
//        String state=null;
//        String city=null;
//        String country=null;
        try {
            Signature signatures[] = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
            for (Signature signature : signatures) {
                final byte[] rawCert = signature.toByteArray();
                InputStream certStream = new ByteArrayInputStream(rawCert);


                try {
                    CertificateFactory certFactory = CertificateFactory.getInstance("X509");

                    X509Certificate x509Cert = (X509Certificate) certFactory.generateCertificate(certStream);
                    String issuerDn = x509Cert.getIssuerDN().getName();



                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  (HH:mm:ss)");
                    java.sql.Date createddate = new Date(x509Cert.getNotBefore().getTime());
                    String dateTime = dateFormat.format(createddate);
                    createdDate= String.valueOf(dateTime);

                    java.sql.Date validityDate = new Date(x509Cert.getNotAfter().getTime());
                    String validitydateTime = dateFormat.format(validityDate);
                    validDate= String.valueOf(validitydateTime);

                     serialNumber=String.valueOf(x509Cert.getSerialNumber());
                   algorithem=String.valueOf(x509Cert.getSigAlgName());

                    textCreated.setText(createdDate);
                    textValidUntil.setText(validDate);
                    textSerialnumber.setText(serialNumber);
                    textAlgorithem.setText(algorithem);
                    String indexOfOrg = (issuerDn);
                    String[] arrSplit_2 = indexOfOrg.split(",");
                  organisationName=arrSplit_2[1];
                    organisation=arrSplit_2[0];
                    String Name=arrSplit_2[2];
                     cityName=arrSplit_2[3];
                    stateName=arrSplit_2[4];
                     CountryName=arrSplit_2[5];
                    if(arrSplit_2==null && indexOfOrg==null && issuerDn!=null){

                        mOrganizationNamePublisher.setText(organisationName);
                        mOrganizationNameSubject.setText(organisationName);
                        mOrganizationPublisher.setText(organisation);
                        mOrganizationSubject.setText(organisation);
                        mSubjectCity.setText(cityName);
                        mPublisherCity.setText(cityName);
                        mPublisherState.setText(stateName);
                        mSubjectState.setText(stateName);
                        mSubjectCountry.setText(CountryName);
                        mPublisherCountry.setText(CountryName);

                    }else {
                         String name=arrSplit_2[2];

                        String[] arrSplitname = name.split("=");
                        String orgName=arrSplitname[1];
                        mOrganizationNamePublisher.setText(orgName);
                        mOrganizationNameSubject.setText(orgName);
                       String org=arrSplit_2[0];

                        String[] arrSplitorg=org.split("=");
                        String Org=arrSplitorg[1];
                        mOrganizationPublisher.setText(Org);
                        mOrganizationSubject.setText(Org);
                       String city=arrSplit_2[3];
                        String[] arrSplitcity=city.split("=");
                        String orgCity=arrSplitcity[1];
                        mSubjectCity.setText(orgCity);
                        mPublisherCity.setText(orgCity);
                       String state=arrSplit_2[4];
                        String[] arrSplitstate=state.split("=");
                        String orgState=arrSplitstate[1];
                        mPublisherState.setText(orgState);
                        mSubjectState.setText(orgState);
                       String country=arrSplit_2[5];
                        String[] arrSplitcountry=country.split("=");
                        String orgCountry = arrSplitcountry[1];
                        mSubjectCountry.setText(orgCountry);
                        mPublisherCountry.setText(orgCountry);

                    }




                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                MessageDigest md2 = MessageDigest.getInstance("MD5");
                MessageDigest md3 = MessageDigest.getInstance("SHA-256");

                md.update(signature.toByteArray());
                md2.update(signature.toByteArray());
                md3.update(signature.toByteArray());
            SHAKEY1= Base64.encodeToString(md.digest(), Base64.DEFAULT);
               MD5= Base64.encodeToString(md2.digest(), Base64.DEFAULT);
             SHAKEY256= Base64.encodeToString(md3.digest(), Base64.DEFAULT);

                textsha1.setText(SHAKEY1);
                textMd5.setText(MD5);
                textsha256.setText(SHAKEY256);

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//      String sha1=  getKeyHash("SHA1");
//        String MD5=  getKeyHash("MD5");
//        String sha256 =  getKeyHash("SHA256");
//      textsha1.setText(sha1);
//      textMd5.setText(MD5);
//      textsha256.setText(sha256);

        ContentValues contentValues = new ContentValues();
        // get  & set with contentvalues
        contentValues.put(DeviceDB.CREATED_ON, createdDate);
        contentValues.put(DeviceDB.VALID_TILL,validDate);
        contentValues.put(DeviceDB.ALGORITHEM,algorithem);
        contentValues.put(DeviceDB.SERIALNUMBER,serialNumber);
        contentValues.put(DeviceDB.MDFIVE,MD5);
        contentValues.put(DeviceDB.SHAONE,SHAKEY1);
        contentValues.put(DeviceDB.SHATWO,SHAKEY256);
        contentValues.put(DeviceDB.PUBLISHER_ORGANIsATION,organisation);
        contentValues.put(DeviceDB.PUBLISHER_ORGANIsATION,organisationName);
        contentValues.put(DeviceDB.PUBLISHER_CITY,cityName);
        contentValues.put(DeviceDB.PUBLISHER_STATE,stateName);
        contentValues.put(DeviceDB.PUBLISHER_COUNTRY,CountryName);
        contentValues.put(DeviceDB.PACKAGE_N,packageName);

        DeviceDB lb = new DeviceDB(this);
        long row = lb.addCertificateDetailsListItem( createdDate,validDate,algorithem,serialNumber,MD5,SHAKEY1,SHAKEY256,organisation,organisationName,cityName,stateName,CountryName,packageName);
        if (row > 0) {
            Toast.makeText(this, "Your Location Inserted Successfully....",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something Wrong...", Toast.LENGTH_SHORT).show();
        }


    }
    private String getKeyHash(String hashStretagy) {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA1");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("KeyHash  -->>>>>>>>>>>>" , something);

                // Notification.registerGCM(this);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found" , e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm" , e.toString());
        } catch (Exception e) {
            Log.e("exception" , e.toString());
        }
        return hashStretagy;
    }

}
