package com.example.kukuliner.kuliner.Model;

import com.google.firebase.storage.StorageReference;

public class adminTab1 {
    private StorageReference img;
    private String name;
    private String username;
    private String email;
    private String noHP;
    private String alamat;

    public StorageReference getImg() {
        return img;
    }

    public void setImg(StorageReference img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public adminTab1(StorageReference img, String name, String username, String email, String noHP, String alamat) {

        this.img = img;
        this.name = name;
        this.username = username;
        this.email = email;
        this.noHP = noHP;
        this.alamat = alamat;
    }
}
