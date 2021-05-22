package com.akshata.applicationlist.Model;

public class PermissionList {
    private  String name;
   // private  String normal;
    private  String granted;
    public PermissionList(String name,String granted) {
        this.name = name;
       // this.normal=normal;
        this.granted=granted;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//    public String getNormal() {
//        return normal;
//    }
//
//    public void setNormal(String normal) {
//        this.normal = normal;
//    }

    public String getGranted() {
        return granted;
    }

    public void setGranted(String granted) {
        this.granted = granted;
    }

}
