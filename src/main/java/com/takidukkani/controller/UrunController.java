package com.takidukkani.controller;

import com.takidukkani.entity.Urun;
import com.takidukkani.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
}
