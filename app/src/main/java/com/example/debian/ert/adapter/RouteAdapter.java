package com.example.debian.ert.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.debian.ert.R;
import com.example.debian.ert.model.BusRoute;
import com.example.debian.ert.model.RoutesModel;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder> {

    List<BusRoute> routes;
    Context context;

    public RouteAdapter(List<BusRoute> routes, Context context) {
        this.routes = routes;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_terminal_loc,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        holder.mRouteD.setText(routes.get(i).getName());
        holder.mRouteSched.setText(routes.get(i).getDpart());

        if(routes.get(i).getStatus().equals("1")){
            holder.mRouteS.setText("Departed");
        }else{
            holder.mRouteS.setText("Available");
            holder.mRouteS.setTextColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mRouteSched, mRouteD, mRouteS;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mRouteD = itemView.findViewById(R.id.routeDestination);
            mRouteSched = itemView.findViewById(R.id.routeSched);
            mRouteS = itemView.findViewById(R.id.routeStatus);

        }
    }
}
