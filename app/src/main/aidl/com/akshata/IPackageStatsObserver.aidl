// IPackageStatsObserver.aidl
package com.akshata;

// Declare any non-default types here with import statements

interface IPackageStatsObserver {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
                void onGetStatsCompleted(in PackageStats pStats, boolean succeeded);

}
