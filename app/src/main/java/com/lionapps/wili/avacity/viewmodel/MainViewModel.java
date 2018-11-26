package com.lionapps.wili.avacity.viewmodel;


import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.lionapps.wili.avacity.interfaces.SearchResultListener;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.repository.Repository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private Repository repository;
    private LatLng clickedLatLng;
    private Bitmap currPlacePhoto;
    private String markerTag;

    private List<Place> searchList;

    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public List<Place> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<Place> searchList) {
        this.searchList = searchList;
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

    public LiveData getPlacesListLiveData(){
        return repository.getPlacesLiveData();
    }

    public void insertPlace(Place place){
        repository.insertPlace(place);
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

    public void searchForPlace(SearchResultListener listener){
        repository.searchForPlace(listener);
    }
}
