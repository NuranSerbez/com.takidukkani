package com.takidukkani.model;

import jakarta.persistence.*;

@Entity
@Table(name = "urunler")
public class Urun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "urun_adi", nullable = false)
    private String urunAdi;

    @Column(name = "fiyat")
    private double fiyat;

    public Urun() { }

    public Urun(String urunAdi, double fiyat) {
        this.urunAdi = urunAdi;
        this.fiyat = fiyat;
    }

    // Getter ve Setter metotları
    public int getId() { return id; }
    public String getUrunAdi() { return urunAdi; }
    public void setUrunAdi(String urunAdi) { this.urunAdi = urunAdi; }
    public double getFiyat() { return fiyat; }
    public void setFiyat(double fiyat) { this.fiyat = fiyat; }
    public static void main(String[] args) {

    }
}

