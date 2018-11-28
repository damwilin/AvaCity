package com.lionapps.wili.avacity.models;

import java.util.Comparator;
import java.util.List;

public class Place implements Comparator<Place>, Comparable<Place> {
    private String title;
    private double lat;
    private double lng;
    private boolean good;
    private int likeCount;
    private String finderId;
    private String placeId;
    private String photoUrl;
    private List<String> tags;

    public Place() {
    }

    public Place(String title, String placeId, double lat, double lng, boolean good, int likeCount, String finderId, List<String> tags, String photoUrl) {
        this.title = title;
        this.placeId = placeId;
        this.lat = lat;
        this.lng = lng;
        this.good = good;
        this.likeCount = likeCount;
        this.finderId = finderId;
        this.tags = tags;
        this.photoUrl = photoUrl;
    }

    public Place(String placeId) {
        this.placeId = placeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getFinderId() {
        return finderId;
    }

    public void setFinderId(String finderId) {
        this.finderId = finderId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public int compare(Place o1, Place o2) {
        return o1.getPlaceId().compareTo(o2.getPlaceId());
    }

    @Override
    public int compareTo(Place o) {
        return this.getPlaceId().compareTo(o.getPlaceId());
    }
}
