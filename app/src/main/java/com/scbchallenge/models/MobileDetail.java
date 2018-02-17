package com.scbchallenge.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileDetail {
    @SerializedName("mobile_id")
    @Expose
    private int mobileId;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("id")
    @Expose
    private int id;

    public int getMobileId() {
        return mobileId;
    }

    public void setMobileId(int mobileId) {
        this.mobileId = mobileId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
