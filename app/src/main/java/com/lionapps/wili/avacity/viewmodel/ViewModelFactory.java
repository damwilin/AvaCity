package com.lionapps.wili.avacity.viewmodel;

import com.lionapps.wili.avacity.repository.FirebaseRepository;
import com.lionapps.wili.avacity.repository.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    public ViewModelFactory() {
        this.repository = FirebaseRepository.getInstance();
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class))
            return (T) new  MainViewModel(repository);
        else if (modelClass.isAssignableFrom(LoginViewModel.class))
            return (T) new LoginViewModel(repository);
        else if (modelClass.isAssignableFrom(UserDetailsViewModel.class))
            return (T) new UserDetailsViewModel(repository);
            throw new IllegalArgumentException("ViewModel Not Found");
    }
}
