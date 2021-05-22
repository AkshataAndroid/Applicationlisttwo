package com.akshata.applicationlist.Model;

public class ActivityList {
    private  String activityname;
    private  String  packagename;

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public ActivityList(String activityname, String packagename) {
        this.activityname = activityname;
        this.packagename = packagename;

    }
}
