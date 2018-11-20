package com.lionapps.wili.avacity.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;
import com.lionapps.wili.avacity.repository.FirebaseRepository;
import com.lionapps.wili.avacity.repository.Repository;
import com.lionapps.wili.avacity.utils.MapUtils;
import com.lionapps.wili.avacity.viewmodel.MainViewModel;
import com.lionapps.wili.avacity.viewmodel.MapViewModel;
import com.lionapps.wili.avacity.viewmodel.ViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    @BindView(R.id.map_view)
    MapView mapView;
    private MapViewModel viewModel;
    private GoogleMap map;
    private final static int REQUEST_LOCATION_PERMISSION = 1001;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        ButterKnife.bind(this, view);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }catch (Exception e){
            e.printStackTrace();
        }
        displayMap();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewModelFactory factory = new ViewModelFactory();
        viewModel = ViewModelProviders.of(this, factory).get(MapViewModel.class);
        // TODO: Use the ViewModel
    }
    private void displayMap() {
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        displayMarkers();
        setLocationEnabled();
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

    private void setLocationEnabled() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
            EasyPermissions.requestPermissions(this, "Please grant location permission", REQUEST_LOCATION_PERMISSION, perms);
        } else {
            map.setMyLocationEnabled(true);
            Snackbar.make(getActivity().findViewById(R.id.coordinator), "Location enabled", Snackbar.LENGTH_LONG).show();
        }
    }

    private void setOnMarkerClickListener(){
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String tag = marker.getTag().toString();
                viewModel.setMarkerTag(tag);
                //displayPlaceDetailsFragment();
                //expandSlidingPanel();
                return true;
            }
        });
    }

    private void setOnMapLongClickListener(){
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                viewModel.setClickedLatLng(latLng);
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                //displayAddPlaceFragment();
                //expandSlidingPanel();
            }
        });
    }


}
