package com.lionapps.wili.avacity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.interfaces.OnItemClickListener;
import com.lionapps.wili.avacity.models.Place;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PlacesAdapter extends ArrayAdapter<Place> {
    private OnItemClickListener listener;
    private List<Place> places;

    private onDeleteButtonClickListener onDeleteButtonClickListener;

    public PlacesAdapter(@NonNull Context context, List<Place> places, OnItemClickListener listener) {
        super(context, 0, places);
        this.places = places;
        this.listener = listener;
    }

    public void updateData(List<Place> newList) {
        places = newList;
        notifyDataSetChanged();

    }

    public void setOnDeleteButtonClickListener(PlacesAdapter.onDeleteButtonClickListener onDeleteButtonClickListener) {
        this.onDeleteButtonClickListener = onDeleteButtonClickListener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Place place = places.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clicked(place);
            }
        });
        TextView title = convertView.findViewById(R.id.title_text_view);
        TextView likeCount = convertView.findViewById(R.id.like_count_text_view);
        ImageButton deleteButton = convertView.findViewById(R.id.delete_button);
        title.setText(place.getTitle());
        likeCount.setText(String.valueOf(place.getLikeCount()));
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteButtonClickListener.deletePlace(place);
                remove(place);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    public interface onDeleteButtonClickListener {
        void deletePlace(Place place);
    }
}
