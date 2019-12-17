package com.example.debian.ert.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.debian.ert.adapter.PlaceAdapter;
import com.example.debian.ert.api.ApiClient;
import com.example.debian.ert.api.ApiInterface;
import com.example.debian.ert.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Places extends Fragment {
    View v;
    public Places(){

    }
    TextView t_place,sub_t_place,mplace_n,mplace_loc_;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<com.example.debian.ert.model.Places> posts;
    private PlaceAdapter placeAdapter;
    private ApiInterface apiInterface;
    LinearLayout place;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.places,container,false);
        t_place=v.findViewById(R.id.t_place);
        sub_t_place=v.findViewById(R.id.sub_t_place);

        mplace_loc_=v.findViewById(R.id.place_add);
        mplace_n=v.findViewById(R.id.place_name);

        Typeface gibsonBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/gibson-bold.ttf");
        Typeface gibsonRegular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Gibson-Regular.ttf");
        t_place.setTypeface(gibsonRegular);
        sub_t_place.setTypeface(gibsonBold);
//        mplace_loc_.setTypeface(gibsonRegular);
//        mplace_n.setTypeface(gibsonBold);

        /*To create a horizontal recyclerView */
        recyclerView = v.findViewById(R.id.r_places);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        post();
        return v;
    }
    public void post(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<com.example.debian.ert.model.Places>> call = apiInterface.getPlaces();
        call.enqueue(new Callback<List<com.example.debian.ert.model.Places>>() {
            @Override
            public void onResponse(Call<List<com.example.debian.ert.model.Places>> call, Response<List<com.example.debian.ert.model.Places>> response) {
                posts = response.body();
                placeAdapter = new PlaceAdapter(posts,getActivity());
                recyclerView.setAdapter(placeAdapter);
            }

            @Override
            public void onFailure(Call<List<com.example.debian.ert.model.Places>> call, Throwable t) {

            }
        });

    }


}
