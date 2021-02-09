package com.epam.esm.controller.impl;

import com.epam.esm.controller.GiftController;
import com.epam.esm.model.dto.CreatingDto;
import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.service.GiftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/gifts")
@RequiredArgsConstructor
@Slf4j
public class GiftControllerImpl implements GiftController {
    private final GiftService giftService;
    private final GiftSearchDto defaultCustomSearchRequest = GiftSearchDto.builder().build();

    @Override
    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> allGifts(@Valid GiftSearchDto giftSearchDto, Integer pageNumber, Integer pageSize) {

        List<GiftCertificateDto> allGifts = !giftSearchDto.equals(defaultCustomSearchRequest)
                ? giftService.searchGifts(giftSearchDto, pageNumber, pageSize)
                : giftService.getAllGifts(pageNumber, pageSize);

        addSelfLinksToList(allGifts);
        return ResponseEntity.ok(allGifts);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> giftById(@PathVariable Long id) {
        GiftCertificateDto giftById = giftService.getGiftById(id);

        addSelfLinks(giftById);
        return ResponseEntity.ok(giftById);
    }

    @Override
    @PostMapping
    public ResponseEntity<GiftCertificateDto> createGift(
            @RequestBody @Valid @Validated(CreatingDto.class) GiftCertificateDto giftCertificateDto) {
        log.info("DTO TO SAVE " + giftCertificateDto);

        GiftCertificateDto createdGift = giftService.createGift(giftCertificateDto);

        addSelfLinks(createdGift);
        return ResponseEntity.ok(createdGift);
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> updateGift(
            @PathVariable Long id,
            @RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);

        GiftCertificateDto updatedGift = giftService.updateGift(giftCertificateDto);

        addSelfLinks(updatedGift);
        return ResponseEntity.ok(updatedGift);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGift(
            @PathVariable Long id
    ) {
        giftService.deleteGiftById(id);
    }

    private void addSelfLinksToList(List<GiftCertificateDto> giftCertificateDtoList) {
        for (GiftCertificateDto giftCertificateDto : giftCertificateDtoList) {
            addSelfLinks(giftCertificateDto);
        }
    }

    private void addSelfLinks(GiftCertificateDto giftCertificateDto) {
        @Valid Set<TagDto> tags = giftCertificateDto.getTags();
        for (TagDto tagDto : tags) {
            tagDto.add(WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(TagControllerImpl.class)
                            .tagById(tagDto.getId()))
                    .withSelfRel());
        }
        giftCertificateDto.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(GiftControllerImpl.class)
                        .giftById(giftCertificateDto.getId()))
                .withSelfRel());
    }
}
