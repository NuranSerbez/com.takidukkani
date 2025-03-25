package com.takidukkani.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "musteriler")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Musteri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "isim", nullable = false)
    private String isim;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    public Musteri(String isim, String email) {
        this.isim = isim;
        this.email = email;
    }
}
