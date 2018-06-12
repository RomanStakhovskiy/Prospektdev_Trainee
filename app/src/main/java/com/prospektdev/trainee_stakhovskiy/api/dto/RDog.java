package com.prospektdev.trainee_stakhovskiy.api.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RDog {

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("message")
    private List<String> urls;

    public String getStatus() {
        return status;
    }

    public List<String> getUrls() {
        return urls;
    }
}
