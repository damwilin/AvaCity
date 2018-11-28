package com.lionapps.wili.avacity.utils;

import com.google.android.gms.maps.model.LatLng;
import com.lionapps.wili.avacity.models.Place;

import java.util.Arrays;
import java.util.List;
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

    public static List<String> parseTags(String toParse) {
        if (toParse != null) {
            List<String> parsed = Arrays.asList(toParse.toLowerCase().split("[ ,.]"));
            return parsed;
        }
        return null;
    }

    public static boolean isPlaceContainsTags(Place place, String query) {
        String[] tags = query.toLowerCase().split(" ");
        List<String> placeTags = place.getTags();
        for (String placeTag : placeTags) {
            for (String searchTag : tags) {
                if (placeTag.trim().equals(searchTag))
                    return true;
            }
        }
        return false;
    }
}
