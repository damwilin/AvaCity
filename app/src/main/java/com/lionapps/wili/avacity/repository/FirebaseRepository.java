package com.lionapps.wili.avacity.repository;

import android.graphics.Bitmap;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.lionapps.wili.avacity.interfaces.GetPlaceListener;
import com.lionapps.wili.avacity.interfaces.PhotoInsertListener;
import com.lionapps.wili.avacity.interfaces.PlaceInsertListener;
import com.lionapps.wili.avacity.interfaces.SearchResultListener;
import com.lionapps.wili.avacity.liveData.PlaceListLiveData;
import com.lionapps.wili.avacity.liveData.UserLiveData;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;
import com.lionapps.wili.avacity.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

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
    public UserLiveData getUserLiveData() {
        DocumentReference ref = FirebaseUtils.getUserReference(firestore, getCurrUser().getUid());
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
        Query query = FirebaseUtils.getUserPlacesReference(firestore, getCurrUser().getUid());
        return new PlaceListLiveData(query);
    }

    @Override
    public void insertPlace(final Place place, final Bitmap bitmap) {
        FirebaseUtils.insertPlace(firestore, place, new PlaceInsertListener() {
            @Override
            public void placeInsertSuccess() {
                FirebaseUtils.updateUserCountOfPlace(firestore, getCurrUser().getUid(), 1);
                if (bitmap != null) {
                    FirebaseUtils.insertPhoto(storage, bitmap, place, new PhotoInsertListener() {
                        @Override
                        public void photoInsertSuccess(String photoUrl) {
                            FirebaseUtils.setPhotoUrlToPlace(firestore, place.getPlaceId(), photoUrl);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void addLikeToPlace(String placeId, int countToAdd) {
        FirebaseUtils.addLikeToPlace(firestore, placeId, countToAdd);
    }

    @Override
    public FirebaseUser getCurrUser() {
        return auth.getCurrentUser();
    }

    @Override
    public Task deletePlace(String placeId) {
        return FirebaseUtils.getPlaceReference(firestore, placeId).delete();
    }

    @Override
    public void searchForPlace(final SearchResultListener listener, final String query) {
        FirebaseUtils.getPlacesReference(firestore).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                List<Place> result = new ArrayList<>();
                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot snapshot : documentSnapshots) {
                    Place curPlace = snapshot.toObject(Place.class);
                    if (Utils.isPlaceContainsTags(curPlace, query))
                        result.add(curPlace);
                }
                listener.successGettingSearchData(result);
            }
        });
    }

    @Override
    public void getPlace(String placeId, final GetPlaceListener listener) {
        FirebaseUtils.getPlaceReference(firestore, placeId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                listener.succcessGettingPlace(documentSnapshot.toObject(Place.class));
            }
        });
    }

    @Override
    public void addLikePlaceToUser(String placeId) {
        FirebaseUtils.addLikePlaceToUser(firestore, getCurrUser().getUid(), placeId);
    }

    @Override
    public void deleteLikePlaceFromUser(String placeId) {
        FirebaseUtils.deleteLikedPlaceFromUser(firestore, getCurrUser().getUid(), placeId);
    }

    @Override
    public void decreaseCountOfUserPlaces() {
        FirebaseUtils.updateUserCountOfPlace(firestore, getCurrUser().getUid(), -1);
    }
}
