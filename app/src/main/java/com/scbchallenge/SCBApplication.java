package com.scbchallenge;


import android.app.Application;

import com.scbchallenge.utils.AHMRealmUtil;

public class SCBApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AHMRealmUtil.getInstance(this);
    }
}
