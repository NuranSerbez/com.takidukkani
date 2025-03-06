package com.takidukkani;

import com.takidukkani.model.Musteri;
import com.takidukkani.model.Siparis;
import com.takidukkani.model.Urun;
import com.takidukkani.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
            int secim = Integer.parseInt(scanner.nextLine());

            switch (secim) {
                case 1:
                    urunEkle(scanner);
                    break;
                case 2:
                    musteriEkle(scanner);
                    break;
                case 3:
                    siparisOlustur(scanner);
                    break;
                case 4:
                    urunListele();
                    break;
                case 5:
                    musteriListele();
                    break;
                case 6:
                    siparisListele();
                    break;
                case 0:
                    System.out.println("Çıkılıyor...");
                    System.exit(0);
                default:
                    System.out.println("Yanlış seçim!");
            }
        }
    }

    private static void urunEkle(Scanner scanner) {
        System.out.print("Ürün Adı: ");
        String ad = scanner.nextLine();
        System.out.print("Fiyat: ");
        double fiyat = Double.parseDouble(scanner.nextLine());

        Urun urun = new Urun(ad, fiyat);
        executeInsideTransaction(session -> session.save(urun));
        System.out.println("Ürün eklendi!");
    }

    private static void musteriEkle(Scanner scanner) {
        System.out.print("Müşteri İsmi: ");
        String isim = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Musteri musteri = new Musteri(isim, email);
        executeInsideTransaction(session -> session.save(musteri));
        System.out.println("Müşteri eklendi!");
    }

    private static void siparisOlustur(Scanner scanner) {
        System.out.print("Müşteri ID: ");
        int musteriId = Integer.parseInt(scanner.nextLine());
        System.out.print("Ürün ID: ");
        int urunId = Integer.parseInt(scanner.nextLine());

        Session session = HibernateUtil.getSessionFactory().openSession();
        Musteri musteri = session.get(Musteri.class, musteriId);
        Urun urun = session.get(Urun.class, urunId);
        session.close();

        if(musteri == null || urun == null) {
            System.out.println("Geçersiz müşteri veya ürün ID'si!");
            return;
        }

        Siparis siparis = new Siparis(musteri, urun, new Date());
        executeInsideTransaction(session1 -> session1.save(siparis));
        System.out.println("Sipariş oluşturuldu!");
    }

    private static void urunListele() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Urun> urunler = session.createQuery("from Urun", Urun.class).list();
        session.close();

        System.out.println("Ürünler:");
        for (Urun u : urunler) {
            System.out.println("ID: " + u.getId() + ", Ad: " + u.getUrunAdi() + ", Fiyat: " + u.getFiyat());
        }
    }

    private static void musteriListele() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Musteri> musteriler = session.createQuery("from Musteri", Musteri.class).list();
        session.close();

        System.out.println("Müşteriler:");
        for (Musteri m : musteriler) {
            System.out.println("ID: " + m.getId() + ", İsim: " + m.getIsim() + ", Email: " + m.getEmail());
        }
    }

    private static void siparisListele() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Siparis> siparisler = session.createQuery("from Siparis", Siparis.class).list();
        session.close();

        System.out.println("Siparişler:");
        for (Siparis s : siparisler) {
            System.out.println("ID: " + s.getId() +
                    ", Müşteri: " + s.getMusteri().getIsim() +
                    ", Ürün: " + s.getUrun().getUrunAdi() +
                    ", Tarih: " + s.getSiparisTarihi());
        }
    }

    // İşlemleri tek bir transaction içinde yürüten yardımcı metod
    private static void executeInsideTransaction(java.util.function.Consumer<Session> action) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            action.accept(session);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
