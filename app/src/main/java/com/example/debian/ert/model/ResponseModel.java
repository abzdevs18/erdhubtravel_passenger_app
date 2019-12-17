package com.example.debian.ert.model;

import com.google.gson.annotations.SerializedName;

public class ResponseModel {
    @SerializedName("code")
    private String code;
    @SerializedName("email")
    private String email;
    @SerializedName("pass")
    private String pass;
    @SerializedName("tbl_id")
    private String tbl_id;

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getTbl_id() {
        return tbl_id;
    }

    public String getCode() {
        return code;
    }


}
