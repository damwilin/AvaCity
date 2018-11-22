package com.lionapps.wili.avacity.repository;

import android.graphics.Bitmap;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.lionapps.wili.avacity.liveData.PlaceListLiveData;
import com.lionapps.wili.avacity.liveData.UserLiveData;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;

public class FirebaseRepository implements Repository {
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private FirebaseAuth auth;
    private static FirebaseRepository instance;


    private FirebaseRepository() {
        this.firestore = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
        this.auth = FirebaseAuth.getInstance();
    }

    public static FirebaseRepository getInstance() {
        if (instance == null)
            instance = new FirebaseRepository();
        return instance;
    }

    @Override
    public UserLiveData getUserLiveData(String userId) {
        DocumentReference ref = FirebaseUtils.getUserReference(firestore, userId);
        return new UserLiveData(ref);
    }

    @Override
    public void insertUser(User user) {
        FirebaseUtils.insertUser(firestore, user);
    }

    @Override
    public PlaceListLiveData getPlacesLiveData() {
        CollectionReference ref = FirebaseUtils.getPlacesReference(firestore);
        return new PlaceListLiveData(ref);
    }

    @Override
    public PlaceListLiveData getUserPlacesLiveData() {
        Query query = FirebaseUtils.getUserPlacesReference(firestore,getCurrUser().getUid());
        return new PlaceListLiveData(query);
    }

    @Override
    public void insertPlace(Place place, Bitmap bitmap) {
            FirebaseUtils.insertPhoto(storage, bitmap, place);
    }

    @Override
    public void addPlaceToUser(String userId, String placeId) {
        FirebaseUtils.addPlaceToUser(firestore, userId, placeId);
    }

    @Override
    public FirebaseUser getCurrUser() {
        return auth.getCurrentUser();
    }
}
