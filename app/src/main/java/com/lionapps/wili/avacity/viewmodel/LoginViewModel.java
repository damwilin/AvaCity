package com.lionapps.wili.avacity.viewmodel;

import android.app.Application;

import com.google.firebase.auth.FirebaseUser;
import com.lionapps.wili.avacity.repository.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LoginViewModel extends AndroidViewModel {
    private Repository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    public void insertUser(FirebaseUser mUser){
        repository.insertUser(mUser);
    }


}
