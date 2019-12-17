package com.example.debian.ert.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.debian.ert.api.ApiClient;
import com.example.debian.ert.model.Places;
import com.example.debian.ert.R;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder> {

    private List<Places> posts;
    private Context context;

    public PlaceAdapter(List<Places> posts, Context context) {
        this.posts = posts;
        this.context = context.getApplicationContext();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.featured_places,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {

        String apiClient = ApiClient.BASE_URL;

        holder.loc.setText(posts.get(i).getName());
        holder.name.setText(posts.get(i).getName());
        holder.loc.setText(posts.get(i).getAddress());
        Uri uri = Uri.parse("https://primelinetools.com/pub/media/catalog/product/placeholder/default/ajax-loader02_4.gif");
        Glide.with(context)
                .load(apiClient + "/img/places/" + posts.get(i).getImage())
                .thumbnail(Glide.with(context).load(uri))
                .centerCrop()
                .crossFade()
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,loc;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Typeface gibsonBold = Typeface.createFromAsset(context.getAssets(),"fonts/gibson-bold.ttf");
            Typeface gibsonRegular = Typeface.createFromAsset(context.getAssets(),"fonts/Gibson-Regular.ttf");
            name = (TextView)itemView.findViewById(R.id.place_name);
            name.setTypeface(gibsonBold);
            loc = (TextView)itemView.findViewById(R.id.place_add);
            loc.setTypeface(gibsonRegular);
            img = (ImageView)itemView.findViewById(R.id.place_img);
        }
    }
}
