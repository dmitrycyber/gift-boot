package com.epam.esm.service.impl;

import com.epam.esm.jpa.GiftCertificateRepository;
import com.epam.esm.jpa.TagRepository;
import com.epam.esm.jpa.exception.GiftNotFoundException;
import com.epam.esm.model.dto.search.GiftSearchDto;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.service.GiftService;
import com.epam.esm.util.EntityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GiftServiceImpl implements GiftService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public List<GiftCertificateDto> getAllGifts(Integer pageNumber, Integer pageSize) {
        List<GiftCertificateEntity> giftCertificateEntityList = giftCertificateRepository.findAll(pageNumber, pageSize);
        return giftCertificateEntityList.stream()
                .map(EntityConverter::convertGiftEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GiftCertificateDto> searchGifts(GiftSearchDto customSearchRequest, Integer pageNumber, Integer pageSize) {
        List<GiftCertificateEntity> giftList = giftCertificateRepository.findAndSortGifts(customSearchRequest, pageNumber, pageSize);
        return giftList.stream()
                .map(EntityConverter::convertGiftEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GiftCertificateDto getGiftById(Long giftId) {
        GiftCertificateEntity giftById = giftCertificateRepository.findById(giftId);

        checkIfGiftNotFount(giftById);

        return EntityConverter.convertGiftEntityToDto(giftById);
    }

    @Override
    @Transactional
    public GiftCertificateDto createGift(GiftCertificateDto giftCertificateDto) {
        GiftCertificateEntity giftEntityToSave = EntityConverter.convertGiftDtoToEntity(giftCertificateDto);
        Set<TagEntity> tagsToSave = giftEntityToSave.getTagEntities();

        Set<TagEntity> savedTags = createTagsIfNeeded(tagsToSave, giftEntityToSave);
        GiftCertificateEntity savedEntity = giftCertificateRepository.createGift(giftEntityToSave);
        savedEntity.setTagEntities(savedTags);

        return EntityConverter.convertGiftEntityToDto(savedEntity);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateGift(GiftCertificateDto giftCertificateDto) {
        GiftCertificateEntity giftEntityToSave = giftCertificateRepository.findById(giftCertificateDto.getId());

        checkIfGiftNotFount(giftEntityToSave);

        if (giftCertificateDto.getName() != null){
            giftEntityToSave.setName(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getDescription() != null){
            giftEntityToSave.setDescription(giftCertificateDto.getDescription());
        }
        if (giftCertificateDto.getPrice() != null){
            giftEntityToSave.setPrice(giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != null){
            giftEntityToSave.setDuration(giftCertificateDto.getDuration());
        }

        GiftCertificateEntity savedGiftEntity = giftCertificateRepository.updateGift(giftEntityToSave);
        return EntityConverter.convertGiftEntityToDto(savedGiftEntity);
    }

    @Override
    @Transactional
    public void deleteGiftById(Long giftId) {
        giftCertificateRepository.deleteGift(giftId);
    }

    private Set<TagEntity> createTagsIfNeeded(Set<TagEntity> tagEntities, GiftCertificateEntity giftCertificateEntity){
        Set<TagEntity> savedTags = new HashSet<>();

        for(TagEntity tagEntity : tagEntities){
            TagEntity tagEntityByName = tagRepository.findTagByName(tagEntity.getName());
            if (tagEntityByName == null){
                tagRepository.createTag(tagEntity);
                savedTags.add(tagEntity);
            }else {
                savedTags.add(tagEntityByName);
            }
        }
        return savedTags;
    }


    private Set<TagEntity> updateGiftTags(Set<TagEntity> savedTags, Set<TagEntity> tagsToSave, GiftCertificateEntity giftCertificateEntity) {
        if (tagsToSave.isEmpty()) {
            return deleteAllGiftTags(savedTags, tagsToSave, giftCertificateEntity);
        }

        Set<String> savedTagNames = savedTags == null
                ? new HashSet<>()
                : savedTags.stream()
                .map(TagEntity::getName)
                .collect(Collectors.toSet());
        Set<String> tagNamesToSave = tagsToSave.stream()
                .map(TagEntity::getName)
                .collect(Collectors.toSet());

        if (savedTags != null) {
            deleteGiftTagsIfNeeded(savedTags, giftCertificateEntity, tagNamesToSave);
        }
        addTagsIfNeeded(tagsToSave, giftCertificateEntity, savedTagNames);
        return tagsToSave;
    }

    private Set<TagEntity> deleteAllGiftTags(Set<TagEntity> savedTags, Set<TagEntity> tagsToSave, GiftCertificateEntity giftCertificateEntity) {
//        Long id = giftCertificateEntity.getId();
//        if (savedTags != null) {
//            savedTags.
//                    forEach(tagEntity -> {
//                        deleteGiftById(tagEntity.getId());
//                        giftTagDao.deleteGiftTag(id, tagEntity.getId());
//                    });
//        }
//        return tagsToSave;
        return null;
    }

    private void addTagsIfNeeded(Set<TagEntity> tagsToSave, GiftCertificateEntity giftCertificateEntity, Set<String> savedTagNames) {
//        for (TagEntity tagEntity : tagsToSave) {
//            if (savedTagNames.contains(tagEntity.getName())) {
//                TagEntity tagByName = tagDao.findTagByName(tagEntity.getName());
//                tagEntity.setId(tagByName.getId());
//            } else {
//                TagEntity tagsIfNeeded = createTagsIfNeeded(tagEntity, giftCertificateEntity);
//                tagEntity.setId(tagsIfNeeded.getId());
//            }
//        }
    }

    private void deleteGiftTagsIfNeeded(Set<TagEntity> savedTags, GiftCertificateEntity giftCertificateEntity, Set<String> tagNamesToSave) {
//        for (TagEntity tagEntity : savedTags) {
//            if (!tagNamesToSave.contains(tagEntity.getName())) {
//                giftTagDao.deleteGiftTag(giftCertificateEntity.getId(), tagEntity.getId());
//            }
//        }
    }

    private void createGiftTag(GiftCertificateEntity giftEntity, TagEntity savedTag) {
//        GiftTagEntity giftTagEntity = GiftTagEntity.builder()
//                .giftId(giftEntity.getId())
//                .tagId(savedTag.getId())
//                .build();
//        giftTagDao.saveGiftTag(giftTagEntity);
    }

    private void checkIfGiftNotFount(GiftCertificateEntity giftById) {
        if (giftById == null){
            throw new GiftNotFoundException();
        }
    }

}
