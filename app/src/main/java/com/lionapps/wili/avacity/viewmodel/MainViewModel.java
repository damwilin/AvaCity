package com.lionapps.wili.avacity.viewmodel;


import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.lionapps.wili.avacity.interfaces.GetPlaceListener;
import com.lionapps.wili.avacity.interfaces.SearchResultListener;
import com.lionapps.wili.avacity.liveData.UserLiveData;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;
import com.lionapps.wili.avacity.repository.Repository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private Repository repository;
    private LatLng clickedLatLng;
    private Bitmap currPlacePhoto;
    private String markerTag;
    private User currUser;

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

    public LiveData getPlacesListLiveData() {
        return repository.getPlacesLiveData();
    }

    public void insertPlace(Place place) {
        repository.insertPlace(place, currPlacePhoto);
    }

    public LatLng getClickedLatLng() {
        return clickedLatLng;
    }

    public void setClickedLatLng(LatLng clickedLatLng) {
        this.clickedLatLng = clickedLatLng;
    }

    public Bitmap getCurrPlacePhoto() {
        return currPlacePhoto;
    }

    public void setCurrPlacePhoto(Bitmap currPlacePhoto) {
        this.currPlacePhoto = currPlacePhoto;
    }

    public void searchForPlace(SearchResultListener listener, String query) {
        repository.searchForPlace(listener, query);
    }

    public void getPlace(GetPlaceListener listener) {
        repository.getPlace(getMarkerTag(), listener);
    }

    public UserLiveData getUser() {
        return repository.getUserLiveData();
    }

    public void addLikeToPlace(String placeId, int likeCount) {
        repository.addLikeToPlace(placeId, likeCount);
    }

    public void addLikedPlaceToUser(String placeId) {
        repository.addLikePlaceToUser(placeId);
    }

    public void deleteLikedPlaceFromUser(String placeId) {
        repository.deleteLikePlaceFromUser(placeId);
    }
}
