package com.epam.esm.jpa;

import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.TagEntity;

import java.util.List;

public interface TagRepository {

    /**
     * Find all tags
     * @return List TagEntity
     * if fount no tags - return empty list
     */
    List<TagEntity> findAllTags();

    /**
     * Find tags by tag id
     * @param tagId
     * @return TagEntity
     * @throws com.epam.esm.jpa.exception.TagNotFoundException if fount no tags
     */
    TagEntity findTagById(Long tagId);

    /**
     * Find tags by full tag name
     * @param tagName
     * @return List TagEntity which matches the search conditions
     */
    TagEntity findTagByName(String tagName);

    /**
     * Find tags by full part tag name
     * @param tagSearchDto
     * @return List TagEntity which matches the search conditions
     */
    List<TagEntity> findTagByPartName(TagSearchDto tagSearchDto);

    /**
     * Create tag
     * @param tagEntity
     * @return created tag
     */
    TagEntity createTag(TagEntity tagEntity);

    /**
     * Delete tag by id
     * @param tagId
     * @throws com.epam.esm.jpa.exception.TagNotFoundException if fount no tags
     */
    void deleteTagById(Long tagId);

    TagEntity findMostWidelyUsedUserTag(Long userId);

}
