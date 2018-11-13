package com.lionapps.wili.avacity.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.models.User;
import com.lionapps.wili.avacity.repository.FirestoreRepository;
import com.lionapps.wili.avacity.repository.Repository;
import com.lionapps.wili.avacity.ui.fragments.AccountFragment;
import com.lionapps.wili.avacity.viewmodel.MainViewModel;
import com.lionapps.wili.avacity.viewmodel.ViewModelFactory;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
private FragmentManager fragmentManager;
private AccountFragment accountFragment;

public MainViewModel viewModel;
private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        repository = new FirestoreRepository();
        ViewModelFactory factory = new ViewModelFactory(repository);
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        //slidingUpPanelLayout.setAnchorPoint(100);
        initializeFragment();
        displayAccount();

    }

    private void initializeFragment() {
        accountFragment = new AccountFragment();
        fragmentManager = getSupportFragmentManager();
    }

    private void displayAccount(){
        fragmentManager.beginTransaction()
                .add(R.id.account_container, accountFragment)
                .commit();
    }
}
