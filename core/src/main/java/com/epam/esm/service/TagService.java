package com.epam.esm.service;


import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.search.TagSearchDto;

import java.util.List;

public interface TagService {
    List<TagDto> getAllTags(Integer pageNumber, Integer pageSize);
    TagDto getTagById(Long tagId);
    TagDto getTagByName(String tagName);
    List<TagDto> getTagByPartName(TagSearchDto tagSearchDto, Integer pageNumber, Integer pageSize);
    TagDto createTag(TagDto tagDto);
    void deleteTagById(Long tagId);

    TagDto findMostWidelyUsedUserTag(Long userId);
}
