package com.lionapps.wili.avacity.repository;

import android.graphics.Bitmap;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.lionapps.wili.avacity.interfaces.GetPlaceListener;
import com.lionapps.wili.avacity.interfaces.SearchResultListener;
import com.lionapps.wili.avacity.liveData.PlaceListLiveData;
import com.lionapps.wili.avacity.liveData.UserLiveData;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;

public interface Repository {
    UserLiveData getUserLiveData();
    void insertUser(User user);

    PlaceListLiveData getPlacesLiveData();
    void getPlace(String placeId, GetPlaceListener listener);
    PlaceListLiveData getUserPlacesLiveData();

    void insertPlace(Place place, Bitmap bitmap);
    Task deletePlace(String placeId);

    void addLikeToPlace(String placeId, int countToAdd);

    void addLikePlaceToUser(String placeId);

    void deleteLikePlaceFromUser(String placeId);
    FirebaseUser getCurrUser();

    void decreaseCountOfUserPlaces();

    void searchForPlace(SearchResultListener listener, String query);
}
