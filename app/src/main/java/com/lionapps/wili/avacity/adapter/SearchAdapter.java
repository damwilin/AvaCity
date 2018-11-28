package com.lionapps.wili.avacity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lionapps.wili.avacity.R;
import com.lionapps.wili.avacity.interfaces.OnItemClickListener;
import com.lionapps.wili.avacity.models.Place;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchAdapter extends ArrayAdapter<Place> {
    private OnItemClickListener listener;

    public SearchAdapter(@NonNull Context context, OnItemClickListener listener) {
        super(context, 0);
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Place place = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_list_item, parent, false);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.clicked(place);
            }
        });
        TextView title = convertView.findViewById(R.id.title_list_item);
        title.setText(place.getTitle());
        return convertView;
    }
}
