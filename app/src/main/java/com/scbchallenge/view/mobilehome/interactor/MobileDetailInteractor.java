package com.scbchallenge.view.mobilehome.interactor;


import com.scbchallenge.models.MobileDetail;

import java.util.List;

public interface MobileDetailInteractor {

    void mobileDetailItems(OnFinishedListener listener, int mobileId);

    interface OnFinishedListener {
        void onFinished(List<MobileDetail> items);

        void onFailure(String message);
    }

}
