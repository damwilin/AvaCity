package com.lionapps.wili.avacity.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.ui.fragments.AccountFragment;
import com.lionapps.wili.avacity.viewmodel.MainViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
private FragmentManager fragmentManager;
private AccountFragment accountFragment;

public MainViewModel mainViewModel;

//TO DELETE
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        initializeFragment();
        displayAccount();

        //TO DELETE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        //mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        final TextView userTextView = findViewById(R.id.user_text_view);
        userTextView.setText(user.getEmail());
        Button logoutButton = findViewById(R.id.logout_main);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mGoogleSignInClient.signOut();
                mAuth.signOut();
                userTextView.setText(user.getEmail());
            }
        });
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
