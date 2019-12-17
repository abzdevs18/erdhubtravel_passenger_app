package com.example.debian.ert;

import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.debian.ert.adapter.MarkerAdapater;
import com.example.debian.ert.api.ApiClient;
import com.example.debian.ert.api.ApiInterface;
import com.example.debian.ert.model.MarkerModel;
import com.example.debian.ert.model.Terminal;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.LocationComponentOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

public class TerminalRoute extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener{

//    List<Terminal> terminals;
    private ApiInterface apiInterface;



    MapView mapView;

    private MapboxMap map;
    // variables for adding location layer
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    // variables for calculating and drawing a route
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    // variables needed to initialize navigation
    private Button button;
    // Animator
    private ValueAnimator markerAnimator;
    private boolean markerSelected = false;
//    RelativeLayout mHomeLayout;
//    int Navigation = 0;

//    MaterialSearchView searchView;
    MaterialSearchView searchView;
    Toolbar toolbar;

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String MARKER_IMAGE = "custom-image";
    private static final String LAYER_ID = "LAYER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        // This contains the MapView in XML and needs to be called after the access token is configured.
//        setContentView(R.layout.activity_terminal_route);
        setContentView(R.layout.activity_terminal_route);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);

        LinearLayout linearSheet = findViewById(R.id.linearSheet);

        FloatingActionButton fab = findViewById(R.id.startNavigation);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Snackbar.make(v,"Heres the snackbar",Snackbar.LENGTH_SHORT).setAction("Action",null).show();
//                RouteBusNumberDialog bottomSheet = new RouteBusNumberDialog();
//                bottomSheet.show(getSupportFragmentManager(),"bottomSheet");

                boolean simulateRoute = true;
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        .directionsRoute(currentRoute)
                        .shouldSimulateRoute(simulateRoute)
                        .build();
// Call this method with Context from within an Activity
                NavigationLauncher.startNavigation(TerminalRoute.this, options);
            }
        });

        mapView = findViewById(R.id.mapViewTerminal);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

