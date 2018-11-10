package com.lionapps.wili.avacity.ui.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.viewmodel.AccountViewModel;

public class AccountFragment extends Fragment {

    private AccountViewModel mViewModel;

    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @BindView(R.id.account_image_view)
    ImageView accountImageView;
    @BindView(R.id.account_name_text_view)
    TextView accountNameTextView;
    @BindView(R.id.account_rank_text_view)
    TextView accountRankTextView;
    @BindView(R.id.gps_image_view)
    ImageView gpsImageView;
    @BindView(R.id.places_number_text_view)
    TextView placesNumberTextView;
    @BindView(R.id.settings_image_view)
    ImageView settingsImageView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        // TODO: Use the ViewModel
    }

}
