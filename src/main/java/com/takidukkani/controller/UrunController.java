package com.takidukkani.controller;

import com.takidukkani.entity.Urun;
import com.takidukkani.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UrunController {

    public void urunEkle(String urunAdi, double fiyat) {
        Urun urun = new Urun(urunAdi, fiyat);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(urun);  // Yeni ürünü kaydet
                transaction.commit();
                System.out.println("Ürün başarıyla eklendi!");
            } catch (RuntimeException e) {
                transaction.rollback();
                System.out.println("Ürün eklenirken bir hata oluştu: " + e.getMessage());
            }
        }
    }

    public void urunGuncelle(int urunId, String urunAdi, double fiyat) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Urun urun = session.get(Urun.class, urunId);
            if (urun == null) {
                System.out.println("Böyle bir ürün bulunamadı!");
                return;
            }

            urun.setUrunAdi(urunAdi);
            urun.setFiyat(fiyat);

            Transaction transaction = session.beginTransaction();
            try {
                session.update(urun);
                transaction.commit();
                System.out.println("Ürün başarıyla güncellendi!");
            } catch (RuntimeException e) {
                transaction.rollback();
                System.out.println("Ürün güncellenirken bir hata oluştu: " + e.getMessage());
            }
        }
    }

    public void urunSil(int urunId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Urun urun = session.get(Urun.class, urunId);
            if (urun == null) {
                System.out.println("Böyle bir ürün bulunamadı!");
                return;
            }

            Transaction transaction = session.beginTransaction();
            try {
                session.delete(urun);
                transaction.commit();
                System.out.println("Ürün başarıyla silindi!");
            } catch (RuntimeException e) {
                transaction.rollback();
                System.out.println("Ürün silinirken bir hata oluştu: " + e.getMessage());
            }
        }
    }
    public void urunListele() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Urun> urunler = session.createQuery("FROM Urun", Urun.class).list();

            if (urunler.isEmpty()) {
                System.out.println("Veritabanında ürün bulunmamaktadır.");
            } else {
                System.out.println("📋 Ürün Listesi:");
                for (Urun urun : urunler) {
                    System.out.println("ID: " + urun.getId() +
                            " | Ürün Adı: " + urun.getUrunAdi() +
                            " | Fiyat: " + urun.getFiyat() + "₺");
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Ürünler listelenirken bir hata oluştu: " + e.getMessage());
        }
    }
}
