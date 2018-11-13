package com.lionapps.wili.avacity.config;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

public class AuthUiConfig {
    private static final List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );
    private AuthUiConfig(){}

    public static List<AuthUI.IdpConfig> getProviders() {
        return providers;
    }
}
