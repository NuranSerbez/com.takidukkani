package com.takidukkani.app;

import com.takidukkani.controller.MusteriController;
import com.takidukkani.controller.SiparisController;
import com.takidukkani.controller.UrunController;
import com.takidukkani.entity.Musteri;
import com.takidukkani.entity.Urun;
import com.takidukkani.util.HibernateUtil;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static final MusteriController musteriController = new MusteriController();
    private static final UrunController urunController = new UrunController();
    private static final SiparisController siparisController = new SiparisController();

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
                    case 4 -> urunController.urunListele();
                    case 5 -> musteriController.musteriListele();
                    case 6 -> siparisController.siparisListele();
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
        double fiyat = getDoubleInput(scanner);
        if (fiyat <= 0) {
            System.out.println("Fiyat 0 veya negatif olamaz!");
            return;
        }

        urunController.urunEkle(ad, fiyat);
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

        musteriController.musteriEkle(isim, email);
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

            siparisController.siparisEkle(musteri, urun, LocalDateTime.now());
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

    private static double getDoubleInput(Scanner scanner) {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Geçerli bir fiyat giriniz!");
            return -1;
        }
    }
}
