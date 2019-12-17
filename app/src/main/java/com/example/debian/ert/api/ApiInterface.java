package com.example.debian.ert.api;

import com.example.debian.ert.model.BusRoute;
import com.example.debian.ert.model.MarkerModel;
import com.example.debian.ert.model.Places;
import com.example.debian.ert.model.Alert;
import com.example.debian.ert.model.Depart;
import com.example.debian.ert.model.ResponseModel;
import com.example.debian.ert.model.RoutesModel;
import com.example.debian.ert.model.Terminal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("Api/places")
    Call<List<Places>> getPlaces();

    @POST("Api/alert")
    Call<List<Alert>> transitActivity();

    @POST("Api/terminalLocations")
    Call<List<Terminal>> terminalLocation();

    @POST("Api/getRoutes")
    Call<List<BusRoute>> getRoutes();

    @FormUrlEncoded
    @POST("Api/queryRoute")
    Call<List<BusRoute>> routeSearch(@Field("query") String query);

    @FormUrlEncoded
    @POST("Api/markerQuery")
    Call<List<MarkerModel>> markerQuery(@Field("query") String query);
}
