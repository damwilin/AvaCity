package com.lionapps.wili.avacity.liveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.lionapps.wili.avacity.models.User;

import javax.annotation.Nullable;

import androidx.lifecycle.LiveData;

public class UserLiveData extends LiveData<User> implements EventListener<DocumentSnapshot> {
    private DocumentReference documentReference;
    private ListenerRegistration listenerRegistration;

    public UserLiveData(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    @Override
    protected void onActive() {
        super.onActive();
        listenerRegistration = documentReference.addSnapshotListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        listenerRegistration.remove();
    }


    @Override
    public void onEvent(@Nullable DocumentSnapshot docSnap, @Nullable FirebaseFirestoreException e) {
        if (docSnap != null && docSnap.exists()) {
            User userModel = docSnap.toObject(User.class);
            setValue(userModel);
        }
    }
}
