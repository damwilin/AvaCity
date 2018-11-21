package com.lionapps.wili.avacity.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    protected void startMainActivity(){
        Intent mainActivityIntent = new Intent(this, UserDetailsActivity.class);
        startActivity(mainActivityIntent);
    }
    protected void startUserDetailsActivity(){
        Intent userDetailsActivityIntent = new Intent(this, MainActivity.class);
        startActivity(userDetailsActivityIntent);
    }
}
