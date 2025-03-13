CREATE TABLE urunler
(
    id       INT AUTO_INCREMENT NOT NULL,
    urun_adi VARCHAR(255) NOT NULL,
    fiyat DOUBLE NULL,
    CONSTRAINT pk_urunler PRIMARY KEY (id)
);
CREATE TABLE siparisler
(
    id             INT AUTO_INCREMENT NOT NULL,
    siparis_tarihi datetime           NULL,
    musteri_id     INT                NULL,
    urun_id        INT                NULL,
    CONSTRAINT `pk_sıparısler` PRIMARY KEY (id)
);

ALTER TABLE siparisler
    ADD CONSTRAINT FK_SIPARISLER_ON_MUSTERI FOREIGN KEY (musteri_id) REFERENCES musteriler (id);

ALTER TABLE siparisler
    ADD CONSTRAINT FK_SIPARISLER_ON_URUN FOREIGN KEY (urun_id) REFERENCES urunler (id);
CREATE TABLE musteriler
(
    id    INT AUTO_INCREMENT NOT NULL,
    isim  VARCHAR(255)       NOT NULL,
    email VARCHAR(255)       NULL,
    CONSTRAINT `pk_musterıler` PRIMARY KEY (id)
);