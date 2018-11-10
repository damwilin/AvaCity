package com.lionapps.wili.avacity.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.lionapps.wili.avacity.models.User;

public class Firestore {
    private static final String USERS = "USERS";
    private static final String PLACES = "PLACES";

    private FirebaseFirestore firestore;

    public Firestore() {
        firestore = FirebaseFirestore.getInstance();
    }

    public CollectionReference getUsersReference(FirebaseFirestore firestore){
        return firestore.collection(USERS);
    }

    public DocumentReference getUserReference(FirebaseFirestore firestore, User user){
        return firestore.collection(USERS).document(user.getUserId());
    }

    public CollectionReference getPlacesReference(FirebaseFirestore firestore){
        return firestore.collection(PLACES);
    }

    public DocumentReference getPlacesReference(FirebaseFirestore firestore, String placeId){
        return firestore.collection(PLACES).document(placeId);
    }

    public void setUser(FirebaseFirestore firestore, User user){
        firestore.collection(USERS).document(user.getUserId()).set(user);
    }

    public User getUser(){
        return null;
    }
}
