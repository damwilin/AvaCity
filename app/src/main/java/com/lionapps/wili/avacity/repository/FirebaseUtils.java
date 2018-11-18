package com.lionapps.wili.avacity.repository;

import android.graphics.Bitmap;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;

import java.io.ByteArrayOutputStream;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

public class FirebaseUtils {
    private static final String USERS = "USERS";
    private static final String PLACES = "PLACES";
    private static final String IMAGES = "IMAGES";


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

    public static void addPlaceCountToUser(FirebaseFirestore firestore, String userId){
        final DocumentReference ref = FirebaseUtils.getUserReference(firestore, userId);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                user.setCountOfPlace(user.getCountOfPlace()+1);
                ref.set(user);
            }
        });
    }

    public static boolean insertPhoto(FirebaseStorage storage, Bitmap bitmap, String placeId){
        final boolean[] isSuccess = new boolean[1];
        isSuccess[0] = false;
        StorageReference ref = storage.getReference().child(IMAGES);
        StorageReference placeRef = ref.child(placeId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = placeRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                isSuccess[0]=true;
            }
        });
        return isSuccess[0];
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
