package com.lionapps.wili.avacity.utils;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lionapps.wili.avacity.models.Place;

public class MapUtils {
    public static MarkerOptions createMarkerFromPlace(Place place){
        float markerColor;
        LatLng markerPosition = new LatLng(place.getLat(),place.getLng());
        if (place.isGood())
            markerColor = BitmapDescriptorFactory.HUE_GREEN;
        else
            markerColor = BitmapDescriptorFactory.HUE_RED;

        return new MarkerOptions()
                .title(place.getTitle())
                .position(markerPosition)
                .snippet(place.getPlaceId())
                .icon(BitmapDescriptorFactory.defaultMarker(markerColor));
    }
}
