package com.scbchallenge.view.mobilehome.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.scbchallenge.R;
import com.scbchallenge.models.MobileDetail;
import com.scbchallenge.utils.AppConstants;
import com.scbchallenge.view.mobilehome.MobileDetailView;
import com.scbchallenge.view.mobilehome.interactor.MobileDetailInteractorImpl;
import com.scbchallenge.view.mobilehome.presenter.MobileDetailPresenter;
import com.scbchallenge.view.mobilehome.presenter.MobileDetailPresenterImpl;
import com.scbchallenge.view.mobilehome.ui.fragmentinterface.MobileViewInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MobileDetailFragment extends Fragment implements View.OnClickListener, MobileDetailView {

    private TextView mobileName;
    private TextView priceValue;
    private TextView ratingValue;
    private TextView mobileDescription;
    private LinearLayout mobileImageLayout;
    private MobileViewInterface mobileViewInterface;
    private ProgressBar mProgressBar;
    private MobileDetailPresenter mobileDetailPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View detailView = inflater.inflate(R.layout.mobile_detail_view, container, false);
        mobileName = (TextView) detailView.findViewById(R.id.mobile_name);
        mobileDescription = (TextView) detailView.findViewById(R.id.mobile_description);
        priceValue = (TextView) detailView.findViewById(R.id.price_value);
        ratingValue = (TextView) detailView.findViewById(R.id.rating_value);
        mProgressBar = (ProgressBar) detailView.findViewById(R.id.progress);
        mobileImageLayout = (LinearLayout) detailView.findViewById(R.id.image_dynamic);
        mobileDetailPresenter = new MobileDetailPresenterImpl(this, new MobileDetailInteractorImpl());
        ImageView backButton = (ImageView) detailView.findViewById(R.id.back);
        backButton.setOnClickListener(this);
        return detailView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            mobileName.setText(getArguments().getString(AppConstants.MOBILE_NAME));
            mobileDescription.setText(getArguments().getString(AppConstants.MOBILE_DESCRIPTION));
            priceValue.setText(getArguments().getString(AppConstants.PRICE_VALUE));
            ratingValue.setText(getArguments().getString(AppConstants.RATING_VALUE));
            mobileDetailPresenter.onResume(getArguments().getString(AppConstants.IMAGE_URL));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            mobileViewInterface.onBack();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mobileViewInterface = (MobileViewInterface) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mobileViewInterface = (MobileViewInterface) activity;
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setItems(List<MobileDetail> detailItems) {
        for (MobileDetail mobileImage : detailItems) {
            ImageView img = new ImageView(getContext());
            img.setVisibility(View.VISIBLE);
            img.setTag(mobileImage.getId());
            mobileImageLayout.addView(img);
            Picasso.with(getContext()).load(mobileImage.getUrl()).into(img);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
