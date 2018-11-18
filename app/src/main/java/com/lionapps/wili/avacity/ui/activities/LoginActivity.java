package com.lionapps.wili.avacity.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jaeger.library.StatusBarUtil;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.config.AuthUiConfig;
import com.lionapps.wili.avacity.models.User;
import com.lionapps.wili.avacity.viewmodel.LoginViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.login_with_google_button)
    Button loginWithGoogleButton;
    @BindView(R.id.logout_button)
    Button logoutButton;

    private static final String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9308;

    public LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setTranslucent(this,10);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        setLoginWithGoogleButton();

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    setButtonSignedIn();
                    logoutButton.getBackground().clearColorFilter();
                    logoutButton.setBackgroundColor(getColor(R.color.colorPrimary));
                    logoutButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            signOut();
                        }
                    });
                } else {
                    setButtonSignIn();
                    logoutButton.getBackground().setColorFilter(getColor(R.color.grey), PorterDuff.Mode.DARKEN);
                }
            }
        });
    }

    private void startMainActivity() {
        Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    private void setLoginWithGoogleButton() {
        loginWithGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signOut() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            logoutButton.getBackground().setColorFilter(getColor(R.color.grey), PorterDuff.Mode.DARKEN);
                            snack("Signed out");
                        }
                    });
        } else {
            snack("You are not signed in");
        }
    }

    private void signIn() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(AuthUiConfig.getProviders())
                            .build(), RC_SIGN_IN);
        } else
            startMainActivity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                //Successfully signed in
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                viewModel.insertUser(new User(firebaseUser));
                startMainActivity();
                snack("Signed in");
            } else {
                //Sign in failed
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK)
                    snack("No network connection");
                else
                    snack("Error with sign in");
            }
        }
    }

    private void setButtonSignedIn() {
        loginWithGoogleButton.setText("Signed in");
    }

    private void setButtonSignIn() {
        loginWithGoogleButton.setText("Sign in with Google");
    }

    private void snack(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT).show();
    }
    }
