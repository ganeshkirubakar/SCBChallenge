package com.scbchallenge.view.mobilehome;


import com.scbchallenge.models.MobileDetail;

import java.util.List;

public interface MobileDetailView {

    void showProgress();

    void hideProgress();

    void setItems(List<MobileDetail> detailItems);

    void showMessage(String message);
}
