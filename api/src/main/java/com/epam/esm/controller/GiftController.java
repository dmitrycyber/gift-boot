package com.epam.esm.controller;

import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.dto.GiftCertificateDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GiftController {

    @ApiOperation(value = "Api v1. Get all gifts")
    ResponseEntity<List<GiftCertificateDto>> allGifts(
            @ApiParam(name = "customSearchRequestDto", value = "Search and sort gift certificates by parameters")
                    GiftSearchDto giftSearchDto);

    ResponseEntity<GiftCertificateDto> giftById(Long id);

    ResponseEntity<GiftCertificateDto> createGift(GiftCertificateDto giftCertificateDto);

    ResponseEntity<GiftCertificateDto> updateGift(Long id, GiftCertificateDto giftCertificateDto);

    void deleteGift(Long id);

}
