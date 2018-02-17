package com.scbchallenge.servicegateway;


import com.scbchallenge.models.MobileDetail;
import com.scbchallenge.models.MobileList;
import com.scbchallenge.utils.ApiConstants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SCBService {
    @GET(ApiConstants.BASE_URL)
    Call<List<MobileList>> getMobileList();

    @GET(ApiConstants.DETAIL_IMAGE)
    Call<List<MobileDetail>> getMobileDetail(@Path("mobile_id") int mobileId);
}