//        button = findViewById(R.id.startButton);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean simulateRoute = true;
//                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
//                        .directionsRoute(currentRoute)
//                        .shouldSimulateRoute(simulateRoute)
//                        .build();
//// Call this method with Context from within an Activity
//                NavigationLauncher.startNavigation(TerminalRoute.this, options);
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings( {"MissingPermission"})
    public void enableLocation(@NonNull Style loadedMapStyle){
        if(PermissionsManager.areLocationPermissionsGranted(this)){
            initializeLocationEngine();
            initializeLocationLayer();

        }else{
            permissionsManager =  new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            LocationComponentOptions locationComponentOptions = LocationComponentOptions.builder(TerminalRoute.this)
//                    .elevation(5)
                    .foregroundDrawable(R.drawable.origin)
//                    .accuracyAlpha(float)
                    .build();
        // Activate the MapboxMap LocationComponent to show user location
//         Adding in LocationComponentOptions is also an optional parameter
            LocationComponentActivationOptions locationComponentActivationOptions = LocationComponentActivationOptions
                    .builder(TerminalRoute.this,loadedMapStyle)
                    .locationComponentOptions(locationComponentOptions)
                    .build();

            locationComponent = map.getLocationComponent();

            locationComponent.activateLocationComponent(locationComponentActivationOptions);
//            locationComponent.activateLocationComponent(this, loadedMapStyle);

            locationComponent.setLocationComponentEnabled(true);
            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

//            locationComponent.activateLocationComponent(locationComponentActivationOptions);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
//        loadedMapStyle.addImage("destination-icon-id",
//                BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_location_on_black_24dp));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Style style = map.getStyle();

        Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

//        GeoJsonSource source = map.getStyle().getSourceAs("destination-source-id");
//        if (source != null) {
//            source.setGeoJson(Feature.fromGeometry(destinationPoint));
//        }

        getRoute(originPoint, destinationPoint);
        button.setEnabled(true);
//        button.setBackgroundResource(R.color.mapboxBlue);

        if (style != null) {
            final SymbolLayer selectedMarkerSymbolLayer =
                    (SymbolLayer) style.getLayer("selected-marker-layer");

            final PointF pixel = map.getProjection().toScreenLocation(point);
            List<Feature> features = map.queryRenderedFeatures(pixel, "marker-layer");
            List<Feature> selectedFeature = map.queryRenderedFeatures(
                    pixel, "selected-marker-layer");

            if (selectedFeature.size() > 0 && markerSelected) {
                return false;
            }

            if (features.isEmpty()) {
                if (markerSelected) {
                    deselectMarker(selectedMarkerSymbolLayer);
                }
                return false;
            }
            GeoJsonSource source = style.getSourceAs("selected-marker");
            if (source != null) {
                source.setGeoJson(FeatureCollection.fromFeatures(
                        new Feature[]{Feature.fromGeometry(features.get(0).geometry())}));
            }

            if (markerSelected) {
                deselectMarker(selectedMarkerSymbolLayer);
            }
            if (features.size() > 0) {
                selectMarker(selectedMarkerSymbolLayer);
            }
        }
        return true;
    }

    private void selectMarker(final SymbolLayer iconLayer) {
        markerAnimator = new ValueAnimator();
        markerAnimator.setObjectValues(1f, 2f);
        markerAnimator.setDuration(300);
        markerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                iconLayer.setProperties(
                        PropertyFactory.iconSize((float) animator.getAnimatedValue())
                );
            }
        });
        markerAnimator.start();
        markerSelected = true;
    }

    private void deselectMarker(final SymbolLayer iconLayer) {
        markerAnimator.setObjectValues(2f, 1f);
        markerAnimator.setDuration(300);
        markerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                iconLayer.setProperties(
                        PropertyFactory.iconSize((float) animator.getAnimatedValue())
                );
            }
        });
        markerAnimator.start();
        markerSelected = false;
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, map, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    private void initializeLocationEngine() {
    }

    private void initializeLocationLayer() {
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.map = mapboxMap;

        IconFactory iconFactory = IconFactory.getInstance(TerminalRoute.this);
        Icon icon = iconFactory.fromResource(R.drawable.busstop);



        List<Feature> symbolicLayerIconList = new ArrayList<>();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Terminal>> termincalLocations = apiInterface.terminalLocation();
        termincalLocations.enqueue(new Callback<List<Terminal>>() {
            @Override
            public void onResponse(Call<List<Terminal>> call, Response<List<Terminal>> response) {
//                terminals = response.body();
                for (int i = 0; i < response.body().size(); i++) {
                    String lnglat = response.body().get(i).getCoordinate_mobile();
                    String[] part = lnglat.split(", ");
                    Double lng = Double.parseDouble(part[0]);
                    Double lat = Double.parseDouble(part[1]);
//                    Double latlng = Double.parseDouble(response.body().get(i).getCoordinate_mobile());
//                    symbolicLayerIconList.add(Feature.fromGeometry(Point.fromLngLat(lng,lat)));

                    String latlng = response.body().get(i).getLatlong();
                    String[] latlngpart = latlng.split(", ");
                    Double snapLat = Double.parseDouble(latlngpart[0]);
                    Double snapLng = Double.parseDouble(latlngpart[1]);

                    mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(snapLat,snapLng))
                    .icon(icon)
                    .title(response.body().get(i).getName().toString()));

                    Log.d("Result", lng + ":" + lat + response.body().size());
                }
            }

            @Override
            public void onFailure(Call<List<Terminal>> call, Throwable t) {
                Log.d("Resuldt", t.getMessage());

            }
        });


        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Point destinationPoint = Point.fromLngLat(marker.getPosition().getLongitude(),marker.getPosition().getLatitude());
                Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                        locationComponent.getLastKnownLocation().getLatitude());
                getRoute(originPoint,destinationPoint);
