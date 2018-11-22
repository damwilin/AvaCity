package com.lionapps.wili.avacity.models;

import java.util.Comparator;

public class Place implements Comparator<Place>, Comparable<Place> {
    private String title;
    private double lat;
    private double lng;
    private boolean good;
    private int upVote;
    private int downVote;
    private String finderId;
    private String placeId;
    private String photoUrl;

    public Place() {
    }

    public Place(String title, String placeId, double lat, double lng, boolean good, int upVote, int downVote, String finderId, String photoUrl) {
        this.title = title;
        this.placeId = placeId;
        this.lat = lat;
        this.lng = lng;
        this.good = good;
        this.upVote = upVote;
        this.downVote = downVote;
        this.finderId = finderId;
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

    public int getUpVote() {
        return upVote;
    }

    public void setUpVote(int upVote) {
        this.upVote = upVote;
    }

    public int getDownVote() {
        return downVote;
    }

    public void setDownVote(int downVote) {
        this.downVote = downVote;
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

    @Override
    public int compare(Place o1, Place o2) {
        return o1.getPlaceId().compareTo(o2.getPlaceId());
    }

    @Override
    public int compareTo(Place o) {
        return this.getPlaceId().compareTo(o.getPlaceId());
    }
}
