package com.epam.esm.controller;

import com.epam.esm.model.dto.TagDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagController {
    ResponseEntity<List<TagDto>> allTags(String name);

    ResponseEntity<TagDto> tagById(Long id);

    ResponseEntity<TagDto> createTag(TagDto tagDto);

    void deleteTag(Long id);




}
