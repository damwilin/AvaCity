package com.lionapps.wili.avacity.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
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
import com.lionapps.wili.avacity.viewmodel.UserDetailsViewModel;
import com.lionapps.wili.avacity.viewmodel.ViewModelFactory;

import java.util.List;

public class UserDetailsActivity extends AppCompatActivity {
    private UserDetailsViewModel viewModel;

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
        setupBottomNavigationView();
        setupLogoutButton();
        ViewModelFactory factory = new ViewModelFactory();
        viewModel = ViewModelProviders.of(this, factory).get(UserDetailsViewModel.class);
        setupListView();
    }


    private void setupListView(){
        viewModel.getUserPlacesListLiveData().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                PlacesAdapter adapter = new PlacesAdapter(getBaseContext(),0, places);
                userListView.setAdapter(adapter);
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
}
