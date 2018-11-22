package com.lionapps.wili.avacity.viewmodel;


import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.repository.Repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private Repository repository;
    private LatLng clickedLatLng;
    private Bitmap currPlacePhoto;
    private String markerTag;

    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public String getMarkerTag() {
        return markerTag;
    }

    public void setMarkerTag(String markerTag) {
        this.markerTag = markerTag;
    }

    public String getUserId() {
        return repository.getCurrUser().getUid();
    }

    public LiveData getCurrUserLiveData(){
        return repository.getUserLiveData(getUserId());
    }

    public LiveData getUserLiveData(String userId) {
        return repository.getUserLiveData(userId);
    }

    public LiveData getPlacesListLiveData(){
        return repository.getPlacesLiveData();
    }

    public void insertPlace(Place place){
        repository.insertPlace(place, currPlacePhoto);
    }

    public LatLng getClickedLatLng() {
        return clickedLatLng;
    }

    public void setClickedLatLng(LatLng clickedLatLng) {
        this.clickedLatLng = clickedLatLng;
    }

    public void addPlaceToUser(String placeId){
        repository.addPlaceToUser(getUserId(),placeId);
    }

    public Bitmap getCurrPlacePhoto() {
        return currPlacePhoto;
    }

    public void setCurrPlacePhoto(Bitmap currPlacePhoto) {
        this.currPlacePhoto = currPlacePhoto;
    }
}
