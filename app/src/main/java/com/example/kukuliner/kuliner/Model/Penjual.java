package com.example.kukuliner.kuliner.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Penjual {

    public String name;
    public String password;
    public String email;
    public String Alamat;
    public String noHP;
    public String tipe;
    public String aktivasi;
    public int ratting;
    public int voted;
    public double longitude;
    public double latitude;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)

    public Penjual(String name, String password, String email, String Alamat, String noHP, String tipe, String aktivasi, int ratting, int voted, double longitude, double latitude) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.Alamat = Alamat;
        this.noHP = noHP;
        this.tipe = tipe;
        this.aktivasi = aktivasi;
        this.ratting = ratting;
        this.voted = voted;
        this.longitude = longitude;
        this.latitude = latitude;
    }


}