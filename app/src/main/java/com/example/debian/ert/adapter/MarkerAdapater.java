package com.example.debian.ert.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.debian.ert.R;
import com.example.debian.ert.model.MarkerModel;

import java.util.List;

public class MarkerAdapater extends RecyclerView.Adapter<MarkerAdapater.ViewHolder> {

    List<MarkerModel> markerModel;
    Context context;

    public MarkerAdapater(List<MarkerModel> markerModel, Context context) {
        this.markerModel = markerModel;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bottom_recycler_items,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.mRouteBusNum.setText(markerModel.get(i).getBusNum());
        holder.mRouteSched.setText(markerModel.get(i).getDpart());
        holder.mRoute.setText(markerModel.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return markerModel.size();
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mRoute, mRouteSched, mRouteBusNum;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mRoute = itemView.findViewById(R.id.routeItem);
            mRouteSched = itemView.findViewById(R.id.routeBusTime);
            mRouteBusNum = itemView.findViewById(R.id.routeBusNum);
        }
    }
}
