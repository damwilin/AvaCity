package com.lionapps.wili.avacity.viewmodel;

import com.lionapps.wili.avacity.repository.FirestoreRepository;
import com.lionapps.wili.avacity.repository.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class))
            return (T) new  MainViewModel(repository);
        else
            throw new IllegalArgumentException("ViewModel Not Found");
    }
}
