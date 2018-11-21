package com.lionapps.wili.avacity.viewmodel;

import com.google.firebase.auth.FirebaseUser;
import com.lionapps.wili.avacity.repository.Repository;

import androidx.lifecycle.ViewModel;

public class UserDetailsViewModel extends ViewModel {
    private Repository repository;

    public UserDetailsViewModel(Repository repository) {
        this.repository = repository;
    }

    public FirebaseUser getUser(){
        return repository.getCurrUser();
    }
}
