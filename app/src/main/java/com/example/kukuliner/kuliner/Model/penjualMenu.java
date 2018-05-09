package com.example.kukuliner.kuliner.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public class penjualMenu {
    private StorageReference mDatabase;
    private String namaMakanan;
    private String hargaMakanan;
    private String namaToko;



    public penjualMenu(StorageReference mDatabase, String namaMakanan, String hargaMakanan, String namaToko) {

        this.mDatabase = mDatabase;
        this.namaMakanan = namaMakanan;
        this.hargaMakanan = hargaMakanan;
        this.namaToko = namaToko;

    }

    public StorageReference getmDatabase() {
        return mDatabase;
    }

    public void setmDatabase(StorageReference mDatabase) {
        this.mDatabase = mDatabase;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public String getHargaMakanan() {
        return hargaMakanan;
    }

    public void setHargaMakanan(String hargaMakanan) {
        this.hargaMakanan = hargaMakanan;
    }
    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

}
