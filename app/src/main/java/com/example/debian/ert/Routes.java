package com.example.debian.ert;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.debian.ert.adapter.Adapter;
import com.example.debian.ert.adapter.RouteAdapter;
import com.example.debian.ert.api.ApiClient;
import com.example.debian.ert.api.ApiInterface;
import com.example.debian.ert.fragment.Home;
import com.example.debian.ert.fragment.Places;
import com.example.debian.ert.model.BusRoute;
import com.example.debian.ert.model.RoutesModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Routes extends AppCompatActivity {


    private List<BusRoute> routes;

    private RouteAdapter routeAdapter;

    ApiInterface apiInterface;

    RecyclerView mRecycler;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);



        LinearLayoutManager routeLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);

        mRecycler = findViewById(R.id.routeInDb);
        mRecycler.setLayoutManager(routeLayoutManager);
        mRecycler.setHasFixedSize(true);
        mRecycler.setHasFixedSize(true);

        routeList();
        searchView = findViewById(R.id.routeQuery);
        searchView.setIconifiedByDefault(true);
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                routeQuery(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                routeQuery(s);
                return false;
            }
        });
    }

    private void routeQuery(String query) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<BusRoute>> routeList = apiInterface.routeSearch(query);
        routeList.enqueue(new Callback<List<BusRoute>>() {
            @Override
            public void onResponse(Call<List<BusRoute>> call, Response<List<BusRoute>> response) {
                routes = response.body();
                routeAdapter = new RouteAdapter(routes,Routes.this);
                mRecycler.setAdapter(routeAdapter);

            }

            @Override
            public void onFailure(Call<List<BusRoute>> call, Throwable t) {

            }
        });

    }
    private void routeList() {

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<BusRoute>> routeList = apiInterface.getRoutes();
        routeList.enqueue(new Callback<List<BusRoute>>() {
            @Override
            public void onResponse(Call<List<BusRoute>> call, Response<List<BusRoute>> response) {
                routes = response.body();
                routeAdapter = new RouteAdapter(routes,Routes.this);
                mRecycler.setAdapter(routeAdapter);

            }

            @Override
            public void onFailure(Call<List<BusRoute>> call, Throwable t) {

            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.todo:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    break;
                case R.id.place:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    break;
                case R.id.vendor:
//                    fragment = new Home();
                    startActivity(new Intent(getApplicationContext(), Routes.class));
                    break;
                default:
                    break;
            }
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder,selectedFragment).commit();
//            ActionBar actionBar = getSupportActionBar();
//            actionBar.setElevation(0);
            return true;
        }
    };
}
