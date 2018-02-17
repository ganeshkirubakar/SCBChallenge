package com.scbchallenge.utils;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.scbchallenge.R;
import com.scbchallenge.view.mobilehome.ui.fragmentinterface.DialogInterface;

public class CustomDialog extends AppCompatDialog implements View.OnClickListener {

    private Activity activity;
    private DialogInterface dialogInterface;

    public CustomDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        TextView mLowToHigh = (TextView) findViewById(R.id.low_high);
        TextView mHighToLow = (TextView) findViewById(R.id.high_low);
        TextView mRating = (TextView) findViewById(R.id.rating);
        TextView mCancel = (TextView) findViewById(R.id.cancel);
        mLowToHigh.setOnClickListener(this);
        mHighToLow.setOnClickListener(this);
        mRating.setOnClickListener(this);
        mCancel.setOnClickListener(this);
    }

    public void setDialogInterface(DialogInterface dialogInterface) {
        super.onAttachedToWindow();
        this.dialogInterface = dialogInterface;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.low_high) {
            this.dialogInterface.sortBy(AppConstants.LOW_TO_HIGH);
            dismiss();
        } else if (v.getId() == R.id.high_low) {
            this.dialogInterface.sortBy(AppConstants.HIGH_TO_LOW);
            dismiss();
        } else if (v.getId() == R.id.rating) {
            this.dialogInterface.sortBy(AppConstants.RATING);
            dismiss();
        } else if (v.getId() == R.id.cancel) {
            dismiss();
        }
    }
}
