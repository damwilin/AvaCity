package com.lionapps.wili.avacity.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.utils.Utils;
import com.lionapps.wili.avacity.viewmodel.MainViewModel;
import com.suke.widget.SwitchButton;


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

    OnUploadClickListener mCallback;

    private static final int REQUEST_IMAGE_CAPTURE = 3001;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_place, container, false);
        ButterKnife.bind(this, view);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPlace();
                Snackbar.make(getActivity().findViewById(R.id.coordinator), "Place inserted", Snackbar.LENGTH_LONG).show();
                mCallback.onUploadClick();
            }
        });
        uploadImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        return view;
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
        place.setUpVote(0);
        place.setDownVote(0);
        viewModel.insertPlace(place);
        //TODO Add onSuccess if tru -> Snackbar Place Inserted, else Failed
    }

    public void setmCallback(OnUploadClickListener mCallback) {
        this.mCallback = mCallback;
    }

    public interface OnUploadClickListener {
        public void onUploadClick();
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
            Bitmap imageBitmap = (Bitmap) extras.get("data");
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


}
