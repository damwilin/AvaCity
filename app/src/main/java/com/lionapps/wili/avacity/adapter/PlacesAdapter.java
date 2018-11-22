package com.lionapps.wili.avacity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.models.Place;
import com.lionapps.wili.avacity.models.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PlacesAdapter extends ArrayAdapter<Place> {
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Place place = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        //TODO FindView
        TextView title = convertView.findViewById(R.id.title_text_view);
        title.setText(place.getTitle());
        //ToDo attach models to place
        return convertView;
    }

    public PlacesAdapter(@NonNull Context context, int resource, List<Place> places) {
        super(context, resource, places);


    }

}
