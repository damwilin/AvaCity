package com.lionapps.wili.avacity.repository;

import android.graphics.Bitmap;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class FirebaseUtils {
    private static final String USERS = "USERS";
    private static final String PLACES = "PLACES";
    private static final String IMAGES = "IMAGES";
    private static final String FINDER = "finderId";


    public static CollectionReference getUsersReference(FirebaseFirestore firestore){
        return firestore.collection(USERS);
    }

    public static DocumentReference getUserReference(FirebaseFirestore firestore,String userId){
        return firestore.collection(USERS).document(userId);
    }

    public static CollectionReference getPlacesReference(FirebaseFirestore firestore){
        return firestore.collection(PLACES);
    }

    public static Query getUserPlacesReference(FirebaseFirestore firestore, String userId){
        return firestore.collection(PLACES).whereEqualTo(FINDER, userId);
    }

    public static DocumentReference getPlaceReference(FirebaseFirestore firestore, String placeId){
        return firestore.collection(PLACES).document(placeId);
    }

    public static void insertUser(final FirebaseFirestore firestore, final User user){
        final DocumentReference ref = FirebaseUtils.getUserReference(firestore,user.getUserId());
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (!documentSnapshot.exists())
                    ref.set(user);
            }
        });
    }

    public static void insertPlace(FirebaseFirestore firestore, final Place place){
        final DocumentReference ref = FirebaseUtils.getPlaceReference(firestore, place.getPlaceId());
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (!documentSnapshot.exists())
                    ref.set(place);
            }
        });

    }

    public static void addPlaceToUser(FirebaseFirestore firestore, String userId, final String placeId){
        final DocumentReference ref = FirebaseUtils.getUserReference(firestore, userId);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                List<String> userPlaces = user.getPlaces();
                userPlaces.add(placeId);
                user.setPlaces(userPlaces);
                user.setCountOfPlace(userPlaces.size());
                ref.set(user);
            }
        });
    }

    public static void addPlaceIdToUser(FirebaseFirestore firestore, String userId, String placeId){
        DocumentReference ref = FirebaseUtils.getUserReference(firestore,userId);
        ref.update("places",FieldValue.arrayUnion(placeId));
    }

    public static void insertPhoto(FirebaseStorage storage, Bitmap bitmap, Place place){
        final StorageReference ref = storage.getReference().child(IMAGES);
        final StorageReference placeRef = ref.child(place.getPlaceId());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = placeRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                placeRef.getDownloadUrl();
            }
        });
    }


    public static User getUser(){
        return null;
    }

    public static Task getPlacePhotoTask(FirebaseStorage storage, String placeId){
        final Uri[] photoUri = new Uri[1];
        final StorageReference ref = storage.getReference().child(IMAGES);
        return ref.child(placeId).getDownloadUrl();
    }
}
