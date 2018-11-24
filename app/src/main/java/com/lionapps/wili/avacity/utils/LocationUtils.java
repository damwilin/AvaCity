package com.lionapps.wili.avacity.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationUtils {
    private mLocationListener listener;
    private final static int REQUEST_LOCATION_PERMISSION = 9001;
    private LocationManager locationManager;
    private Context context;
    private static final int MIN_TIME = 1000 * 60 * 2;
    private static final int MIN_DISTAMCE = 3;

    public LocationUtils(Context context, mLocationListener listener) {
        this.listener = listener;
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void listenLocationGPS() {
        if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, 3, new LocationListener() {
                @Override
                public void onLocationChanged(android.location.Location location) {
                    listener.onLocationChange(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }
    }

    public interface mLocationListener {
        void onLocationChange(Location location);
    }
/*
    private void getLocation() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if (EasyPermissions.hasPermissions(context, perms)) {
            //TODO GET LOCATION
        } else {
            EasyPermissions.requestPermissions(activity, "Please grant location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }

*/
}
