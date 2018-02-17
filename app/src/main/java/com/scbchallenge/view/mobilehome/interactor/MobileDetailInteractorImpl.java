package com.scbchallenge.view.mobilehome.interactor;


import com.scbchallenge.models.MobileDetail;
import com.scbchallenge.networkutils.NetworkAdapter;
import com.scbchallenge.utils.AppConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileDetailInteractorImpl implements MobileDetailInteractor {

    @Override
    public void mobileDetailItems(final OnFinishedListener listener, int mobileId) {
        NetworkAdapter.getInstance().getMobileDetail(mobileId).enqueue(new Callback<List<MobileDetail>>() {
            @Override
            public void onResponse(Call<List<MobileDetail>> call, Response<List<MobileDetail>> response) {
                listener.onFinished(response.body());
            }

            @Override
            public void onFailure(Call<List<MobileDetail>> call, Throwable t) {
                listener.onFailure(AppConstants.ERROR_MESSAGE);
            }
        });
    }
}

