package com.takidukkani.controller;

import com.takidukkani.entity.Siparis;
import com.takidukkani.entity.Musteri;
import com.takidukkani.entity.Urun;
import com.takidukkani.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class SiparisController {

    public void siparisEkle(Musteri musteri, Urun urun, LocalDateTime siparisTarihi) {
        Siparis siparis = new Siparis(musteri, urun, siparisTarihi);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(siparis);
                transaction.commit();
                System.out.println("Sipariş başarıyla eklendi!");
            } catch (RuntimeException e) {
                transaction.rollback();
                System.out.println("Sipariş eklenirken bir hata oluştu: " + e.getMessage());
            }
        }
    }

    public void siparisGuncelle(int siparisId, Musteri musteri, Urun urun, LocalDateTime siparisTarihi) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Siparis siparis = session.get(Siparis.class, siparisId);
            if (siparis == null) {
                System.out.println("Böyle bir sipariş bulunamadı!");
                return;
            }

            siparis.setMusteri(musteri);
            siparis.setUrun(urun);
            siparis.setSiparisTarihi(siparisTarihi);

            Transaction transaction = session.beginTransaction();
            try {
                session.update(siparis);  // Güncellenmiş siparişi kaydet
                transaction.commit();
                System.out.println("Sipariş başarıyla güncellendi!");
            } catch (RuntimeException e) {
                transaction.rollback();
                System.out.println("Sipariş güncellenirken bir hata oluştu: " + e.getMessage());
            }
        }
    }

    public void siparisSil(int siparisId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Siparis siparis = session.get(Siparis.class, siparisId);
            if (siparis == null) {
                System.out.println("Böyle bir sipariş bulunamadı!");
                return;
            }

            Transaction transaction = session.beginTransaction();
            try {
                session.delete(siparis);  // Siparişi sil
                transaction.commit();
                System.out.println("Sipariş başarıyla silindi!");
            } catch (RuntimeException e) {
                transaction.rollback();
                System.out.println("Sipariş silinirken bir hata oluştu: " + e.getMessage());
            }
        }
    }
    public void siparisListele() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Siparis> siparisler = session.createQuery("FROM Siparis", Siparis.class).list();

            if (siparisler.isEmpty()) {
                System.out.println("Veritabanında sipariş bulunmamaktadır.");
            } else {
                System.out.println("📋 Sipariş Listesi:");
                for (Siparis siparis : siparisler) {
                    System.out.println("ID: " + siparis.getId() +
                            " | Müşteri: " + siparis.getMusteri().getIsim() +
                            " | Ürün: " + siparis.getUrun().getUrunAdi() +
                            " | Tarih: " + siparis.getSiparisTarihi());
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Siparişler listelenirken bir hata oluştu: " + e.getMessage());
        }
    }
}