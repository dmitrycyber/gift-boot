package com.epam.esm.jpa;

import com.epam.esm.model.entity.GiftCertificateEntity;

import java.util.List;

public interface GiftCertificateRepository /*extends BaseRepository<GiftCertificateEntity, Long>*/ {

    List<GiftCertificateEntity> findAll();
    GiftCertificateEntity findById(Long id);
    GiftCertificateEntity createGift(GiftCertificateEntity giftCertificateEntity);
    GiftCertificateEntity updateGift(GiftCertificateEntity giftCertificateEntity);
    void deleteGift(Long id);

}
