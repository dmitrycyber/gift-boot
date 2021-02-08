package com.epam.esm.jpa;

import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.entity.GiftCertificateEntity;

import java.util.List;

public interface GiftCertificateRepository {

    /**
     * Find all gift certificates
     * @return List GiftCertificateEntity
     * if fount no gifts - return empty list
     */
    List<GiftCertificateEntity> findAll();


    /**
     * Find gift certificate by criteria
     * @param giftSearchDto
     * @return List GiftCertificateEntity which matches the search conditions
     */
    List<GiftCertificateEntity> findAndSortGifts(GiftSearchDto giftSearchDto);


    /**
     * Find gift certificate by gift id
     * @param id
     * @return GiftCertificateEntity
     * @throws com.epam.esm.jpa.exception.GiftNotFoundException if fount no gifts
     */
    GiftCertificateEntity findById(Long id);

    /**
     * Create gift
     * @param giftCertificateEntity
     * @return created gift
     */
    GiftCertificateEntity createGift(GiftCertificateEntity giftCertificateEntity);

    /**
     * Update gift
     * @param giftCertificateEntity
     * @return updated gift
     * @throws com.epam.esm.jpa.exception.GiftNotFoundException if fount no gifts
     */
    GiftCertificateEntity updateGift(GiftCertificateEntity giftCertificateEntity);

    /**
     * Delete gift by id
     * @param id
     * @throws com.epam.esm.jpa.exception.GiftNotFoundException if fount no gifts
     */
    void deleteGift(Long id);

}
