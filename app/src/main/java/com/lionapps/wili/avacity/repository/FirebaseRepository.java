package com.lionapps.wili.avacity.repository;

import android.graphics.Bitmap;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.lionapps.wili.avacity.liveData.PlaceListLiveData;
import com.lionapps.wili.avacity.liveData.UserLiveData;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;

public class FirebaseRepository implements Repository {
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;
    private static FirebaseRepository instance= null;


    private FirebaseRepository() {
        this.firestore = FirebaseFirestore.getInstance();
        this.storage = FirebaseStorage.getInstance();
    }
    public static FirebaseRepository getInstance(){
        if(instance == null)
            return new FirebaseRepository();
        else
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
    public void insertPlace(Place place, Bitmap bitmap) {
        FirebaseUtils.insertPlace(firestore, place);
        if (bitmap != null){
        FirebaseUtils.insertPhoto(storage,bitmap, place.getPlaceId());
        }
    }

    @Override
    public void addPlaceCountToUser(String userId) {
        FirebaseUtils.addPlaceCountToUser(firestore, userId);
    }

    @Override
    public Task getPlacePhotoUri(String placeId) {
        return FirebaseUtils.getPlacePhotoTask(storage,placeId);
    }

}
