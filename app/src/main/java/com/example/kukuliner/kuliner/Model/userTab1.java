package com.example.kukuliner.kuliner.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class userTab1 {
    private StorageReference image;
    private String namaToko, jamBuka,username;
    private int ratting2;



    public userTab1(String namaToko, StorageReference image, String jamBuka, int ratting, String username) {
        this.image = image;
        this.namaToko = namaToko;
        this.jamBuka = jamBuka;
        this.ratting2 = ratting;
        this.username = username;

    }

    public int getRatting() {
        return ratting2;
    }

    public void setRatting(int ratting) {
        this.ratting2 = ratting;
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

