package com.example.debian.ert.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.debian.ert.Routes;
import com.example.debian.ert.TerminalRoute;
import com.example.debian.ert.adapter.ActivityAdapter;
import com.example.debian.ert.adapter.Adapter;
import com.example.debian.ert.api.ApiClient;
import com.example.debian.ert.api.ApiInterface;
import com.example.debian.ert.Login_activity;
import com.example.debian.ert.model.Places;
import com.example.debian.ert.R;
import com.example.debian.ert.model.Alert;
import com.jgabrielfreitas.core.BlurImageView;

import java.util.List;

import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment implements View.OnClickListener {
    View v;
    public Home(){

    }
    TextView mTitle,mDescription, mfav,mStop,m_fav_place,mSearch_input;
    TextView mAccIcon,mInstruction,t_place,sub_t_place,mTransit;
    BlurImageView mBlur;
    RelativeLayout mOver;
    Button mSearch_btn;
    private Toolbar toolbar;

    private RecyclerView recyclerView,mTransitActivity;
    private RecyclerView.LayoutManager layoutManager,mRec;

    private List<Places> posts;
    private List<Alert> alerts;

    private Adapter adapter;
    private ActivityAdapter alertAdapter;
    private ApiInterface apiInterface;
    LinearLayout place, terminal, routes;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v =inflater.inflate(R.layout.home,container,false);

        mTransitActivity = v.findViewById(R.id.activityRecycler);


        place = v.findViewById(R.id.f_places);
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
                tx.replace(R.id.fragment_holder,new com.example.debian.ert.fragment.Places());
                tx.commit();
            }
        });

//        Find terminal click listener
        terminal = v.findViewById(R.id.f_terminal);
        terminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TerminalRoute.class));
            }
        });

        routes = v.findViewById(R.id.routes);
        routes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Routes.class));
            }
        });


//        Default import won't work so add manually
//        android.support.v7.widget.Toolbar in this activity

        toolbar = (Toolbar)v.findViewById(R.id.home_app_bar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Action");



        mTitle = (TextView)v.findViewById(R.id.title_home);
        mDescription = (TextView)v.findViewById(R.id.sub_title);
        mfav = (TextView)v.findViewById(R.id.fav);
        mStop = (TextView)v.findViewById(R.id.findStop);
        m_fav_place = (TextView)v.findViewById(R.id.fav_p);
        mSearch_input = (TextView)v.findViewById(R.id.search_input);
        mInstruction = (TextView)v.findViewById(R.id.instruction);
        mSearch_btn = (Button)v.findViewById(R.id.search_btn);
        mTransit = (TextView)v.findViewById(R.id.transit);




        //Custom fonts below
        Typeface gibsonBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/gibson-bold.ttf");
        Typeface gibsonRegular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Gibson-Regular.ttf");
        mTitle.setTypeface(gibsonRegular);
        mDescription.setTypeface(gibsonRegular);
        mfav.setTypeface(gibsonRegular);
        mSearch_input.setTypeface(gibsonRegular);
        mInstruction.setTypeface(gibsonRegular);
        mSearch_btn.setTypeface(gibsonRegular);
        mStop.setTypeface(gibsonRegular);
        m_fav_place.setTypeface(gibsonRegular);
        mTransit.setTypeface(gibsonRegular);

        //        If the sdk version of the  device is greater than the KitKat we use transparent titlebar
        Window w = getActivity().getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mBlur = (BlurImageView)v.findViewById(R.id.blurImg);
        mOver = (RelativeLayout)v.findViewById(R.id.imgOver);
        mBlur.setBlur(5);


        // TODO: 23/01/2019 we create a toolbar and make the below's toast message
        mAccIcon = (TextView)v.findViewById(R.id.account_icon);
        mAccIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), Login_activity.class);
//                startActivity(intent);
//                getActivity().finish();
//                Toast toast= Toast.makeText(MainActivity.this,"account",Toast.LENGTH_SHORT);
//                toast.show();
            }
        });

        /*To create a horizontal recyclerView */
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager transitActivity = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);


        recyclerView = v.findViewById(R.id.recyclerView);
        mTransitActivity = v.findViewById(R.id.activityRecycler);
        mTransitActivity.setLayoutManager(transitActivity);
        recyclerView.setLayoutManager(layoutManager); /*To use LinearLayout for horizonatal recyclerview */
//        mRec = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(mRec);
        recyclerView.setHasFixedSize(true);
        mTransitActivity.setHasFixedSize(true);

//        Thread timer = new Thread(){
//            @Override
//            public void run() {
//                while(!interrupted()){
//                    try {
//                        Thread.sleep(1000);
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                alert();
//                            }
//                        };
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        timer.start();
        Thread thread = new Thread(){
            @Override
            public void run() {
                while (!interrupted()) {
                    try {
                        sleep(1000);
                        alert();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        post();
        alert();

        return v;
    }

    @Override
    public void onClick(View v) {

    }

    public void post(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Places>> call = apiInterface.getPlaces();
        call.enqueue(new Callback<List<Places>>() {
            @Override
            public void onResponse(Call<List<Places>> call, Response<List<Places>> response) {
                posts = response.body();
                adapter = new Adapter(posts,getActivity());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Places>> call, Throwable t) {

            }
        });
    }

    public void alert() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        final Call<List<Alert>> transitAlert = apiInterface.transitActivity();
        transitAlert.enqueue(new Callback<List<Alert>>() {
            @Override
            public void onResponse(Call<List<Alert>> call, Response<List<Alert>> response) {
                alerts = response.body();
                alertAdapter = new ActivityAdapter(alerts,getActivity());
                mTransitActivity.setAdapter(alertAdapter);

            }

            @Override
            public void onFailure(Call<List<Alert>> call, Throwable t) {

            }
        });
    }
}
