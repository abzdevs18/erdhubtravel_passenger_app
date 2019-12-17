package com.example.debian.ert;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.debian.ert.adapter.Adapter;
import com.example.debian.ert.adapter.MarkerAdapater;
import com.example.debian.ert.model.Alert;
import com.example.debian.ert.model.MarkerModel;

import java.util.List;

public class RouteBusNumberDialog extends BottomSheetDialogFragment {

    RecyclerView mRecycler;
    private List<MarkerModel> markerAdapaters;

    private MarkerAdapater adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =inflater.inflate(R.layout.bottom_sheet,container,false);



        LinearLayoutManager routeLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);

        mRecycler = v.findViewById(R.id.bottomRecycler);
        mRecycler.setLayoutManager(routeLayoutManager);
        mRecycler.setHasFixedSize(true);
        mRecycler.setHasFixedSize(true);



        return v;
    }
}
