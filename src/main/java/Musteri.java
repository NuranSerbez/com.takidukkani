package com.takidukkani.model;

import jakarta.persistence.*;

@Entity
@Table(name = "musteriler")
public class Musteri {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "isim", nullable = false)
    private String isim;

    @Column(name = "email")
    private String email;

    public Musteri() { }

    public Musteri(String isim, String email) {
        this.isim = isim;
        this.email = email;
    }

    // Getter ve Setter metotları
    public int getId() { return id; }
    public String getIsim() { return isim; }
    public void setIsim(String isim) { this.isim = isim; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public static void main(String[] args) {

    }
}
