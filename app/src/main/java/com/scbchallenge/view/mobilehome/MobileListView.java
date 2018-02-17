package com.scbchallenge.view.mobilehome;


import com.scbchallenge.models.MobileList;

import java.util.List;

public interface MobileListView {

    void showProgress();

    void hideProgress();

    void setItems(List<MobileList> items);

    void showMessage(String message);
}
