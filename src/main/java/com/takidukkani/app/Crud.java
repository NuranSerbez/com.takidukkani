package com.takidukkani;

import com.takidukkani.controller.MusteriController;
import com.takidukkani.controller.SiparisController;
import com.takidukkani.controller.UrunController;
import com.takidukkani.entity.Urun;
import com.takidukkani.util.HibernateUtil;
import org.hibernate.Session;

public class Crud {
    public static void main(String[] args) {
// Yeni bir müsteri ekle
        MusteriController musteriController = new MusteriController();
        musteriController.musteriEkle("Melahat", "melahat@gmail.com");
// Yeni bir ürün ekle
        UrunController urunController = new UrunController();
        urunController.urunEkle("Gümüş Kolye", 4000.00);
// Yeni bir sipariş ekle
        SiparisController siparisController = new SiparisController();
        urunController.urunEkle("İnci Kolye", 5000.00);


      //  UrunController urunController = new UrunController();

       // try (Session session = HibernateUtil.getSessionFactory().openSession()) {
           // Urun urun = session.createQuery("FROM Urun ORDER BY id DESC", Urun.class)
                   // .setMaxResults(1)
                   // .getSingleResult();

            //if (urun != null) {
                //System.out.println("Silinecek Sipariş ID: " + urun.getId());
               // urunController.urunSil(urun.getId());
            //} else {
             //   System.out.println("Silinecek sipariş bulunamadı!");
           // }
        //}
    }
}
