package com.akshata.applicationlist.Model;

import android.graphics.drawable.Drawable;

public class AppList {
    private  String name;
    Drawable icon;
    private  String versionname;
    private  int versioncode;
    private long lastUpdate;
    private long installed;




    public AppList(String name, Drawable icon, String versionname) {
        this.name = name;
        this.icon = icon;
        this.versionname=versionname;
        this.versioncode=versioncode;
        this.lastUpdate=lastUpdate;
        this.installed=installed;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }




    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    public long getInstalled() {
        return installed;
    }

    public void setInstalled(long installed) {
        this.installed = installed;
    }


}

