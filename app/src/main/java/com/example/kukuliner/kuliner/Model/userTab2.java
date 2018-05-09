package com.example.kukuliner.kuliner.Model;

import com.google.firebase.storage.StorageReference;

public class userTab2 {
    private StorageReference image;
    private String namaToko, jamBuka,username;
    private int ratting;



    public userTab2(String namaToko, StorageReference image, String jamBuka, int ratting, String username) {
        this.image = image;
        this.namaToko = namaToko;
        this.jamBuka = jamBuka;
        this.ratting = ratting;
        this.username = username;

    }

    public int getRatting() {
        return ratting;
    }

    public void setRatting(int ratting) {
        this.ratting = ratting;
    }

    public StorageReference getImage() {
        return image;
    }

    public void setImage(StorageReference image) {
        this.image = image;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getJamBuka() {
        return jamBuka;
    }

    public void setJamBuka(String jamBuka) {
        this.jamBuka = jamBuka;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}