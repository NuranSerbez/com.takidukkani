package com.takidukkani;

import com.takidukkani.controller.MusteriController;

public class Crud {
    public static void main(String[] args) {
        MusteriController musteriController = new MusteriController();
        musteriController.musteriEkle("Nuran Serbez", "nuranserbez@gmail.com");
    }
}

