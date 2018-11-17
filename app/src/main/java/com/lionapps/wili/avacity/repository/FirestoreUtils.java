package com.lionapps.wili.avacity.repository;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;

import javax.annotation.Nullable;

public class FirestoreUtils {
    private static final String USERS = "USERS";
    private static final String PLACES = "PLACES";


    public static CollectionReference getUsersReference(FirebaseFirestore firestore){
        return firestore.collection(USERS);
    }

    public static DocumentReference getUserReference(FirebaseFirestore firestore,String userId){
        return firestore.collection(USERS).document(userId);
    }

    public static CollectionReference getPlacesReference(FirebaseFirestore firestore){
        return firestore.collection(PLACES);
    }

    public static DocumentReference getPlaceReference(FirebaseFirestore firestore, String placeId){
        return firestore.collection(PLACES).document(placeId);
    }

    public static void insertUser(final FirebaseFirestore firestore, final User user){
        final DocumentReference ref = FirestoreUtils.getUserReference(firestore,user.getUserId());
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (!documentSnapshot.exists())
                    ref.set(user);
            }
        });
    }

    public static void insertPlace(FirebaseFirestore firestore, Place place){
        CollectionReference ref = FirestoreUtils.getPlacesReference(firestore);
        ref.add(place);
    }

    public static User getUser(){
        return null;
    }
}
