package com.scbchallenge.networkutils;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.scbchallenge.view.mobilehome.ui.MobileListHome;

public class SCBNetworkError extends BroadcastReceiver {

    /**
     * Receiver to keep listening for network changes. Default
     * lifecycle method of Broadcast receiver
     *
     * @param context Receives the context of the calling activity
     * @param intent  intent from the calling class. Currently unused.
     */
    @Override
    public void onReceive(@NonNull Context context, Intent intent) {
        boolean isNetworkAvailable;
        ConnectivityManager conn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conn.getActiveNetworkInfo();
        // Checks the network connection. Based on the result, decides whether
        // to refresh the display or keep the current display.
        // checks to see if the device has a Wi-Fi connection.
        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            // If device has its Wi-Fi connection, sets isNetworkAvailable
            // to true.
            isNetworkAvailable = true;
        } else if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            isNetworkAvailable = true;
        } else
            // If there is ANY network and there is a network connection
            // (which by process of elimination would be mobile), sets
            // isNetworkAvailable to true.
            // If there is no network connection (mobile or Wi-Fi) Sets
            // isNetworkAvailable to false.
            isNetworkAvailable = networkInfo != null;
        ((MobileListHome) context).onNetworkChange(isNetworkAvailable);
    }
}