//                button.setEnabled(true);
                RouteBusNumberDialog bottomSheet = new RouteBusNumberDialog();
                bottomSheet.show(getSupportFragmentManager(),"bottomSheet");

                apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                Call<List<MarkerModel>> call = apiInterface.markerQuery(marker.getTitle().toString());
                call.enqueue(new Callback<List<MarkerModel>>() {
                    @Override
                    public void onResponse(Call<List<MarkerModel>> call, Response<List<MarkerModel>> response) {
                        for (int i = 0; i < response.body().size(); i++){
                            Log.d("Data", response.body().get(i).getName());
                        }
                        List<MarkerModel> markerModels = response.body();
                        MarkerAdapater markerAdapater = new MarkerAdapater(markerModels,getApplicationContext());
                        bottomSheet.mRecycler.setAdapter(markerAdapater);
                    }

                    @Override
                    public void onFailure(Call<List<MarkerModel>> call, Throwable t) {

                    }
                });
//                Toast.makeText(TerminalRoute.this,marker.getTitle() + marker.getPosition(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
//        Lng and Lat in DB is not properly ordered

//        symbolicLayerIconList.add(Feature.fromGeometry(Point.fromLngLat(123.305579,9.303585)));
//        symbolicLayerIconList.add(Feature.fromGeometry(Point.fromLngLat(123.307468,9.304708 )));
//        symbolicLayerIconList.add(Feature.fromGeometry(Point.fromLngLat(123.304346,9.305783)));


//        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")
////                .withImage(ICON_ID, BitmapFactory.decodeResource(TerminalRoute.this.getResources(), R.drawable.terminal))
//                .withImage(ICON_ID, this.getResources().getDrawable(R.drawable.terminal))
//                .withSource(new GeoJsonSource(SOURCE_ID, FeatureCollection.fromFeatures(symbolicLayerIconList)))
//                .withLayer(new SymbolLayer(LAYER_ID,SOURCE_ID)
//                    .withProperties(PropertyFactory.iconImage(ICON_ID),
//                            iconAllowOverlap(true),
//                            iconOffset(new Float[]{0f,-9f})) Style.MAPBOX_STREETS
//                ),
//        last working ends here

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        enableLocation(style);
                        addDestinationIconSymbolLayer(style);
//                        terminalLocations();

//                        mapboxMap.addOnMapClickListener(TerminalRoute.this);
//                        Last working ends here


                        // Add the marker image to map
                        style.addSource(new GeoJsonSource("marker-source",
                                FeatureCollection.fromFeatures(symbolicLayerIconList)));

//                        style.addImage(MARKER_IMAGE, BitmapFactory.decodeResource(TerminalRoute.this.getResources(), R.drawable.terminal));
//                        style.addImage(MARKER_IMAGE,TerminalRoute.this.getResources().getDrawable(R.drawable.busstop));


                        // Adding an offset so that the bottom of the blue icon gets fixed to the coordinate, rather than the
                        // middle of the icon being fixed to the coordinate point.
                        style.addLayer(new SymbolLayer("marker-layer", "marker-source")
                                .withProperties(PropertyFactory.iconImage("my-marker-image"),
                                        iconAllowOverlap(true),
                                        iconOffset(new Float[]{0f, -9f})));

                        // Add the selected marker source and layer
                        style.addSource(new GeoJsonSource("selected-marker"));

                        // Adding an offset so that the bottom of the blue icon gets fixed to the coordinate, rather than the
                        // middle of the icon being fixed to the coordinate point.
                        style.addLayer(new SymbolLayer("selected-marker-layer", "selected-marker")
                                .withProperties(PropertyFactory.iconImage(MARKER_IMAGE),
                                        iconAllowOverlap(true),
                                        iconOffset(new Float[]{0f, -9f})));

//                        This is just a hacked part code. This will disable clicks in the part of the map. so the user will only be allowed to
//                        click on the markers.

//                        mapboxMap.addOnMapClickListener(TerminalRoute.this);
                    }
                });
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocation(map.getStyle());
        } else {
            Toast.makeText(this, "Nor", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
//
//    @Override
//    public boolean onMapClick(@NonNull LatLng point) {
//        return false;
//    }
}
