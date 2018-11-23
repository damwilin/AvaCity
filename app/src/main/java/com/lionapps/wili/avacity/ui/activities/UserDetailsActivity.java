package com.lionapps.wili.avacity.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.adapter.PlacesAdapter;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.ui.fragments.UserFragment;
import com.lionapps.wili.avacity.viewmodel.UserDetailsViewModel;
import com.lionapps.wili.avacity.viewmodel.ViewModelFactory;

import java.util.List;

public class UserDetailsActivity extends AppCompatActivity implements PlacesAdapter.onDeleteButtonClickListener {
    private UserDetailsViewModel viewModel;
    private UserFragment userFragment;
    private PlacesAdapter adapter;

    @BindView(R.id.logout_button)
    MaterialButton logoutButton;
    @BindView(R.id.user_list_view)
    ListView userListView;
    @BindView(R.id.bottom_navigation_menu)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
        ViewModelFactory factory = new ViewModelFactory();
        viewModel = ViewModelProviders.of(this, factory).get(UserDetailsViewModel.class);
        setupBottomNavigationView();
        setupLogoutButton();
        setupListView();
        initFragments();
        displayUserFragment();
    }

    private void initFragments() {
        userFragment = new UserFragment();
    }

    private void displayUserFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.user_container, userFragment)
                .commit();
    }

    private void setupListView() {
        viewModel.getUserPlacesListLiveData().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                if (userListView.getAdapter() == null) {
                    adapter = new PlacesAdapter(getBaseContext(), 0, places);
                    adapter.setOnDeleteButtonClickListener(UserDetailsActivity.this);
                    userListView.setAdapter(adapter);
                } else {
                }

            }
        });
    }

    private void setupLogoutButton() {
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        logoutButton.getBackground().setColorFilter(getColor(R.color.grey), PorterDuff.Mode.DARKEN);
                        //snack("Signed out");
                        startLoginActivity();
                    }
                });
    }

    private void startLoginActivity() {
        Intent loginActivityIntent = new Intent(this, LoginActivity.class);
        loginActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginActivityIntent);
    }

    private void startMainActivity() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    private void setupBottomNavigationView() {
        bottomNavigationView.setSelectedItemId(R.id.account_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map_view:
                        startMainActivity();
                    case R.id.account_view:
                        //Current Activity
                }
                return true;
            }
        });
    }


    @Override
    public void deletePlace(Place place) {
        Log.w("UserDetailsActivity", place.getPlaceId());
        viewModel.deletePlace(place.getPlaceId()).addOnSuccessListener(new OnSuccessListener<Place>() {
            @Override
            public void onSuccess(Place place) {
            adapter.remove(place);
            adapter.notifyDataSetChanged();
                }
        });
    }
}
