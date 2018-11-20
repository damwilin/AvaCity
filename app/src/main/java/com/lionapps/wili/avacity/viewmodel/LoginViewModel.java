package com.lionapps.wili.avacity.viewmodel;


import com.lionapps.wili.avacity.models.User;
import com.lionapps.wili.avacity.repository.Repository;


import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private Repository repository;

    public LoginViewModel(Repository repository) {
        this.repository = repository;
    }

    public void insertUser(User user) {
        repository.insertUser(user);
    }


}
