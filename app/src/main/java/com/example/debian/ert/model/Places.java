package com.example.debian.ert.model;

import com.google.gson.annotations.SerializedName;

public class Places {

    @SerializedName("id")
    private String id;

    @SerializedName("image")
    private String image;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("coordinates")
    private String coordinates;

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCoordinates() {
        return coordinates;
    }
}
