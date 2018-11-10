package com.lionapps.wili.avacity.repository;

import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public class Repository {
    private static final String TAG = "Repository";
    private FirebaseFirestore db;

    public Repository() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void insertUser(FirebaseUser mUser){
        final Map<String, Object> user= new HashMap<>();
        user.put("name", mUser.getDisplayName());
        user.put("id", mUser.getUid());
        user.put("email", mUser.getEmail());

        db.collection("users")
                .document(mUser.getEmail())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"User added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"User adding error" + e);
                    }
                });
    }
}
