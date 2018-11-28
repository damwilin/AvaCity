package com.lionapps.wili.avacity.viewmodel;


import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.lionapps.wili.avacity.interfaces.GetPlaceListener;
import com.lionapps.wili.avacity.interfaces.SearchResultListener;
import com.lionapps.wili.avacity.interfaces.UserListener;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;
import com.lionapps.wili.avacity.repository.Repository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel implements UserListener {

    private Repository repository;
    private LatLng clickedLatLng;
    private Bitmap currPlacePhoto;
    private String markerTag;
    private User user;

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

    public void searchForPlace(SearchResultListener listener) {
        repository.searchForPlace(listener);
    }

    public void getPlace(GetPlaceListener listener) {
        repository.getPlace(getMarkerTag(), listener);
    }

    private void updateUser() {
        repository.getUser(getUserId(), this);
    }

    public User getUser() {
        updateUser();
        return user;
    }


    @Override
    public void setUser(User user) {
        this.user = user;

    }

    public void addLikeToPlace(String placeId, int likeCount) {
        repository.addLikeToPlace(placeId, likeCount);
    }

    public void addLikedPlaceToUser(String placeId) {
        repository.addLikePlaceToUser(user.getUserId(), placeId);
        updateUser();
    }

    public void deleteLikedPlaceFromUser(String placeId) {
        repository.deleteLikePlaceFromUser(user.getUserId(), placeId);
        updateUser();
    }
}
