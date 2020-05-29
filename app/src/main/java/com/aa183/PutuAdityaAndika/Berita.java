package com.aa183.PutuAdityaAndika;

import java.util.Date;

public class Berita {

        private int idBerita;
        private String judul;
        private Date tanggal;
        private String gambar;
        private String caption;
        private String penulis;
        private String isiBuku;
        private String link;

    public Berita(int idBerita, String judul, Date tanggal, String gambar, String caption, String penulis, String isiBuku, String link) {
        this.idBerita = idBerita;
        this.judul = judul;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.caption = caption;
        this.penulis = penulis;
        this.isiBuku = isiBuku;
        this.link = link;
    }

    public int getIdBerita() {
        return idBerita;
    }

    public void setIdBerita(int idBerita) {
        this.idBerita = idBerita;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getIsiBerita() {
        return isiBuku;
    }

    public void setIsiBerita(String isiBerita) {
        this.isiBuku = isiBerita;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}



