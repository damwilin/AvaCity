package com.lionapps.wili.avacity.repository;

import com.google.firebase.auth.FirebaseUser;
import com.lionapps.wili.avacity.liveData.UserLiveData;
import com.lionapps.wili.avacity.models.User;

public interface Repository {
    UserLiveData getUserLiveData(String userId);

    void insertUser(User user);
}
