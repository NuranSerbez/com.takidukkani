package com.takidukkani.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "siparisler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Siparis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "siparis_tarihi", nullable = false)
    private LocalDateTime siparisTarihi;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "musteri_id", nullable = false)
    private Musteri musteri;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "urun_id", nullable = false)
    private Urun urun;

    public Siparis(Musteri musteri, Urun urun, LocalDateTime siparisTarihi) {
        this.musteri = musteri;
        this.urun = urun;
        this.siparisTarihi = siparisTarihi;
    }
}
