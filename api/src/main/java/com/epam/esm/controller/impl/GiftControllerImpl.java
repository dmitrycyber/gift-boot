package com.epam.esm.controller.impl;

import com.epam.esm.controller.GiftController;
import com.epam.esm.model.dto.CreatingDto;
import com.epam.esm.model.dto.CustomSearchRequestDto;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.service.GiftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/gifts")
@RequiredArgsConstructor
@Slf4j
public class GiftControllerImpl implements GiftController {
    private final GiftService giftService;
    private final CustomSearchRequestDto defaultCustomSearchRequest = CustomSearchRequestDto.builder().build();

    @Override
    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> allGifts(CustomSearchRequestDto customSearchRequestDto) {
        List<GiftCertificateDto> allGifts = !customSearchRequestDto.equals(defaultCustomSearchRequest)
                ? giftService.searchGifts(customSearchRequestDto)
                : giftService.getAllGifts();

        return ResponseEntity.ok(allGifts);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> giftById(@PathVariable Long id) {
        GiftCertificateDto giftById = giftService.getGiftById(id);
        return ResponseEntity.ok(giftById);
    }

    @Override
    @PostMapping
    public ResponseEntity<GiftCertificateDto> createGift(
            @RequestBody
            @Valid
            @Validated(CreatingDto.class)
                    GiftCertificateDto giftCertificateDto) {
        log.info("DTO TO SAVE " + giftCertificateDto);

        GiftCertificateDto createdGift = giftService.createGift(giftCertificateDto);
        return ResponseEntity.ok(createdGift);
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> updateGift(
            @PathVariable Long id,
            @RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);

        GiftCertificateDto gift = giftService.updateGift(giftCertificateDto);

        return ResponseEntity.ok(gift);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGift(
            @PathVariable Long id
    ) {
        giftService.deleteGiftById(id);
    }
}
