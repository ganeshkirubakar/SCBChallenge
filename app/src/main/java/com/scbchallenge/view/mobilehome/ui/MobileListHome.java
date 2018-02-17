package com.scbchallenge.view.mobilehome.ui;


import android.app.Dialog;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.scbchallenge.R;
import com.scbchallenge.networkutils.SCBNetworkError;
import com.scbchallenge.utils.AppConstants;
import com.scbchallenge.view.mobilehome.ui.fragment.MobileListFragment;
import com.scbchallenge.view.mobilehome.ui.fragmentinterface.MobileViewInterface;

public class MobileListHome extends AppCompatActivity implements MobileViewInterface {

    private Dialog networkDialog;
    private SCBNetworkError networkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetworkListener();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 1) {
            getSupportFragmentManager().popBackStack();
        } else if (count == 1) {
            finish();
        }
    }

    private void loadFragmentContent(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.replace(R.id.fragment_list_item, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void loadFragment(Fragment fragment) {
        loadFragmentContent(fragment);
    }

    @Override
    public void onBack() {
        onBackPressed();
    }

    public void onNetworkChange(boolean isNetworkAvailable) {
        if (!isNetworkAvailable) {
            showNetworkDialog();
        } else {
            closeNetworkDialog();
            MobileListFragment mobileListFragment = new MobileListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(AppConstants.ON_PAGE_SELECTED, 0);
            mobileListFragment.setArguments(bundle);
            loadFragmentContent(mobileListFragment);
        }
    }

    private void showNetworkDialog() {
        if (networkDialog == null) {
            networkDialog = new Dialog(this);
            networkDialog.setContentView(R.layout.no_network_dialog);
            networkDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            networkDialog.setCancelable(false);
            networkDialog.show();
        }
    }

    private void closeNetworkDialog() {
        if (networkDialog != null) {
            networkDialog.dismiss();
            networkDialog = null;
        }
    }

    /**
     * Register the Listener to for Network Change events
     */
    private void registerNetworkListener() {
        // Registers BroadcastReceiver to track network connection changes.
        IntentFilter filter = new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION);
        networkReceiver = new SCBNetworkError();
        this.registerReceiver(networkReceiver, filter);
    }

    /**
     * Un-Register the Network listener
     */
    private void unRegisterNetworkListener() {
        if (networkReceiver != null) {
            this.unregisterReceiver(networkReceiver);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterNetworkListener();
    }
}
