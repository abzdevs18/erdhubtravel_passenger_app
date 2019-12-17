package com.example.debian.ert.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.debian.ert.R;
import com.example.debian.ert.model.Alert;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {
    List<Alert> alert;
    Context context;

    public ActivityAdapter(List<Alert> alert, Context context) {
        this.alert = alert;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_of_departures,viewGroup,false);
        return new ActivityAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.mRoute.setText(alert.get(i).getName());
        myViewHolder.mTime.setText(alert.get(i).getDpart());
    }

    @Override
    public int getItemCount() {
        return alert.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTime,mRoute,notif_fot,from;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Typeface gibsonRegular = Typeface.createFromAsset(context.getAssets(),"fonts/Gibson-Regular.ttf");
            mTime = (TextView)itemView.findViewById(R.id.depart_time);
            mTime.setTypeface(gibsonRegular);
//            notif_fot.setTypeface(gibsonRegular);
//            from.setTypeface(gibsonRegular);

            mRoute = (TextView)itemView.findViewById(R.id.route);
            mRoute.setTypeface(gibsonRegular);
        }
    }
}
