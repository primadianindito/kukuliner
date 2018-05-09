package com.example.kukuliner.kuliner.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String name;
    public String username;
    public String password;
    public String email;
    public String noIdentitas;
    public String Alamat;
    public String gender;
    public String noHP;
    public String tanggalLahir;
    public String pertanyaanRahasia;
    public String jawaban;
    public String tipe;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name, String username, String password, String email, String noIdentitas, String alamat, String gender, String noHP, String tanggalLahir, String pertanyaanRahasia, String jawaban, String tipe) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.noIdentitas = noIdentitas;
        Alamat = alamat;
        this.gender = gender;
        this.noHP = noHP;
        this.tanggalLahir = tanggalLahir;
        this.pertanyaanRahasia = pertanyaanRahasia;
        this.jawaban = jawaban;
        this.tipe = tipe;
    }
}