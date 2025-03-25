package com.takidukkani.app;

import com.takidukkani.controller.MusteriController;
import com.takidukkani.entity.Musteri;
import com.takidukkani.entity.Siparis;
import com.takidukkani.entity.Urun;
import com.takidukkani.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n--- Takı Dükkanı Uygulaması ---");
                System.out.println("1. Ürün Ekle");
                System.out.println("2. Müşteri Ekle");
                System.out.println("3. Sipariş Oluştur");
                System.out.println("4. Ürünleri Listele");
                System.out.println("5. Müşterileri Listele");
                System.out.println("6. Siparişleri Listele");
                System.out.println("0. Çıkış");
                System.out.print("Seçiminiz: ");

                int secim = getIntInput(scanner);
                if (secim == -1) continue;

                switch (secim) {
                    case 1 -> urunEkle(scanner);
                    case 2 -> musteriEkle(scanner);
                    case 3 -> siparisOlustur(scanner);
                    case 4 -> urunListele();
                    case 5 -> musteriListele();
                    case 6 -> siparisListele();
                    case 0 -> {
                        System.out.println("Çıkılıyor...");
                        return;
                    }
                    default -> System.out.println("Yanlış seçim! Lütfen tekrar deneyin.");
                }
            }
        }
    }

    private static void urunEkle(Scanner scanner) {
        System.out.print("Ürün Adı: ");
        String ad = scanner.nextLine().trim();
        if (ad.isEmpty()) {
            System.out.println("Ürün adı boş olamaz!");
            return;
        }

        System.out.print("Fiyat: ");
        double fiyat;
        try {
            fiyat = Double.parseDouble(scanner.nextLine());
            if (fiyat <= 0) {
                System.out.println("Fiyat 0 veya negatif olamaz!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Lütfen geçerli bir fiyat giriniz!");
            return;
        }

        Urun urun = new Urun(ad, fiyat);
        executeInsideTransaction(session -> session.persist(urun));
        System.out.println("Ürün başarıyla eklendi!");
    }

    private static void musteriEkle(Scanner scanner) {
        System.out.print("Müşteri İsmi: ");
        String isim = scanner.nextLine().trim();
        if (isim.isEmpty()) {
            System.out.println("Müşteri ismi boş olamaz!");
            return;
        }

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty() || !email.contains("@")) {
            System.out.println("Lütfen geçerli bir e-posta adresi giriniz!");
            return;
        }

        Musteri musteri = new Musteri(isim, email);
        executeInsideTransaction(session -> session.persist(musteri));
        System.out.println("Müşteri başarıyla eklendi!");
    }

    private static void siparisOlustur(Scanner scanner) {
        System.out.print("Müşteri ID: ");
        int musteriId = getIntInput(scanner);
        System.out.print("Ürün ID: ");
        int urunId = getIntInput(scanner);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Musteri musteri = session.get(Musteri.class, musteriId);
            Urun urun = session.get(Urun.class, urunId);

            if (musteri == null || urun == null) {
                System.out.println("Geçersiz müşteri veya ürün ID'si!");
                return;
            }

            Siparis siparis = new Siparis(musteri, urun, LocalDateTime.now());
            executeInsideTransaction(session1 -> session1.persist(siparis));
            System.out.println("Sipariş başarıyla oluşturuldu!");
        }
    }

    private static void urunListele() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Urun> urunler = session.createQuery("from Urun", Urun.class).list();
            if (urunler.isEmpty()) {
                System.out.println("Henüz eklenmiş ürün bulunmuyor.");
                return;
            }
            System.out.println("Ürünler:");
            urunler.forEach(u -> System.out.println("ID: " + u.getId() + ", Ad: " + u.getUrunAdi() + ", Fiyat: " + u.getFiyat()));
        }
    }

    private static void musteriListele() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Musteri> musteriler = session.createQuery("from Musteri", Musteri.class).list();
            if (musteriler.isEmpty()) {
                System.out.println("Henüz eklenmiş müşteri bulunmuyor.");
                return;
            }
            System.out.println("Müşteriler:");
            musteriler.forEach(m -> System.out.println("ID: " + m.getId() + ", İsim: " + m.getIsim() + ", Email: " + m.getEmail()));
        }
    }

    private static void siparisListele() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Siparis> siparisler = session.createQuery("from Siparis", Siparis.class).list();
            if (siparisler.isEmpty()) {
                System.out.println("Henüz eklenmiş sipariş bulunmuyor.");
                return;
            }
            System.out.println("Siparişler:");
            siparisler.forEach(s -> System.out.println("ID: " + s.getId() +
                    ", Müşteri: " + s.getMusteri().getIsim() +
                    ", Ürün: " + s.getUrun().getUrunAdi() +
                    ", Tarih: " + s.getSiparisTarihi()));
        }
    }

    private static void executeInsideTransaction(java.util.function.Consumer<Session> action) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                action.accept(session);
                tx.commit();
            } catch (RuntimeException e) {
                tx.rollback();
                e.printStackTrace();
            }
        }
    }

    private static int getIntInput(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Geçerli bir sayı giriniz!");
            return -1;
        }
    }
}
