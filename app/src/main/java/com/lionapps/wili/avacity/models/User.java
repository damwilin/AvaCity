package com.lionapps.wili.avacity.models;

import com.google.firebase.auth.FirebaseUser;

public class User {
    private String name;
    private String userId;
    private String emailAdress;
    private String photoUrl;
    private int countOfPlace;

    public User(String name, String userId, String emailAdress, String photoUrl, int countOfPlace) {
        this.name = name;
        this.userId = userId;
        this.emailAdress = emailAdress;
        this.photoUrl = photoUrl;
        this.countOfPlace = countOfPlace;
    }

    public User createFrom(FirebaseUser firebaseUser) {
        this.name = firebaseUser.getDisplayName();
        this.userId = firebaseUser.getUid();
        this.emailAdress = firebaseUser.getEmail();
        if (firebaseUser.getPhotoUrl() != null)
            this.photoUrl = firebaseUser.getPhotoUrl().toString();
        this.countOfPlace = 0;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String uId) {
        this.userId = uId;
    }

    public int getCountOfPlace() {
        return countOfPlace;
    }

    public void setCountOfPlace(int countOfPlace) {
        this.countOfPlace = countOfPlace;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }
}
