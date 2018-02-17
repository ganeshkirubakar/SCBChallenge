package com.scbchallenge.view.mobilehome.presenter;


import com.scbchallenge.models.MobileDetail;
import com.scbchallenge.view.mobilehome.MobileDetailView;
import com.scbchallenge.view.mobilehome.interactor.MobileDetailInteractor;

import java.util.List;

public class MobileDetailPresenterImpl implements MobileDetailPresenter, MobileDetailInteractor.OnFinishedListener {

    private MobileDetailView mobileDetailView;
    private MobileDetailInteractor mobileDetailInteractor;

    public MobileDetailPresenterImpl(MobileDetailView mobileDetail, MobileDetailInteractor mobileDetailInteractor) {
        this.mobileDetailView = mobileDetail;
        this.mobileDetailInteractor = mobileDetailInteractor;
    }

    @Override
    public void onResume(String mobileId) {
        if (mobileDetailView != null) {
            mobileDetailView.showProgress();
        }
        mobileDetailInteractor.mobileDetailItems(this, Integer.valueOf(mobileId));
    }

    @Override
    public void onDestroy() {
        mobileDetailView = null;
    }

    @Override
    public void onFinished(List<MobileDetail> items) {
        if (mobileDetailView != null) {
            mobileDetailView.setItems(items);
            mobileDetailView.hideProgress();
        }
    }

    @Override
    public void onFailure(String message) {
        if (mobileDetailView != null) {
            mobileDetailView.showMessage(message);
        }
    }

    public MobileDetailView getMainView() {
        return mobileDetailView;
    }
}
