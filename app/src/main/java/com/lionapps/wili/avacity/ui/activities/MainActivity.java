package com.lionapps.wili.avacity.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.jaeger.library.StatusBarUtil;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.ui.fragments.AddPlaceFragment;
import com.lionapps.wili.avacity.ui.fragments.PlaceDetailsFragment;
import com.lionapps.wili.avacity.utils.MapUtils;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.viewmodel.MainViewModel;
import com.lionapps.wili.avacity.viewmodel.ViewModelFactory;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, AddPlaceFragment.OnUploadClickListener{
    private FragmentManager fragmentManager;
    private AddPlaceFragment addPlaceFragment;
    private PlaceDetailsFragment placeDetailsFragment;


    public MainViewModel viewModel;
    private GoogleMap map;

    private final static int REQUEST_LOCATION_PERMISSION = 1001;


    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.sliding_up_panel)
    SlidingUpPanelLayout slidingUpPanel;
    @BindView(R.id.bottom_navigation_menu)
    BottomNavigationView bottomNavigationView;

    private SupportMapFragment mapFragment;
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTranslucent(this,50);
        ButterKnife.bind(this);
        setupBottomNavigationView();
        ViewModelFactory factory = new ViewModelFactory();
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        initializeFragments();
    }


    private void startUserDetailsActivity(){
        Intent userDetailsActivityIntent = new Intent(this, UserDetailsActivity.class);
        startActivity(userDetailsActivityIntent);
    }
    private void initializeFragments() {
        fragmentManager = getSupportFragmentManager();
        mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    private void displayPlaceDetailsFragment(){
        placeDetailsFragment = new PlaceDetailsFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.sliding_panel_container, placeDetailsFragment)
                .commit();
    }

    private void setOnMarkerClickListener(){
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String tag = marker.getTag().toString();
                viewModel.setMarkerTag(tag);
                displayPlaceDetailsFragment();
                expandSlidingPanel();
                return true;
            }
        });
    }

    private void setOnMapLongClickListener(){
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                viewModel.setClickedLatLng(latLng);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));
                displayAddPlaceFragment();
                expandSlidingPanel();
            }
        });
    }

    private void displayAddPlaceFragment(){
        addPlaceFragment = new AddPlaceFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.sliding_panel_container, addPlaceFragment)
                .commit();
        addPlaceFragment.setmCallback(this);
    }

    private void setLocationEnabled() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
            EasyPermissions.requestPermissions(this, "Please grant location permission", REQUEST_LOCATION_PERMISSION, perms);
        } else {
            map.setMyLocationEnabled(true);
            Snackbar.make(this.findViewById(R.id.coordinator), "LocationUtils enabled", Snackbar.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setLocationEnabled();
        setupLocation();
        displayMarkers();
        setOnMapLongClickListener();
        setOnMarkerClickListener();
    }

    private void displayMarkers(){
        viewModel.getPlacesListLiveData().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                for (Place currPlace : places){
                    Marker currMarker;
                    currMarker = map.addMarker(MapUtils.createMarkerFromPlace(currPlace));
                    currMarker.setTag(currPlace.getPlaceId());
                }
            }
        });
    }

    private void expandSlidingPanel(){
        slidingUpPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
        slidingUpPanel.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    private void setupBottomNavigationView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.map_view:
                        //Curent Activity
                        break;
                    case R.id.account_view:
                        startUserDetailsActivity();
                }
                return true;
            }
        });
    }


    @Override
    public void onUploadClick() {
        slidingUpPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }

    private void setupLocation() {
        //TODO Get only first time location, then check by map.LocationEnabled(); or Track by this code and draw point on map, then delete;
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(MapUtils.getLatLngFromLoctation(location), 17));
        } else {
            EasyPermissions.requestPermissions(this, "Please grant location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }
}
