package com.lionapps.wili.avacity.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.lionapps.wili.avacity.interfaces.SearchResultListener;
import com.lionapps.wili.avacity.liveData.PlaceListLiveData;
import com.lionapps.wili.avacity.liveData.UserLiveData;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;

public interface Repository {
    UserLiveData getUserLiveData();
    void insertUser(User user);

    PlaceListLiveData getPlacesLiveData();
    PlaceListLiveData getUserPlacesLiveData();
    void insertPlace(Place place);
    Task deletePlace(String placeId);

    FirebaseUser getCurrUser();

    void searchForPlace(SearchResultListener listener);

    void addPlaceToUser(String userId, String placeId);
}
