package com.lionapps.wili.avacity.repository;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lionapps.wili.avacity.interfaces.PhotoInsertListener;
import com.lionapps.wili.avacity.interfaces.PlaceInsertListener;
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


    public static CollectionReference getUsersReference(FirebaseFirestore firestore) {
        return firestore.collection(USERS);
    }

    public static DocumentReference getUserReference(FirebaseFirestore firestore, String userId) {
        return firestore.collection(USERS).document(userId);
    }

    public static CollectionReference getPlacesReference(FirebaseFirestore firestore) {
        return firestore.collection(PLACES);
    }

    public static Query getUserPlacesReference(FirebaseFirestore firestore, String userId) {
        return firestore.collection(PLACES).whereEqualTo(FINDER, userId);
    }

    public static DocumentReference getPlaceReference(FirebaseFirestore firestore, String placeId) {
        return firestore.collection(PLACES).document(placeId);
    }

    public static void insertUser(final FirebaseFirestore firestore, final User user) {
        final DocumentReference ref = FirebaseUtils.getUserReference(firestore, user.getUserId());
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (!documentSnapshot.exists())
                    ref.set(user);
            }
        });
    }

    public static void insertPlace(FirebaseFirestore firestore, final Place place, final PlaceInsertListener listener) {
        final DocumentReference ref = FirebaseUtils.getPlaceReference(firestore, place.getPlaceId());
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (!documentSnapshot.exists())
                    ref.set(place).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            listener.placeInsertSuccess();
                        }
                    });
            }
        });

    }

    public static void addLikePlaceToUser(FirebaseFirestore firestore, String userId, final String placeId) {
        final DocumentReference ref = FirebaseUtils.getUserReference(firestore, userId);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                List<String> userLikes = user.getLikedPlaces();
                userLikes.add(placeId);
                user.setLikedPlaces(userLikes);
                ref.set(user);
            }
        });
    }

    public static void deleteLikedPlaceFromUser(FirebaseFirestore firestore, String userId, final String placeId) {
        final DocumentReference ref = FirebaseUtils.getUserReference(firestore, userId);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                List<String> userLikes = user.getLikedPlaces();
                userLikes.remove(placeId);
                user.setLikedPlaces(userLikes);
                ref.set(user);
            }
        });
    }

    public static void updateUserCountOfPlace(FirebaseFirestore firestore, String userId, final int countToAdd) {
        final DocumentReference ref = FirebaseUtils.getUserReference(firestore, userId);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = task.getResult().toObject(User.class);
                int userPlaces = user.getCountOfPlace();
                user.setCountOfPlace(userPlaces + countToAdd);
                ref.set(user);
            }
        });
    }

    public static void insertPhoto(FirebaseStorage storage, Bitmap bitmap, Place place, final PhotoInsertListener listener) {
        final StorageReference ref = storage.getReference().child(IMAGES);
        final StorageReference placeRef = ref.child(place.getPlaceId());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = placeRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                placeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.photoInsertSuccess(uri.toString());
                    }
                });
            }
        });
    }

    public static void addLikeToPlace(FirebaseFirestore firestore, String placeId, final int likeCountToAdd) {
        final DocumentReference ref = FirebaseUtils.getPlaceReference(firestore, placeId);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Place place = task.getResult().toObject(Place.class);
                int placeLikes = place.getLikeCount();
                place.setLikeCount(placeLikes + likeCountToAdd);
                ref.set(place);
            }
        });
    }

    public static void setPhotoUrlToPlace(FirebaseFirestore firestore, String placeId, final String photoUrl) {
        final DocumentReference ref = FirebaseUtils.getPlaceReference(firestore, placeId);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Place place = task.getResult().toObject(Place.class);
                place.setPhotoUrl(photoUrl);
                ref.set(place);
            }
        });
    }
}
