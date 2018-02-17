package com.scbchallenge.view.mobilehome.interactor;


import com.scbchallenge.models.MobileList;
import com.scbchallenge.networkutils.NetworkAdapter;
import com.scbchallenge.utils.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileListInteractorImpl implements MobileListInteractor {

    @Override
    public void mobileListItems(final OnFinishedListener listener) {
        NetworkAdapter.getInstance().getMobileList().enqueue(new Callback<List<MobileList>>() {
            @Override
            public void onResponse(Call<List<MobileList>> call, Response<List<MobileList>> response) {
                listener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<List<MobileList>> call, Throwable t) {
                listener.onFailure(AppConstants.ERROR_MESSAGE);
            }
        });
    }
}
