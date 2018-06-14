package com.raju.pagination.utils;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ExT-Emp-005 on 16-03-2017.
 */

public class Contant extends MultiDexApplication {

    public static final String TAG = Contant.class.getSimpleName();
    public static Context context;
    public static Contant rest;
    private RequestQueue mRequestQueue;


    @Override
    public void onCreate() {
        super.onCreate();
        rest = this;
        context = getApplicationContext();
    }

    public static synchronized Contant getInstance() {
        return rest;
    }

    public static Context getAppContext() {
        return Contant.context;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}