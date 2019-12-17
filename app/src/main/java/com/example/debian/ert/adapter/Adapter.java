package com.example.debian.ert.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.debian.ert.R;
import com.example.debian.ert.api.ApiClient;
import com.example.debian.ert.model.Places;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private List<Places> posts;
    private Context context;

    public Adapter(List<Places> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_menu,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
//        holder.content.setText(posts.get(i).getContent());
        String apiClient = ApiClient.BASE_URL;

        holder.name.setText(posts.get(i).getName());
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

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

//            content = (TextView)itemView.findViewById(R.id.content);
            name = (TextView)itemView.findViewById(R.id.name);
            img = (ImageView)itemView.findViewById(R.id.image);
        }
    }
}
