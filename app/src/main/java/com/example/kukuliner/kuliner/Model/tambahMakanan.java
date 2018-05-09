package com.example.kukuliner.kuliner.Model;

public class tambahMakanan {
    private String harga;
    private String namaMakanan;
    private String namaToko;

    public tambahMakanan(){

    }
    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getNamaMakanan() {
        return namaMakanan;
    }

    public void setNamaMakanan(String namaMakanan) {
        this.namaMakanan = namaMakanan;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public tambahMakanan(String harga, String namaMakanan, String namaToko) {

        this.harga = harga;
        this.namaMakanan = namaMakanan;
        this.namaToko = namaToko;

    }
}
