package com.lionapps.wili.avacity.viewmodel;

import com.lionapps.wili.avacity.repository.Repository;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private Repository repository;

    public MainViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData getUserLiveData(String userId) {
        return repository.getUserLiveData(userId);
    }
}
