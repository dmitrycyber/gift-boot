package com.epam.esm.controller.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.model.dto.TagDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TagControllerImpl implements TagController {
    @Override
    public ResponseEntity<List<TagDto>> allTags(String name) {
        return null;
    }

    @Override
    public ResponseEntity<TagDto> tagById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<TagDto> createTag(TagDto tagDto) {
        return null;
    }

    @Override
    public void deleteTag(Long id) {

    }
}
