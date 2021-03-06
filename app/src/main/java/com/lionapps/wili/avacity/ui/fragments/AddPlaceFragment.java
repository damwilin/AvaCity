package com.lionapps.wili.avacity.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.utils.Utils;
import com.lionapps.wili.avacity.viewmodel.MainViewModel;
import com.squareup.picasso.Picasso;
import com.suke.widget.SwitchButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;


public class AddPlaceFragment extends Fragment {

    private MainViewModel viewModel;

    @BindView(R.id.title_edit_text)
    EditText titleEditText;
    @BindView(R.id.upload_button)
    MaterialButton uploadButton;
    @BindView(R.id.switch_button)
    SwitchButton switchButton;
    @BindView(R.id.upload_image_view)
    ImageView uploadImageView;
    @BindView(R.id.tags_edit_text)
    EditText tagsEditText;
    private static final String SWITCH_BUTTON_KEY = "SB_KEY";

    OnUploadClickListener mCallback;

    private static final int REQUEST_IMAGE_CAPTURE = 3001;
    @BindView(R.id.delete_photo_image_button)
    ImageButton deletePhotoButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_place, container, false);
        ButterKnife.bind(this, view);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPlace();
            }
        });
        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        deletePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePhoto();
            }
        });
        return view;
    }

    private void deletePhoto() {
        if (viewModel.getCurrPlacePhoto() != null) {
            Picasso.get()
                    .load(R.drawable.ic_photo_camera)
                    .into(uploadImageView);
            viewModel.setCurrPlacePhoto(null);
        } else {
            Snackbar.make(getActivity().findViewById(R.id.coordinator), getString(R.string.error_no_photo), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        setUploadImageView();
    }

    private void insertPlace() {
        LatLng latLng = viewModel.getClickedLatLng();
        Place place = new Place();
        place.setTitle(titleEditText.getText().toString());
        place.setPlaceId(Utils.createPlaceId(latLng, viewModel.getUserId()));
        place.setLat(latLng.latitude);
        place.setLng(latLng.longitude);
        place.setGood(!switchButton.isChecked());
        place.setFinderId(viewModel.getUserId());
        place.setLikeCount(0);
        List<String> tags = Utils.parseTags(tagsEditText.getText().toString());
        place.setTags(tags);
        if (place.getTitle() != null && !place.getTitle().equals("")) {
            viewModel.insertPlace(place);
            Snackbar.make(getActivity().findViewById(R.id.coordinator), getString(R.string.success_insert_place), Snackbar.LENGTH_LONG).show();
            mCallback.onUploadClick();
        } else
            Snackbar.make(getActivity().findViewById(R.id.coordinator), getString(R.string.error_no_title), Snackbar.LENGTH_SHORT).show();
    }

    public void setmCallback(OnUploadClickListener mCallback) {
        this.mCallback = mCallback;
    }

    public interface OnUploadClickListener {
        void onUploadClick();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get(getString(R.string.bundle_bitmap));
            viewModel.setCurrPlacePhoto(imageBitmap);
            setUploadImageView();
        }

    }

    private void setUploadImageView() {
        Bitmap image = viewModel.getCurrPlacePhoto();
        if (image != null) {
            uploadImageView.setImageBitmap(image);
            uploadImageView.setCropToPadding(true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SWITCH_BUTTON_KEY, switchButton.isChecked());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            boolean switchButtonState = savedInstanceState.getBoolean(SWITCH_BUTTON_KEY);
            switchButton.setChecked(switchButtonState);
        }
    }
}
