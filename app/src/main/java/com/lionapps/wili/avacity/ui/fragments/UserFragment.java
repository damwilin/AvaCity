package com.lionapps.wili.avacity.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.models.User;
import com.lionapps.wili.avacity.viewmodel.UserDetailsViewModel;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment {

    private UserDetailsViewModel viewModel;

    @BindView(R.id.account_image_view)
    CircleImageView accountImageView;
    @BindView(R.id.account_name_text_view)
    TextView accountNameTextView;
    @BindView(R.id.places_number_text_view)
    TextView placesNumberTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(UserDetailsViewModel.class);
        // TODO: Use the ViewModel
        viewModel.getUserLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                accountNameTextView.setText(user.getName());
                placesNumberTextView.setText(String.valueOf(user.getCountOfPlace()));
                Picasso.get()
                        .load(user.getPhotoUrl())
                        .into(accountImageView);
            }
        });
    }

}
