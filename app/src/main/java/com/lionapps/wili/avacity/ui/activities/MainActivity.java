package com.lionapps.wili.avacity.ui.activities;

import android.os.Bundle;

import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.repository.FirestoreRepository;
import com.lionapps.wili.avacity.repository.Repository;
import com.lionapps.wili.avacity.ui.fragments.AccountFragment;
import com.lionapps.wili.avacity.ui.fragments.MapFragment;
import com.lionapps.wili.avacity.viewmodel.MainViewModel;
import com.lionapps.wili.avacity.viewmodel.ViewModelFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
private FragmentManager fragmentManager;
private AccountFragment accountFragment;
private MapFragment mapFragment;

public MainViewModel viewModel;
private Repository repository;

@BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        repository = new FirestoreRepository();
        ViewModelFactory factory = new ViewModelFactory(repository);
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        //slidingUpPanelLayout.setAnchorPoint(100);
        initializeFragments();
        displayAccount();
        displayMap();
    }

    private void initializeFragments() {
        accountFragment = new AccountFragment();
        mapFragment = new MapFragment();
        fragmentManager = getSupportFragmentManager();
    }

    private void displayAccount(){
        fragmentManager.beginTransaction()
                .add(R.id.account_container, accountFragment)
                .commit();
    }

    private void displayMap(){
        fragmentManager.beginTransaction()
                .add(R.id.map_container, mapFragment)
                .commit();
    }
}
