package com.takidukkani.controller;

import com.takidukkani.entity.Musteri;
import com.takidukkani.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MusteriController {

    public void musteriEkle(String isim, String email) {

        Musteri musteri = new Musteri(isim, email);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(musteri);
                transaction.commit();
                System.out.println("Müşteri başarıyla eklendi!");
            } catch (RuntimeException e) {
                transaction.rollback();
                System.out.println("Müşteri eklenirken bir hata oluştu: " + e.getMessage());
            }
        }
    }

    public void musteriGuncelle(int musteriId, String isim, String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Musteri musteri = session.get(Musteri.class, musteriId);
            if (musteri == null) {
                System.out.println("Böyle bir müşteri bulunamadı!");
                return;
            }

            musteri.setIsim(isim);
            musteri.setEmail(email);

            Transaction transaction = session.beginTransaction();
            try {
                session.update(musteri);
                transaction.commit();
                System.out.println("Müşteri başarıyla güncellendi!");
            } catch (RuntimeException e) {
                transaction.rollback();
                System.out.println("Müşteri güncellenirken bir hata oluştu: " + e.getMessage());
            }
        }
    }

    public void musteriSil(int musteriId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Musteri musteri = session.get(Musteri.class, musteriId);
            if (musteri == null) {
                System.out.println("Böyle bir müşteri bulunamadı!");
                return;
            }

            Transaction transaction = session.beginTransaction();
            try {
                session.delete(musteri);
                transaction.commit();
                System.out.println("Müşteri başarıyla silindi!");
            } catch (RuntimeException e) {
                transaction.rollback();
                System.out.println("Müşteri silinirken bir hata oluştu: " + e.getMessage());
            }
        }
    }
    public void musteriListele() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Musteri> musteriler = session.createQuery("FROM Musteri", Musteri.class).list();

            if (musteriler.isEmpty()) {
                System.out.println("Veritabanında müşteri bulunmamaktadır.");
            } else {
                System.out.println("📋 Müşteri Listesi:");
                for (Musteri musteri : musteriler) {
                    System.out.println("ID: " + musteri.getId() + " | İsim: " + musteri.getIsim() + " | Email: " + musteri.getEmail());
                }
            }
        } catch (RuntimeException e) {
            System.out.println("Müşteriler listelenirken bir hata oluştu: " + e.getMessage());
        }
    }
}