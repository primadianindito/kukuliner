package com.example.kukuliner.kuliner.Model;

public class pesanan {
    private String namaMakanan;
    private String hargaMakanan;
    private int jmlMakanan;
    private String namaToko;
    private String pembeli;
    private String status;
    private String alamat;

    public pesanan() {

    }

    public pesanan(String namaMakanan, String hargaMakanan, int jmlMakanan,String namaToko,String pembeli,String status, String alamat) {
        this.namaMakanan = namaMakanan;
        this.hargaMakanan = hargaMakanan;
        this.jmlMakanan = jmlMakanan;
        this.namaToko = namaToko;
        this.pembeli = pembeli;
        this.status = status;
        this.alamat = alamat;
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

    public int getJmlMakanan() {
        return jmlMakanan;
    }

    public void setJmlMakanan(int jmlMakanan) {
        this.jmlMakanan = jmlMakanan;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getPembeli() {
        return pembeli;
    }

    public void setPembeli(String pembeli) {
        this.pembeli = pembeli;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
