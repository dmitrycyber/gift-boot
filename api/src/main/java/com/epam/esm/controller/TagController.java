package com.epam.esm.controller;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.search.TagSearchDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagController {
    ResponseEntity<List<TagDto>> allTags(TagSearchDto tagSearchDto, Integer pageNumber, Integer pageSize);

    ResponseEntity<TagDto> tagById(Long id);

    ResponseEntity<TagDto> createTag(TagDto tagDto);

    void deleteTag(Long id);

    TagDto findMostWidelyUsedUserTag(Long userId);




}
