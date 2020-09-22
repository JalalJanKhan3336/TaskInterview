package com.thesoftparrot.storageapp.myapplication;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class MyApp extends Application {

    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
