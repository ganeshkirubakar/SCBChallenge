package com.scbchallenge.view.mobilehome.presenter;


import com.scbchallenge.models.MobileList;
import com.scbchallenge.view.mobilehome.MobileListView;
import com.scbchallenge.view.mobilehome.interactor.MobileListInteractor;

import java.util.List;

public class MobileListPresenterImpl implements MobileListPresenter, MobileListInteractor.OnFinishedListener {

    private MobileListView mobileListView;
    private MobileListInteractor mobileListInteractor;

    public MobileListPresenterImpl(MobileListView mobileListView, MobileListInteractor mobileListInteractor) {
        this.mobileListView = mobileListView;
        this.mobileListInteractor = mobileListInteractor;
    }

    @Override
    public void onResume() {
        if (mobileListView != null) {
            mobileListView.showProgress();
        }
        mobileListInteractor.mobileListItems(this);
    }

    @Override
    public void onDestroy() {
        mobileListView = null;
    }

    @Override
    public void onFinished(List<MobileList> items) {
        if (mobileListView != null) {
            mobileListView.setItems(items);
            mobileListView.hideProgress();
        }
    }

    @Override
    public void onFailure(String message) {
        if (mobileListView != null) {
            mobileListView.showMessage(message);
        }
    }

    public MobileListView getMainView() {
        return mobileListView;
    }
}
