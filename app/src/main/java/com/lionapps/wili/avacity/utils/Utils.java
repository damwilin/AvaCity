package com.lionapps.wili.avacity.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.Random;

public class Utils {
    public static String createPlaceId(LatLng latLng, String userId){
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        int random = rand.nextInt(100);
        sb.append(latLng.latitude);
        sb.append(latLng.longitude);
        sb.append(userId);
        sb.append(random);
        return sb.toString();
    }
}
