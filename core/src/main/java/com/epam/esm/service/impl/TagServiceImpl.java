package com.epam.esm.service.impl;

import com.epam.esm.jpa.TagRepository;
import com.epam.esm.jpa.exception.TagNameRegisteredException;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.service.TagService;
import com.epam.esm.util.EntityConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    @Transactional
    public List<TagDto> getAllTags(Integer pageNumber, Integer pageSize) {
        List<TagEntity> allTags = tagRepository.findAllTags(pageNumber, pageSize);

        return allTags.stream()
                .map(EntityConverter::convertTagEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TagDto> getTagByPartName(TagSearchDto tagSearchDto, Integer pageNumber, Integer pageSize){
        List<TagEntity> tagByName = tagRepository.findTagByPartName(tagSearchDto, pageNumber, pageSize);

        return tagByName.stream()
                .map(EntityConverter::convertTagEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TagDto getTagById(Long tagId) {
        TagEntity tagById = tagRepository.findTagById(tagId);
        return EntityConverter.convertTagEntityToDto(tagById);
    }

    @Override
    @Transactional
    public TagDto getTagByName(String tagName) {
        TagEntity tagByName = tagRepository.findTagByName(tagName);

        return EntityConverter.convertTagEntityToDto(tagByName);
    }



    @Override
    @Transactional
    public TagDto createTag(TagDto tagDto){
        TagEntity tagEntity = EntityConverter.convertTagDtoToEntity(tagDto);

        TagEntity tagByName = tagRepository.findTagByName(tagEntity.getName());

        if (tagByName != null) {
            throw new TagNameRegisteredException();
        }
        TagEntity tag = tagRepository.createTag(tagEntity);
        return EntityConverter.convertTagEntityToDto(tag);
    }

    @Override
    public void deleteTagById(Long tagId){
        tagRepository.deleteTagById(tagId);
    }

    @Override
    public TagDto findMostWidelyUsedUserTag(Long userId) {
        TagEntity mostWidelyUsedUserTag = tagRepository.findMostWidelyUsedUserTag(userId);

        return EntityConverter.convertTagEntityToDto(mostWidelyUsedUserTag);
    }
}
