package com.lionapps.wili.avacity.repository;

import android.graphics.Bitmap;

import com.google.android.gms.tasks.Task;
import com.lionapps.wili.avacity.liveData.PlaceListLiveData;
import com.lionapps.wili.avacity.liveData.UserLiveData;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;

public interface Repository {
    UserLiveData getUserLiveData(String userId);
    void insertUser(User user);

    PlaceListLiveData getPlacesLiveData();
    void insertPlace(Place place, Bitmap bitmap);

    void addPlaceCountToUser(String userId);

    Task getPlacePhotoUri(String placeId);
}
