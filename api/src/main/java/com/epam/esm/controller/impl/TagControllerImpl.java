package com.epam.esm.controller.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
@Slf4j
public class TagControllerImpl implements TagController {
    private final TagService tagService;
    private final TagSearchDto defaultTagSearchDto = TagSearchDto.builder()
            .build();

    @Override
    @GetMapping
    public ResponseEntity<List<TagDto>> allTags(@Valid TagSearchDto tagSearchDto, Integer pageNumber, Integer pageSize) {
        log.info("REQUEST SEARCH TAGS " + tagSearchDto);

        List<TagDto> tags = !tagSearchDto.equals(defaultTagSearchDto)
                ? tagService.getTagByPartName(tagSearchDto, pageNumber, pageSize)
                : tagService.getAllTags(pageNumber, pageSize);
        addSelfLinksToList(tags);

        return ResponseEntity.ok(tags);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> tagById(
            @PathVariable Long id
    ) {
        TagDto tagById = tagService.getTagById(id);
        addSelfLinks(tagById);
        return ResponseEntity.ok(tagById);
    }

    @Override
    @PostMapping
    public ResponseEntity<TagDto> createTag(@RequestBody @Valid TagDto tagDto) {
        TagDto createdTag = tagService.createTag(tagDto);
        addSelfLinks(createdTag);
        return ResponseEntity.ok(createdTag);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.deleteTagById(id);
    }


    @Override
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto findMostWidelyUsedUserTag(@PathVariable Long userId) {
        TagDto tagDto = tagService.findMostWidelyUsedUserTag(userId);
        addSelfLinks(tagDto);
        return tagDto;
    }

    private void addSelfLinksToList(List<TagDto> tagDtos) {
        for (TagDto tagDto : tagDtos) {
            addSelfLinks(tagDto);
        }
    }

    private void addSelfLinks(TagDto tagDto) {
        tagDto.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(TagControllerImpl.class)
                        .tagById(tagDto.getId()))
                .withSelfRel());
    }
}
