package com.example.kukuliner.kuliner.Model;

import com.google.firebase.storage.StorageReference;

public class userProgress {
    private StorageReference image;
    private String alamat;
    private String hargaMakanan;
    private String jmlMakanan;
    private String namaMakanan;
    private String status;
    private String toko;
    private String pembeli;
    private String key;

    public String getPembeli() {
        return pembeli;
    }

    public void setPembeli(String pembeli) {
        this.pembeli = pembeli;
    }

    public StorageReference getImage() {
        return image;
    }

    public void setImage(StorageReference image) {
        this.image = image;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getHargaMakanan() {
        return hargaMakanan;
    }

    public void setHargaMakanan(String hargaMakanan) {
        this.hargaMakanan = hargaMakanan;
    }

    public String getJmlMakanan() {
        return jmlMakanan;
    }

    public void setJmlMakanan(String jmlMakanan) {
        this.jmlMakanan = jmlMakanan;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToko() {
        return toko;
    }

    public void setToko(String toko) {
        this.toko = toko;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public userProgress(StorageReference image, String alamat, String hargaMakanan, String jmlMakanan, String namaMakanan, String status, String toko, String pembeli, String key) {

        this.image = image;
        this.alamat = alamat;
        this.hargaMakanan = hargaMakanan;
        this.jmlMakanan = jmlMakanan;
        this.namaMakanan = namaMakanan;
        this.status = status;
        this.toko = toko;
        this.pembeli = pembeli;
        this.key =key;
    }


}

