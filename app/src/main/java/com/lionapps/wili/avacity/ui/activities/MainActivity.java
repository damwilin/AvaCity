package com.lionapps.wili.avacity.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.ui.fragments.AddPlaceFragment;
import com.lionapps.wili.avacity.utils.MapUtils;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.repository.FirestoreRepository;
import com.lionapps.wili.avacity.repository.Repository;
import com.lionapps.wili.avacity.ui.fragments.AccountFragment;
import com.lionapps.wili.avacity.viewmodel.MainViewModel;
import com.lionapps.wili.avacity.viewmodel.ViewModelFactory;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FragmentManager fragmentManager;
    private AccountFragment accountFragment;
    private AddPlaceFragment addPlaceFragment;
    private SupportMapFragment mapFragment;


    public MainViewModel viewModel;
    private Repository repository;
    private GoogleMap map;

    private final static int REQUEST_LOCATION_PERMISSION = 1001;


    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        repository = new FirestoreRepository();
        ViewModelFactory factory = new ViewModelFactory(repository);
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        //slidingUpPanelLayout.setAnchorPoint(100);
        initializeFragments();
        displayAccountFragment();
        displayAddPlaceFragment();
        displayMap();
    }

    private void initializeFragments() {
        accountFragment = new AccountFragment();
        addPlaceFragment = new AddPlaceFragment();
        fragmentManager = getSupportFragmentManager();
        mapFragment = (SupportMapFragment)fragmentManager
                .findFragmentById(R.id.map);
    }

    private void displayAccountFragment() {
        fragmentManager.beginTransaction()
                .add(R.id.account_container, accountFragment)
                .commit();
    }

    private void displayMap() {
        mapFragment.getMapAsync(this);
    }

    private void displayAddPlaceFragment(){
        fragmentManager.beginTransaction()
                .add(R.id.sliding_panel_container, addPlaceFragment)
                .commit();
    }

    private void setLocationEnabled() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
            EasyPermissions.requestPermissions(this, "Please grant location permission", REQUEST_LOCATION_PERMISSION, perms);
        } else {
            map.setMyLocationEnabled(true);
            Snackbar.make(this.findViewById(R.id.coordinator), "Location enabled", Snackbar.LENGTH_LONG).show();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setLocationEnabled();
        map.addMarker(new MarkerOptions()
        .position(new LatLng(53.01,18.60))
        .title("Title")
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }

    private void displayMarkers(){
        viewModel.getPlacesListLiveData().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                for (Place currPlace : places){
                    map.addMarker(MapUtils.createMarkerFromPlace(currPlace));
                }
            }
        });
    }



}
