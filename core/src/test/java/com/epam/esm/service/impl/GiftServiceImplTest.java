package com.epam.esm.service.impl;

import com.epam.esm.jpa.GiftCertificateRepository;
import com.epam.esm.jpa.TagRepository;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.util.SearchConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.LongStream;

@ExtendWith(MockitoExtension.class)
class GiftServiceImplTest {
    @Mock
    private static GiftCertificateRepository giftCertificateRepository;
    @Mock
    private static TagRepository tagRepository;

    @InjectMocks
    private GiftServiceImpl giftService;

    private Timestamp currentTimestamp;
    private List<GiftCertificateEntity> giftCertificateEntityList;
    private List<GiftCertificateEntity> searchGiftCertificateEntityList;
    private GiftCertificateEntity giftCertificateEntity;
    private Integer pageNumber;
    private Integer pageSize;

    @BeforeEach
    public void init() {
        pageNumber = 1;
        pageSize = 5;

        currentTimestamp = new Timestamp(System.currentTimeMillis());
        giftCertificateEntityList = new ArrayList<>();

        Set<TagEntity> tags = new HashSet<>();

        LongStream.range(1, 5)
                .forEach(index -> {
                    tags.add(TagEntity.builder()
                            .id(index)
                            .name("name" + index)
                            .build());
                });

        LongStream.range(1, 6)
                .forEach(index -> {
                    giftCertificateEntityList.add(GiftCertificateEntity.builder()
                            .id(index)
                            .name("name" + index)
                            .description("description" + index)
                            .price((int) index)
                            .duration((int) index)
                            .createDate(currentTimestamp)
                            .lastUpdate(currentTimestamp)
                            .tagEntities(tags)
                            .build());
                });
        giftCertificateEntity = giftCertificateEntityList.get(0);

        searchGiftCertificateEntityList = new ArrayList<>();
        searchGiftCertificateEntityList.add(GiftCertificateEntity.builder()
                .id(1L)
                .name("name1")
                .description("description1")
                .price(1)
                .duration(1)
                .createDate(currentTimestamp)
                .lastUpdate(currentTimestamp)
                .tagEntities(tags)
                .build());
    }

    @Test
    void getAllGifts() {
        Mockito.when(giftCertificateRepository.findAll(pageNumber, pageSize)).thenReturn(giftCertificateEntityList);

        List<GiftCertificateDto> allGifts = giftService.getAllGifts(pageNumber, pageSize);

        Assertions.assertEquals(5, allGifts.size());

        allGifts
                .forEach(giftCertificateEntity -> {
                    Assertions.assertTrue(giftCertificateEntity.getName().contains("name"));
                    Assertions.assertTrue(giftCertificateEntity.getDescription().contains("description"));
                    Assertions.assertNotNull(giftCertificateEntity.getId());
                    Assertions.assertNotNull(giftCertificateEntity.getCreateDate());
                    Assertions.assertNotNull(giftCertificateEntity.getLastUpdateDate());
                    Assertions.assertNotNull(giftCertificateEntity.getPrice());
                    Assertions.assertNotNull(giftCertificateEntity.getDuration());
                    Assertions.assertEquals(currentTimestamp.toLocalDateTime(), giftCertificateEntity.getCreateDate());
                    Assertions.assertEquals(currentTimestamp.toLocalDateTime(), giftCertificateEntity.getLastUpdateDate());
                });
    }

    @Test
    void searchGifts() {
        Mockito.when(giftCertificateRepository
                .findAndSortGifts(Mockito.any(GiftSearchDto.class), Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(searchGiftCertificateEntityList);

        List<String> expectedTagNames = new ArrayList<>();
        expectedTagNames.add("name1");

        String expectedName = "name1";
        String expectedDescription = "description1";
        GiftSearchDto customSearchRequest = GiftSearchDto.builder()
                .tagNamePrefixes(expectedTagNames)
                .namePrefix(expectedName)
                .descriptionPrefix(expectedDescription)
                .sortField(SearchConstants.NAME_FIELD)
                .sortMethod(SearchConstants.DESC_METHOD_SORT)
                .build();

        List<GiftCertificateDto> giftCertificateDtoList = giftService.searchGifts(customSearchRequest, pageNumber, pageSize);
        GiftCertificateDto giftCertificateDto = giftCertificateDtoList.get(0);
        Set<TagDto> tags = giftCertificateDto.getTags();

        Assertions.assertNotNull(giftCertificateDtoList);
        Assertions.assertNotNull(tags);
        Assertions.assertEquals(expectedName, giftCertificateDto.getName());
        Assertions.assertEquals(expectedDescription, giftCertificateDto.getDescription());
    }

    @Test
    void getGiftById() {
        long id = 1L;
        Mockito.when(giftCertificateRepository.findById(Mockito.anyLong())).thenReturn(giftCertificateEntity);

        GiftCertificateDto giftById = giftService.getGiftById(id);

        Assertions.assertEquals("name1", giftById.getName());
        Assertions.assertEquals("description1", giftById.getDescription());
        Assertions.assertEquals(1, giftById.getDuration());
        Assertions.assertEquals(1, giftById.getPrice());
        Assertions.assertEquals(currentTimestamp.toLocalDateTime(), giftById.getCreateDate());
        Assertions.assertEquals(currentTimestamp.toLocalDateTime(), giftById.getLastUpdateDate());
    }

    @Test
    void createGift() {
        Mockito.when(giftCertificateRepository.createGift(Mockito.any(GiftCertificateEntity.class))).thenReturn(giftCertificateEntity);
        Mockito.when(tagRepository.createTag(Mockito.any(TagEntity.class))).thenReturn(TagEntity.builder()
                .build());

        Set<TagDto> tags = new HashSet<>();
        tags.add(TagDto.builder()
                .name("tagName")
                .build());

        GiftCertificateDto giftCertificateDto = giftService.createGift(GiftCertificateDto.builder()
                .name("name1")
                .description("description1")
                .price(1)
                .duration(1)
                .tags(tags)
                .build());

        Assertions.assertEquals(1L, giftCertificateDto.getId());
        Assertions.assertEquals("name1", giftCertificateDto.getName());
        Assertions.assertEquals("description1", giftCertificateDto.getDescription());
        Assertions.assertEquals(1, giftCertificateDto.getPrice());
        Assertions.assertEquals(1, giftCertificateDto.getDuration());
    }

    @Test
    void updateGift() {
        Mockito.when(giftCertificateRepository.updateGift(Mockito.any(GiftCertificateEntity.class))).thenReturn(giftCertificateEntity);
        Mockito.when(giftCertificateRepository.findById(Mockito.any())).thenReturn(giftCertificateEntity);

        Set<TagDto> tags = new HashSet<>();
        tags.add(TagDto.builder()
                .name("tagName")
                .build());

        GiftCertificateDto giftCertificateDto = giftService.updateGift(GiftCertificateDto.builder()
                .name("name1")
                .description("description1")
                .price(1)
                .duration(1)
                .tags(tags)
                .build());

        Assertions.assertEquals("description1", giftCertificateDto.getDescription());
    }

    @Test
    void deleteGiftById() {
        Long id = 1L;

        giftService.deleteGiftById(id);

        Mockito.verify(giftCertificateRepository, Mockito.times(1)).deleteGift(Mockito.anyLong());
    }
}