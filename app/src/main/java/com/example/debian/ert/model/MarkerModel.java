package com.example.debian.ert.model;

import com.google.gson.annotations.SerializedName;

public class MarkerModel {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("busNum")
    private String busNum;

    @SerializedName("dpart")
    private String dpart;

    @SerializedName("status")
    private String status;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBusNum() {
        return busNum;
    }

    public String getDpart() {
        return dpart;
    }

    public String getStatus() {
        return status;
    }
}
