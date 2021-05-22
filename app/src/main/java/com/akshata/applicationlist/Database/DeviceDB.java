package com.akshata.applicationlist.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DeviceDB extends SQLiteOpenHelper {
    private static String DBNAME = "DeviceManegementDatabase.db";
    private static int VERSION = 6;
    public static final String ROW_ID = "_id";
    public static final String VERSIONNAME = "package";
    public static final String APP = "appName";
    public static final String VNAME = "name";
    public static final String VCODE = "code";
    public static final String Activities = "minimumSdk";
    public static final String TARGET = "targetSdk";
    public static final String UID = "uid";
    public static final String CREATED_AT = "Updated_On";
    public static final String INSTALLDATE = "INSTALLED_DATE";
    public static final String LASTUPATEDATE = "Last_Updated_DATE";
    public static final String APP_ID = "app_id";
    public static final String DATA_DIR = "data_dir";


    //Activity
    public static final String TABLE_ACTIVITY_LIST = "activity_list";
    public static final String TABLE_CHILD_LIST_ID = "id";
    public static final String  Activity_PACKAGE_NAME = "activity_package_name";
    public static final String ACTIVITY_NAME= "activity";
    public static final String CREATED_AT_ACTIVITY = "Updated_On";

    //services
    public static final String TABLE_SERVICE_LIST = "service_list";
    public static final String TABLE_SERVICE_LIST_ID = "service_id";
    public static final String  SERVICE_PACKAGE_NAME = "service_package_name";
    public static final String SERVICE_NAME= "service";
    public static final String CREATED_AT_SERVICES = "Updated_On";


    //permissions
    public static final String TABLE_PERMISSIONS_LIST = "permission_list";
    public static final String TABLE_PERMISSION_LIST_ID = "permission_id";
    public static final String  PERMISSION_PACKAGE_NAME = "permission_package_name";
    public static final String PERMISSIOJ_NAME= "permission";
    public static final String CREATED_AT_PERMISSION = "Updated_On";

    //providers
    public static final String TABLE_PROVODERS_LIST = "providers_list";
    public static final String TABLE_PROVIDERS_LIST_ID = "providers_id";
    public static final String  PROVIDERS_PACKAGE_NAME = "providers_package_name";
    public static final String PROVIDERS_NAME= "providers_name";
    public static final String CREATED_AT_PROVIDERS= "Updated_On";

    //broadcasts
    public static final String TABLE_BROADCAST_LIST = "broadcasters_list";
    public static final String TABLE_BROADCAST_LIST_ID = "broadcasts_id";
    public static final String  BROADCAST_PACKAGE_NAME = "broadcast_package_name";
    public static final String BROADCAST_NAME= "broadcasts";
    public static final String CREATED_AT_BROADCAST = "Updated_On";


    //devicedetails
    public static final String TABLE_DEVICEDETAILS_LIST = "details_list";
    public static final String TABLE_DETAILS_LIST_ID = "details_id";
    public static final String  VERSION_NUMBER = "version";
    public static final String SIMCOUNT = "total_sim";
    public static final String  NUMBEROFINSTALLEDAPPS = "total_app";
    public static final String IMEINUMBERONE = "imeione";
    public static final String  IMEINUMBERTWO = "imeitwo";
    public static final String BATTERYSTATUS = "battery";
    public static final String  TOTAL_SPACE = "total_storage";
    public static final String CREATED_AT_DEVICE= "Updated_On";
    public static final String INTERNAL_PATH= "internal_path";
    public static final String SECURITY_UPDATE= "security_upate";


    String resource,resourceService,resourcePermission,resoureceactivity,resourcebroadcater,resourcecertificate;

    //certificates
    public static final String TABLE_CERTIFICATES_LIST = "certificates_list";
    public static final String TABLE_CERTIFICATE_LIST_ID = "certificate_id";
    public static final String  CREATED_ON = "created";
    public static final String VALID_TILL = "valid_till";
    public static final String ALGORITHEM = "algorithem";
    public static final String  SERIALNUMBER = "serial_number";
    public static final String MDFIVE = "MD5";
    public static final String  SHAONE = "SHA1";
    public static final String SHATWO = "sha256";
    public static final String  PUBLISHER_ORGANIsATION = "organisation";
    public static final String PUBLISHERNAME= "publishername";
    public static final String  PUBLISHER_COUNTRY= "country";
    public static final String PUBLISHER_CITY= "city";
    public static final String  PUBLISHER_STATE = "state";
    public static final String CREATED_AT_CERTIFICATE= "Updated_On";
    public static final String PACKAGE_N= "package";


    //battaryetails
    public static final String TABLE_BATTERY_LIST = "Battery_list";
    public static final String TABLE_BATTERY_LIST_ID = "certificate_id";
    public static final String  BATTERYLEVEL = "battery_level";
    public static final String DEVICE_STATUS = "device_status";
    public static final String CREATED_AT_BATTERY= "Updated_On";


    public static final String APP_ID_CERTIFICATE= "AppId";
    public static final String APP_ID_SERVICE= "ser";
    public static final String APP_ID_PERMISSION= "perm";
    public static final String APP_ID_BROADCAST= "brod";
    public static final String APP_ID_PROVIDER= "prov";




    long row;
    private SQLiteDatabase mDB;
    private static final String DATABASE_TABLE = "device";

    public DeviceDB( Context context) {
        super(context, DBNAME, null, VERSION);
        this.mDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + DATABASE_TABLE + " ( " +
                ROW_ID + " integer primary key autoincrement not null, " +
                CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                VERSIONNAME + " text , " +
                VNAME + " integer , " +
                VCODE + " integer , " +
                Activities + " integer , " +
                TARGET + " integer , " +
                UID + " integer , " +
                INSTALLDATE + " datetime , " +
                LASTUPATEDATE + " datetime , " +
                DATA_DIR + " text , " +
                APP + " text " +
                " ) ";

        String CREATE_TABLE_CHILD_LIST = "CREATE TABLE " + TABLE_ACTIVITY_LIST + "("
                + TABLE_CHILD_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + APP_ID + " INTEGER,"
                + CREATED_AT_ACTIVITY + "DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + Activity_PACKAGE_NAME + " INTEGER,"
                + ACTIVITY_NAME + " TEXT,"
                + " FOREIGN KEY ("+APP_ID+") REFERENCES "+DATABASE_TABLE+"("+ROW_ID+"));";

        String CREATE_TABLE_SERVICE_LIST = "CREATE TABLE " + TABLE_SERVICE_LIST + "("
                + TABLE_SERVICE_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + APP_ID_SERVICE + " INTEGER,"
                + CREATED_AT_SERVICES + "DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + SERVICE_PACKAGE_NAME + " TEXT,"
                + SERVICE_NAME + " TEXT,"
                + " FOREIGN KEY ("+APP_ID_SERVICE+") REFERENCES "+DATABASE_TABLE+"("+ROW_ID+"));";


        String CREATE_TABLE_PERMISSION_LIST = "CREATE TABLE " + TABLE_PERMISSIONS_LIST + "("
                + TABLE_PERMISSION_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + APP_ID_PERMISSION + " INTEGER,"
                + CREATED_AT_PERMISSION + "DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + PERMISSION_PACKAGE_NAME + " TEXT,"
                + PERMISSIOJ_NAME + " TEXT,"
                + " FOREIGN KEY ("+APP_ID_PERMISSION+") REFERENCES "+DATABASE_TABLE+"("+ROW_ID+"));";


        String CREATE_TABLE_BROADCAST_LIST = "CREATE TABLE " + TABLE_BROADCAST_LIST + "("
                + TABLE_BROADCAST_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + APP_ID_BROADCAST + " INTEGER,"
                + CREATED_AT_BROADCAST + "DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + BROADCAST_PACKAGE_NAME + " TEXT,"
                + BROADCAST_NAME + " TEXT,"
                + " FOREIGN KEY ("+APP_ID_BROADCAST+") REFERENCES "+DATABASE_TABLE+"("+ROW_ID+"));";


        String CREATE_TABLE_PROVIDERS_LIST = "CREATE TABLE " + TABLE_PROVODERS_LIST + "("
                + TABLE_PROVIDERS_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + APP_ID_PROVIDER + " INTEGER,"
                + CREATED_AT_PROVIDERS + "DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + PROVIDERS_PACKAGE_NAME + " TEXT,"
                + PROVIDERS_NAME + " TEXT,"
                + " FOREIGN KEY ("+APP_ID_PROVIDER+") REFERENCES "+DATABASE_TABLE+"("+ROW_ID+"));";


        String CREATE_TABLE_DEVICEETAILS_LIST = "CREATE TABLE " + TABLE_DEVICEDETAILS_LIST + "(" +
                TABLE_DETAILS_LIST_ID + " integer primary key autoincrement , "
                + CREATED_AT_DEVICE + "DATETIME DEFAULT CURRENT_TIMESTAMP,"+
                VERSION_NUMBER + " integer , " +
                SIMCOUNT + " integer , " +
                IMEINUMBERONE + " integer , " +
                IMEINUMBERTWO + " integer , " +
                NUMBEROFINSTALLEDAPPS + " text , " +
                SECURITY_UPDATE + " datetime , " +
                INTERNAL_PATH + " text , " +
                TOTAL_SPACE + " float " +
                " ) ";

        String CREATE_TABLE_CERTIFICATE_LIST = "CREATE TABLE " + TABLE_CERTIFICATES_LIST + "(" +
                TABLE_CERTIFICATE_LIST_ID + " integer primary key autoincrement , "
                + APP_ID_CERTIFICATE + " INTEGER,"
                + CREATED_AT_CERTIFICATE + "DATETIME DEFAULT CURRENT_TIMESTAMP,"+
                CREATED_ON + " date , " +
                VALID_TILL + " date , " +
                ALGORITHEM + " text , " +
                SERIALNUMBER + " text , " +
                MDFIVE + " text , " +
                SHAONE + " text , " +
                SHATWO + " text , " +
                PACKAGE_N + " text , " +
                PUBLISHERNAME + " text , " +
                PUBLISHER_ORGANIsATION + " text , " +
                PUBLISHER_CITY + " text , " +
                PUBLISHER_STATE + " text , " +
                PUBLISHER_COUNTRY + " text ,"
                +
                " FOREIGN KEY ("+APP_ID_CERTIFICATE+") REFERENCES "+DATABASE_TABLE+"("+ROW_ID+"));";


        String CREATE_TABLE_BATTERY_LIST = "CREATE TABLE " + TABLE_BATTERY_LIST + "("
                + TABLE_BATTERY_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CREATED_AT_BATTERY + "DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + BATTERYLEVEL + " INTEGER" + ")";


        db.execSQL(sql);
        db.execSQL(CREATE_TABLE_CHILD_LIST);
        db.execSQL(CREATE_TABLE_SERVICE_LIST);
        db.execSQL(CREATE_TABLE_PERMISSION_LIST);
        db.execSQL(CREATE_TABLE_DEVICEETAILS_LIST);
        db.execSQL(CREATE_TABLE_BROADCAST_LIST);
        db.execSQL(CREATE_TABLE_PROVIDERS_LIST);
        db.execSQL(CREATE_TABLE_CERTIFICATE_LIST);
        db.execSQL(CREATE_TABLE_BATTERY_LIST);

    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public long insert( String ver,String app,String vname,String vcode,String activies,String tar,String uid,String installdate,String updatedate,String datadir){

        ContentValues contentValue = new ContentValues();
        contentValue.put(VERSIONNAME, ver);
        contentValue.put(APP, app);
        contentValue.put(VNAME, vname);
        contentValue.put(VCODE, vcode);
        contentValue.put(Activities, activies);
        contentValue.put(TARGET, tar);
        contentValue.put(UID, uid);
        // contentValue.put(CREATED_AT, getDateTime());
        contentValue.put(INSTALLDATE, installdate);
        contentValue.put(LASTUPATEDATE, updatedate);
        contentValue.put(DATA_DIR, datadir);


        row= mDB.insert(DATABASE_TABLE, null, contentValue);
        return row;

    }
    public long addChildListItem(String activity,String packagename) {

        ContentValues values = new ContentValues();
        values.put(ACTIVITY_NAME, activity);
        values.put(Activity_PACKAGE_NAME, packagename);
        Cursor res=  mDB.rawQuery("select * from "+DATABASE_TABLE+" where Package='"+packagename+"'", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            resoureceactivity=  res.getString(res.getColumnIndex("_id"));
            res.moveToNext();
        }
        values.put(APP_ID, resoureceactivity);
        // values.put(CREATED_AT_ACTIVITY, getDateTime());
        long childrow =mDB.insert(TABLE_ACTIVITY_LIST, null, values);
//        mDB.close();
        return childrow;
    }
    public long addServiceListItem(String services,String packagename) {

        ContentValues values = new ContentValues();
        values.put(SERVICE_NAME, services);
        values.put(SERVICE_PACKAGE_NAME, packagename);
        Cursor res=  mDB.rawQuery("select * from "+DATABASE_TABLE+" where Package='"+packagename+"'", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            resourceService=  res.getString(res.getColumnIndex("_id"));
            res.moveToNext();
        }
        values.put(APP_ID_SERVICE, resourceService);
        //  values.put(CREATED_AT_SERVICES, getDateTime());
        long childrow =mDB.insert(TABLE_SERVICE_LIST, null, values);
//        mDB.close();
        return childrow;
    }

    public long addProvidersListItem(String providers,String packagename) {

        ContentValues values = new ContentValues();
        values.put(PROVIDERS_NAME, providers);
        values.put(PROVIDERS_PACKAGE_NAME, packagename);

          //values.put(CREATED_AT_SERVICES, getDateTime());
        Cursor res=  mDB.rawQuery("select * from "+DATABASE_TABLE+" where Package='"+packagename+"'", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
           resource=  res.getString(res.getColumnIndex("_id"));
            res.moveToNext();
        }
        values.put(APP_ID_PROVIDER, resource);
        long childrow =mDB.insert(TABLE_PROVODERS_LIST, null, values);
//        mDB.close();
        return childrow;
    }

    public long addBroadcastersListItem(String broadcasts,String packagename) {

        ContentValues values = new ContentValues();
        values.put(BROADCAST_NAME, broadcasts);
        values.put(BROADCAST_PACKAGE_NAME, packagename);
        Cursor res=  mDB.rawQuery("select * from "+DATABASE_TABLE+" where Package='"+packagename+"'", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            resourcebroadcater=  res.getString(res.getColumnIndex("_id"));
            res.moveToNext();
        }
        values.put(APP_ID_BROADCAST, resourcebroadcater);
        //  values.put(CREATED_AT_SERVICES, getDateTime());
        long childrow =mDB.insert(TABLE_BROADCAST_LIST, null, values);
//        mDB.close();
        return childrow;
    }

    public long addBatteryListItem(String battrystatus) {

        ContentValues values = new ContentValues();
        values.put(BATTERYLEVEL, battrystatus);
        // values.put(DEVICE_STATUS, devicestatus);
        //  values.put(CREATED_AT_SERVICES, getDateTime());
        long childrow =mDB.insert(TABLE_BATTERY_LIST, null, values);
//        mDB.close();
        return childrow;
    }
    public long addPermissionListItem(String permission,String packagename) {

        ContentValues values = new ContentValues();
        values.put(PERMISSIOJ_NAME, permission);
        values.put(PERMISSION_PACKAGE_NAME, packagename);
        Cursor res=  mDB.rawQuery("select * from "+DATABASE_TABLE+" where Package='"+packagename+"'", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            resourcePermission=  res.getString(res.getColumnIndex("_id"));
            res.moveToNext();
        }
        values.put(APP_ID_PERMISSION, resourcePermission);
        // values.put(CREATED_AT_PERMISSION, getDateTime());
        long childrow =mDB.insert(TABLE_PERMISSIONS_LIST, null, values);
//        mDB.close();
        return childrow;
    }

    public long addDeviceDetailsListItem(String version,String numberofsim,String totalspace,String totalapps,String imeione,String imeitwo,String securityupdate,String internalpath) {

        ContentValues values = new ContentValues();
        values.put(VERSION_NUMBER, version);
        values.put(SIMCOUNT, numberofsim);
        values.put(TOTAL_SPACE, totalspace);
        values.put(NUMBEROFINSTALLEDAPPS, totalapps);
        values.put(IMEINUMBERONE, imeione);
        values.put(IMEINUMBERTWO, imeitwo);
        values.put(SECURITY_UPDATE, securityupdate);
        values.put(INTERNAL_PATH, internalpath);


        // values.put(CREATED_AT_DEVICE, getDateTime());
        long childrow =mDB.insert(TABLE_DEVICEDETAILS_LIST, null, values);
//        mDB.close();
        return childrow;
    }

    public long addCertificateDetailsListItem(String createdon,String validtill,String algorithem,String seralnumber,String mdfive,String shaone,String shatwo,String pname,String organisation,String city,String state,String country,String packagename) {

        ContentValues values = new ContentValues();
        values.put(CREATED_ON, createdon);
        values.put(VALID_TILL, validtill);
        values.put(ALGORITHEM, algorithem);
        values.put(SERIALNUMBER, seralnumber);

        values.put(MDFIVE, mdfive);
        values.put(SHAONE, shaone);
        values.put(SHATWO, shatwo);
        values.put(PUBLISHERNAME, pname);
        values.put(PUBLISHER_ORGANIsATION, organisation);
        values.put(PUBLISHER_CITY, city);
        values.put(PUBLISHER_STATE, state);
        values.put(PUBLISHER_COUNTRY, country);
        values.put(PACKAGE_N, packagename);
        Cursor res=  mDB.rawQuery("select * from "+DATABASE_TABLE+" where Package='"+packagename+"'", null);
        res.moveToFirst();
        while(res.isAfterLast() == false) {
            resourcecertificate=  res.getString(res.getColumnIndex("_id"));
            res.moveToNext();
        }
       values.put(APP_ID_CERTIFICATE, resourcecertificate);
        // values.put(CREATED_AT_DEVICE, getDateTime());
        long childrow =mDB.insert(TABLE_CERTIFICATES_LIST, null, values);
//        mDB.close();
        return childrow;
    }



    public int del(){
        int cnt = mDB.delete(DATABASE_TABLE, null , null);
        return cnt;
    }

    public Cursor getAppDetails(){
        return mDB.query(DATABASE_TABLE, new String[] { ROW_ID, VERSIONNAME , APP,ACTIVITY_NAME} , null, null, null, null, null);
    }
//    public List<ParentListItem> getAllParentListItem() {
//        List<ParentListItem> result = new ArrayList<>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(DATABASE_TABLE, new String[] { ROW_ID, TABLE_ACTIVITY_NAME,VERSIONNAME } , null, null, null, null, null);
//
////        while (cursor.moveToNext()) {
////            ParentListItem item = new ParentListItem(DATABASE_TABLE);
////            item.getName(getStringByColumName(cursor, TABLE_CHILD_LIST_ID));
////            item.getName(getStringByColumName(cursor, VERSIONNAME));
////            result.add(item);
////        }
//        cursor.close();
//        db.close();
//        return result;
//    }



//    public int getIntByColumName(Cursor cursor, String tableColumn) {
//        return cursor.getInt(cursor.getColumnIndex(tableColumn));
//    }
//
//
//    public double getDoubleByColumName(Cursor cursor, String tableColumn) {
//        return cursor.getDouble(cursor.getColumnIndex(tableColumn));
//    }
//
//
//    public String getStringByColumName(Cursor cursor, String tableColumn) {
//        return cursor.getString(cursor.getColumnIndex(tableColumn));
//    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}

