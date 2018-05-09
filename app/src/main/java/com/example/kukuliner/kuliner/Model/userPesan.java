package com.example.kukuliner.kuliner.Model;

import com.google.firebase.storage.StorageReference;

public class userPesan {
    private StorageReference image;
    private String NamaMakanan,HargaMakanan,namaToko,pembeli;
    private int jml;

    public StorageReference getImage() {
        return image;
    }

    public void setImage(StorageReference image) {
        this.image = image;
    }

    public String getNamaMakanan() {
        return NamaMakanan;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public void setNamaMakanan(String namaMakanan) {
        NamaMakanan = namaMakanan;
    }

    public String getHargaMakanan() {
        return HargaMakanan;
    }

    public void setHargaMakanan(String hargaMakanan) {
        HargaMakanan = hargaMakanan;
    }

    public int getJml() {
        return jml;
    }

    public void setJml(int jml) {
        this.jml = jml;
    }

    public userPesan(StorageReference image, String namaMakanan, String hargaMakanan, int jml, String namaToko) {

        this.image = image;
        this.NamaMakanan = namaMakanan;
        this.HargaMakanan = hargaMakanan;
        this.jml = jml;
        this.namaToko = namaToko;
        this.pembeli = pembeli;
    }
}
