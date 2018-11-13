package com.lionapps.wili.avacity.repository;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.model.Document;
import com.lionapps.wili.avacity.liveData.UserLiveData;
import com.lionapps.wili.avacity.models.User;

import javax.annotation.Nullable;

public class FirestoreRepository implements Repository {
    private static final String TAG = "FirestoreRepository";
    private FirebaseFirestore firestore;

    public FirestoreRepository() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public UserLiveData getUserLiveData(String userId) {
        DocumentReference ref = FirestoreUtils.getUserReference(firestore, userId);
        return new UserLiveData(ref);
    }

    @Override
    public void insertUser(User user) {
        FirestoreUtils.insertUser(firestore,user);
    }
}
