package com.scbchallenge.view.mobilehome.interactor;


import com.scbchallenge.models.MobileList;

import java.util.List;

public interface MobileListInteractor {

    void mobileListItems(OnFinishedListener listener);

    interface OnFinishedListener {
        void onFinished(List<MobileList> items);

        void onFailure(String message);
    }
}
