package com.takidukkani.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "siparisler")
public class Siparis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "siparis_tarihi")
    private Date siparisTarihi;

    // İlişkiler: Bir siparişin bir müşteri ve bir ürünü olsun
    @ManyToOne
    @JoinColumn(name = "musteri_id")
    private com.takidukkani.model.Musteri musteri;

    @ManyToOne
    @JoinColumn(name = "urun_id")
    private com.takidukkani.model.Urun urun;

    public Siparis() { }

    public Siparis(com.takidukkani.model.Musteri musteri, com.takidukkani.model.Urun urun, Date siparisTarihi) {
        this.musteri = musteri;
        this.urun = urun;
        this.siparisTarihi = siparisTarihi;
    }

    // Getter ve Setter metotları
    public int getId() { return id; }
    public Date getSiparisTarihi() { return siparisTarihi; }
    public void setSiparisTarihi(Date siparisTarihi) { this.siparisTarihi = siparisTarihi; }
    public Musteri getMusteri() { return musteri; }
    public void setMusteri(Musteri musteri) { this.musteri = musteri; }
    public Urun getUrun() { return urun; }
    public void setUrun(Urun urun) { this.urun = urun; }
    public static void main(String[] args) {

    }
}
