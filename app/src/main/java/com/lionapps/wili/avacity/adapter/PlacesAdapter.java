package com.lionapps.wili.avacity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.models.Place;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class PlacesAdapter extends ArrayAdapter<Place> {
    private List<Place> places;

    private onDeleteButtonClickListener onDeleteButtonClickListener;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Place place = places.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView title = convertView.findViewById(R.id.title_text_view);
        TextView upvote = convertView.findViewById(R.id.upvote_text_view);
        TextView downvote = convertView.findViewById(R.id.downvote_text_view);
        ImageButton deleteButton = convertView.findViewById(R.id.delete_button);
        title.setText(place.getTitle());
        upvote.setText(String.valueOf(place.getUpVote()));
        downvote.setText(String.valueOf(place.getDownVote()));
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

    public void updateData(List<Place> newList) {
        places = newList;
        notifyDataSetChanged();

    }

    public void setOnDeleteButtonClickListener(PlacesAdapter.onDeleteButtonClickListener onDeleteButtonClickListener) {
        this.onDeleteButtonClickListener = onDeleteButtonClickListener;
    }

    public PlacesAdapter(@NonNull Context context, int resource, List<Place> places) {
        super(context, resource, places);
        this.places = places;
    }

    public interface onDeleteButtonClickListener {
        void deletePlace(Place place);
    }
}
