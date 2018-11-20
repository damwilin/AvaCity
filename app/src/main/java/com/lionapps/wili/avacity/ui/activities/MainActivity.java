package com.lionapps.wili.avacity.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.jaeger.library.StatusBarUtil;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.ui.fragments.AddPlaceFragment;
import com.lionapps.wili.avacity.ui.fragments.MapFragment;
import com.lionapps.wili.avacity.ui.fragments.PlaceDetailsFragment;
import com.lionapps.wili.avacity.utils.MapUtils;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.repository.FirebaseRepository;
import com.lionapps.wili.avacity.repository.Repository;
import com.lionapps.wili.avacity.ui.fragments.AccountFragment;
import com.lionapps.wili.avacity.viewmodel.MainViewModel;
import com.lionapps.wili.avacity.viewmodel.ViewModelFactory;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
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

public class MainActivity extends AppCompatActivity implements AddPlaceFragment.OnUploadClickListener {
    private FragmentManager fragmentManager;
    private AccountFragment accountFragment;
    private AddPlaceFragment addPlaceFragment;
    private MapFragment mapFragment;
    private PlaceDetailsFragment placeDetailsFragment;


    public MainViewModel viewModel;




    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.sliding_up_panel)
    SlidingUpPanelLayout slidingUpPanel;
    @BindView(R.id.bottom_navigation_menu)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setTranslucent(this,50);
        ButterKnife.bind(this);;
        ViewModelFactory factory = new ViewModelFactory();
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        initializeFragments();
        displayAccountFragment();
        displayMapFragment();
        setupBottomNavigationView();
    }
    private void initializeFragments() {
        accountFragment = new AccountFragment();
        addPlaceFragment = new AddPlaceFragment();
        placeDetailsFragment = new PlaceDetailsFragment();
        fragmentManager = getSupportFragmentManager();
        mapFragment = new MapFragment();
    }

    private void displayAccountFragment() {
        fragmentManager.beginTransaction()
                .add(R.id.account_container, accountFragment)
                .commit();
    }

    private void displayPlaceDetailsFragment(){
        fragmentManager.getFragments().clear();
        fragmentManager.beginTransaction()
                .replace(R.id.sliding_panel_container, placeDetailsFragment)
                .commit();
    }

    private void displayMapFragment(){
        fragmentManager.beginTransaction()
                .replace(R.id.main_container, mapFragment)
                .commit();
    }





    private void displayAddPlaceFragment(){
        fragmentManager.getFragments().clear();
        fragmentManager.beginTransaction()
                .replace(R.id.sliding_panel_container, addPlaceFragment)
                .commit();
        addPlaceFragment.setmCallback(this);
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
                        //TODO switch mapFragment
                    case R.id.account_view:
                        //TODO switch accountDetailsView
                }
                return true;
            }
        });
    }


    @Override
    public void onUploadClick() {
        slidingUpPanel.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
    }
}
