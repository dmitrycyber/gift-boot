package com.epam.esm.controller;

import com.epam.esm.model.dto.CreatingDto;
import com.epam.esm.model.dto.CustomSearchRequestDto;
import com.epam.esm.model.dto.GiftCertificateDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;

public interface GiftController {

    @ApiOperation(value = "Api v1. Get all gifts")
    ResponseEntity<List<GiftCertificateDto>> allGifts(
            @ApiParam(name = "customSearchRequestDto", value = "Search and sort gift certificates by parameters")
            CustomSearchRequestDto customSearchRequestDto);

    ResponseEntity<GiftCertificateDto> giftById(Long id);

    ResponseEntity<GiftCertificateDto> createGift(GiftCertificateDto giftCertificateDto);

    ResponseEntity<GiftCertificateDto> updateGift(Long id, GiftCertificateDto giftCertificateDto);

    void deleteGift(Long id);

}
