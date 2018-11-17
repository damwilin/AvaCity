package com.lionapps.wili.avacity.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.models.User;
import com.lionapps.wili.avacity.viewmodel.MainViewModel;
import com.squareup.picasso.Picasso;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMapClickListener {

    private MainViewModel viewModel;
    private GoogleMap map;
    private final static int REQUEST_LOCATION_PERMISSION = 1001;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        // TODO: Use the ViewModel

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setLocationEnabled();
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void setLocationEnabled() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
            EasyPermissions.requestPermissions(this, "Please grant location permission", REQUEST_LOCATION_PERMISSION, perms);
        } else {
            map.setMyLocationEnabled(true);
            Snackbar.make(getActivity().findViewById(R.id.coordinator), "Location enabled", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }
}
