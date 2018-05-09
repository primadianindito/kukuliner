package com.example.kukuliner.kuliner.Model;

import com.google.firebase.storage.StorageReference;

public class penjualTab1 {
    private StorageReference image;
    private String namaToko,pesanan,alamat;
    private String harga,status;

    public penjualTab1(String namaToko, StorageReference image, String pesanan, String harga, String status, String alamat) {
        this.image = image;
        this.namaToko = namaToko;
        this.pesanan = pesanan;
        this.harga = harga;
        this.status = status;
        this.alamat = alamat;

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

    public String getPesanan() {
        return pesanan;
    }

    public void setPesanan(String pesanan) {
        this.pesanan = pesanan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
