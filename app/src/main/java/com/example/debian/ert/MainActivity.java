package com.example.debian.ert;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
//import android.support.v7.widget.Toolbar;

import com.example.debian.ert.api.ApiClient;
import com.example.debian.ert.api.ApiInterface;
import com.example.debian.ert.fragment.Home;
import com.example.debian.ert.fragment.Places;
import com.example.debian.ert.model.Depart;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    public static final String CHANNEL_ID = "channel_1";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManagerCompat notificationManagerCompat;
    RelativeLayout mHomeLayout;
    int Navigation = 0;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);

//        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
//        tx.replace(R.id.fragment_holder,new Home());
//        tx.commit();
//        show();

        fragment = new Home();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,fragment,"Categeory").addToBackStack("category").commit();

        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());

        mHomeLayout = findViewById(R.id.relativeHome);
        int resourceId = getResources().getIdentifier("navigation_bar_height","dimen","android");
        if (resourceId > 0){
            Navigation = getResources().getDimensionPixelSize(resourceId);
            mHomeLayout.setPadding(0,0,0,Navigation);
        }


//        Thread time = new Thread(){
//            @Override
//            public void run(){
//                while(!interrupted()){
//                    try{
//                        Thread.sleep(1000);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                final ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//                                Call<List<Depart>> response = apiInterface.check();
//                                response.enqueue(new Callback<List<Depart>>() {
//                                    @Override
//                                    public void onResponse(Call<List<Depart>> call, Response<List<Depart>> response) {
//                                        final String code = response.body().get(0).getCode().toString();
//                                        final String route = response.body().get(0).getRoute().toString();
//                                        String route_id = response.body().get(0).getRoute_id();
//
//                                        if (code != "") {
////                                            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//                                            Call<List<Depart>> updateSeen = apiInterface.seenDepart(route_id);
//                                            updateSeen.enqueue(new Callback<List<Depart>>() {
//                                                @Override
//                                                public void onResponse(Call<List<Depart>> call, Response<List<Depart>> response) {
//                                                    //Toast.makeText(getApplicationContext(),response.body().toString(),Toast.LENGTH_LONG).show();
//                                                }
//
//                                                @Override
//                                                public void onFailure(Call<List<Depart>> call, Throwable t) {
//                                                    Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.notif);
//                                                    Notification builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
//                                                            .setSmallIcon(R.drawable.ic_notif)
//                                                            .setContentTitle("The Easy-Ride Departed:")
//                                                            .setContentText("From " +route)
//                                                            .setSound(uri)
//                                                            .setPriority(NotificationCompat.PRIORITY_HIGH)
//                                                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                                                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                                                            .build();
//                                                    notificationManagerCompat.notify(NOTIFICATION_ID,builder);
//                                                }
//                                            });
//                                        }else{
//
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<List<Depart>> call, Throwable t) {
////                                        Toast.makeText(getApplicationContext(),t.toString(), Toast.LENGTH_LONG).show();
//
//                                    }
//                                });
//                            }
//                        });
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//        };
//        time.start();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            String tag = "";
            switch (menuItem.getItemId()){
                case R.id.todo:
                    fragment = new Home();
                    break;
                case R.id.place:
                    fragment = new Places();
                    tag = "Chat";
                    break;
                case R.id.vendor:
//                    fragment = new Home();
                    startActivity(new Intent(getApplicationContext(), Routes.class));
                    break;
                default:
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,fragment,tag).addToBackStack("home").commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,selectedFragment).commit();
//            ActionBar actionBar = getSupportActionBar();
//            actionBar.setElevation(0);
            return true;
        }
    };

//    Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.notif);
//    public void show(){
//        Notification builder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_notif)
//                .setContentTitle("Job Application Status")
//                .setContentText("Your application is now confirmd!")
//                .setSound(uri)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
//                .build();
//        notificationManagerCompat.notify(NOTIFICATION_ID,builder);
//    }
}
