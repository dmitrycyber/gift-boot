package com.epam.esm.service;

import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.dto.GiftCertificateDto;

import java.util.List;

public interface GiftService {
    List<GiftCertificateDto> getAllGifts(Integer pageNumber, Integer pageSize);
    List<GiftCertificateDto> searchGifts(GiftSearchDto giftSearchDto, Integer pageNumber, Integer pageSize);
    GiftCertificateDto getGiftById(Long giftId);
    GiftCertificateDto createGift(GiftCertificateDto giftCertificateDto);
    GiftCertificateDto updateGift(GiftCertificateDto giftCertificateDto);
    void deleteGiftById(Long giftId);
}
