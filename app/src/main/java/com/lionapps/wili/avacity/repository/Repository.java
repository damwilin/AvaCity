package com.lionapps.wili.avacity.repository;

import com.lionapps.wili.avacity.liveData.PlaceListLiveData;
import com.lionapps.wili.avacity.liveData.UserLiveData;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;

import java.util.List;

public interface Repository {
    UserLiveData getUserLiveData(String userId);
    void insertUser(User user);

    PlaceListLiveData getPlacesLiveData();
    void insertPlace(Place place);
}
