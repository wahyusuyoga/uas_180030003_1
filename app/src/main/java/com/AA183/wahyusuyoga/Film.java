package com.AA183.wahyusuyoga;

import java.util.Date;

public class Film {

    private int idFilm;
    private String judul;
    private String gambar;
    private Date tanggal;
    private String sutradara;
    private String sinopsisFilm;
    private String genre;

    public Film(int idFilm, String judul, String gambar, Date tanggal, String sutradara, String sinopsisFilm, String genre) {
        this.idFilm = idFilm;
        this.judul = judul;
        this.gambar = gambar;
        this.tanggal = tanggal;
        this.sutradara = sutradara;
        this.sinopsisFilm = sinopsisFilm;
        this.genre = genre;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getSutradara() {
        return sutradara;
    }

    public void setSutradara(String sutradara) {
        this.sutradara = sutradara;
    }

    public String getSinopsisFilm() {
        return sinopsisFilm;
    }

    public void setSinopsisFilm(String sinopsisFilm) {
        this.sinopsisFilm = sinopsisFilm;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
