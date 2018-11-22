package com.lionapps.wili.avacity.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.viewmodel.MainViewModel;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceDetailsFragment extends Fragment {

    private MainViewModel viewModel;

    @BindView(R.id.place_image_view)
    ImageView placeImageView;
    @BindView(R.id.isGood_image_view)
    ImageView isGoodImageView;
    @BindView(R.id.place_title_text_view)
    TextView placeTitle;
    @BindView(R.id.upvote_count_text_view)
    TextView upVoteTextView;
    @BindView(R.id.upvote_button)
    Button upVoteButton;
    @BindView(R.id.downvote_count_text_view)
    TextView downVoteTextView;
    @BindView(R.id.downvote_button)
    Button downVoteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_details, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        viewModel.getMarkerTag();
        viewModel.getPlacesListLiveData().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                Place currPlace = null;
                for (Place place : places){
                    if (place.getPlaceId() == viewModel.getMarkerTag())
                        currPlace = place;
                }
                if (currPlace != null) {
                    placeTitle.setText(currPlace.getTitle());
                    if (currPlace.getPhotoUrl() != null)
                            Picasso.get()
                                    .load(currPlace.getPhotoUrl())
                                    .into(placeImageView);
                    upVoteTextView.setText(currPlace.getUpVote());
                    downVoteTextView.setText(currPlace.getDownVote());
                }
            }
        });
    }
}
