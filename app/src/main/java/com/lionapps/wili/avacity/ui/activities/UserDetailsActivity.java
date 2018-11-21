package com.lionapps.wili.avacity.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.viewmodel.UserDetailsViewModel;
import com.lionapps.wili.avacity.viewmodel.ViewModelFactory;

public class UserDetailsActivity extends AppCompatActivity {
    private UserDetailsViewModel viewModel;

    @BindView(R.id.logout_button)
    MaterialButton logoutButton;
    @BindView(R.id.user_place_list)
    RecyclerView userPlaceList;
    @BindView(R.id.bottom_navigation_menu)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
        setupBottomNavigationView();
        ViewModelFactory factory = new ViewModelFactory();
        viewModel = ViewModelProviders.of(this, factory).get(UserDetailsViewModel.class);
        setupLogoutButton();
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
