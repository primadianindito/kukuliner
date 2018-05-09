package com.example.kukuliner.kuliner.Model;

import com.google.firebase.storage.StorageReference;

public class penjualTab2 {
    private StorageReference image;
    private String namaToko, pesanan,progress,alamat,harga;

    public penjualTab2(String namaToko, StorageReference image, String pesanan, String harga,String progress, String alamat) {
        this.image = image;
        this.namaToko = namaToko;
        this.pesanan = pesanan;
        this.harga = harga;
        this.progress = progress;
        this.alamat = alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
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
}