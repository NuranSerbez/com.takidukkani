package com.takidukkani.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "urunler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Urun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "urun_adi", nullable = false)
    private String urunAdi;

    @Column(name = "fiyat", nullable = false)
    private double fiyat;

    public Urun(String urunAdi, double fiyat) {
        this.urunAdi = urunAdi;
        this.fiyat = fiyat;
    }
}
