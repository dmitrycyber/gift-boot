package com.epam.esm.jpa;

import com.epam.esm.model.dto.search.TagSearchDto;
import com.epam.esm.model.entity.TagEntity;

import java.util.List;

public interface TagRepository {

    /**
     * Find all tags
     * @return List TagEntity
     * @param pageNumber pagination
     * @param pageSize pagination
     * if fount no tags - return empty list
     */
    List<TagEntity> findAllTags(Integer pageNumber, Integer pageSize);

    /**
     * Find tags by tag id
     * @param tagId tag id
     * @return TagEntity
     * @throws com.epam.esm.jpa.exception.TagNotFoundException if fount no tags
     */
    TagEntity findTagById(Long tagId);

    /**
     * Find tags by full tag name
     * @param tagName tag name
     * @return List TagEntity which matches the search conditions
     */
    TagEntity findTagByName(String tagName);

    /**
     * Find tags by full part tag name
     * @param tagSearchDto search criteria
     * @param pageNumber pagination
     * @param pageSize pagination
     * @return List TagEntity which matches the search conditions
     */
    List<TagEntity> findTagByPartName(TagSearchDto tagSearchDto, Integer pageNumber, Integer pageSize);

    /**
     * Create tag
     * @param tagEntity entity to save
     * @return created tag
     */
    TagEntity createTag(TagEntity tagEntity);

    /**
     * Delete tag by id
     * @param tagId tag id
     * @throws com.epam.esm.jpa.exception.TagNotFoundException if fount no tags
     */
    void deleteTagById(Long tagId);

    /**
     * Find most widely used user tag
     * @return most widely user tag with the highest cost
     * @param userId user id
     */
    TagEntity findMostWidelyUsedUserTag(Long userId);

}
