package com.scbchallenge.view.mobilehome.ui.fragmentinterface;


import com.scbchallenge.models.MobileList;

import java.util.List;

public interface FavouriteFragmentInterface {

    void setFavouriteItems(List<MobileList> mobileList);

    void sortBy(String key, int id);
}
