package com.lionapps.wili.avacity.viewmodel;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.repository.Repository;


import androidx.lifecycle.AndroidViewModel;
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

    public LiveData getPlacesListLiveData(){
        return repository.getPlacesLiveData();
    }

    public void insertPlace(Place place){
        repository.insertPlace(place);
    }
}
